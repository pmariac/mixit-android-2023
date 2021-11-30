package org.mixitconf.service.synchronization

import androidx.room.Transaction
import org.mixitconf.api.TalkApiRepository
import org.mixitconf.api.dto.TalkApiDto
import org.mixitconf.database.TalkRepository
import org.mixitconf.model.Talk
import org.mixitconf.service.planning.PlanningAlarmService
import org.mixitconf.service.planning.PlanningGenerator
import retrofit2.Response
import timber.log.Timber

class TalkSynchronizationService(
    private val talkApiRepository: TalkApiRepository,
    private val talkRepository: TalkRepository,
    private val alarmService: PlanningAlarmService
) : SynchronizationService<TalkApiDto>() {

    override suspend fun read(): Response<List<TalkApiDto>> =
        talkApiRepository.talks()

    @Transaction
    override suspend fun save(result: List<TalkApiDto>, mode: Companion.SyncMode) {
        if (result.isEmpty()) {
            Timber.w("API returned no talk")
            return
        }
        val planningEvents = PlanningGenerator.generatePlanningEvents(context)
        val allTalks = result + planningEvents

        // We remove all talks
        val favorites: List<Talk> = talkRepository.readFavorites().onEach { alarmService.cancelTalkAlarm(it) }
        talkRepository.deleteAll()
        allTalks
            .map { dto -> dto.toEntity().copy(favorite = favorites.map { it.remoteId }.contains(dto.id)) }
            .forEach { talk -> talkRepository.create(talk) }

        talkRepository.readFavorites().forEach { alarmService.setTalkAlarm(it) }
    }

}