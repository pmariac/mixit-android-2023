package org.mixitconf.service.synchronization

import androidx.room.Transaction
import org.mixitconf.api.TalkApiRepository
import org.mixitconf.api.dto.TalkApiDto
import org.mixitconf.model.dao.TalkRepository
import org.mixitconf.model.entity.Talk
import org.mixitconf.service.planning.PlanningGenerator
import retrofit2.Response

class TalkSynchronizationService(
    val talkApiRepository: TalkApiRepository,
    val talkRepository: TalkRepository
) : SynchronizationService<TalkApiDto>() {

    override suspend fun read(): Response<List<TalkApiDto>> =
        talkApiRepository.talks()

    @Transaction
    override suspend fun save(result: List<TalkApiDto>, mode: Companion.SyncMode) {
        val planningEvents = PlanningGenerator.generatePlanningEvents(context)
        val allTalks = result + planningEvents
        var updated = false

        // We want remove talks if necessary
        val ids = allTalks.map { it.id }
        val talksToDelete = talkRepository.readAll().filter { !ids.contains(it.id) }.map { it.id }
        if (talksToDelete.isNotEmpty()) {
            talkRepository.deleteAllById(talksToDelete)
            updated = true
        }

        allTalks
            .map { it.toEntity() }
            .forEach { talk ->
                talkRepository.readOne(talk.id).also { existingTalk ->
                    if (existingTalk == null) {
                        talkRepository.create(talk)
                        updated = true
                    } else if (existingTalk != talk) {
                        talkRepository.update(talk)
                        updated = true
                    }
                }
            }

        if (updated || mode == Companion.SyncMode.MANUAL) {
            sendNotification<Talk>(Companion.Result.Success)
        }
    }

}