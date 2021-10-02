package org.mixitconf.service.synchronization

import androidx.room.Transaction
import org.mixitconf.api.TalkApiRepository
import org.mixitconf.api.dto.TalkApiDto
import org.mixitconf.model.dao.TalkRepository
import org.mixitconf.model.entity.Talk
import org.mixitconf.service.planning.PlanningGenerator
import retrofit2.Response
import timber.log.Timber

class TalkSynchronizationService(
    private val talkApiRepository: TalkApiRepository,
    private val talkRepository: TalkRepository
) : SynchronizationService<TalkApiDto>() {

    override suspend fun read(): Response<List<TalkApiDto>> =
        talkApiRepository.talks()

    @Transaction
    override suspend fun save(result: List<TalkApiDto>, mode: Companion.SyncMode) {
        if(result.isEmpty()) {
            Timber.w("API returned no talk")
            return
        }
        val planningEvents = PlanningGenerator.generatePlanningEvents(context)
        val allTalks = result + planningEvents

        // We remove all talks
        talkRepository.deleteAll()
        allTalks
            .map { it.toEntity() }
            .forEach { talk ->
                talkRepository.readOne(talk.id).also { existingTalk ->
                    if (existingTalk == null) {
                        talkRepository.create(talk)
                    } else if (existingTalk != talk) {
                        talkRepository.update(talk)
                    }
                }
            }

        if (mode == Companion.SyncMode.MANUAL) {
            sendNotification<Talk>(Companion.Result.Success)
        }
    }

}