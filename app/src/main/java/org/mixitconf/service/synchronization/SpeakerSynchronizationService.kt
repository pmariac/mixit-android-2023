package org.mixitconf.service.synchronization

import androidx.room.Transaction
import org.mixitconf.api.TalkApiRepository
import org.mixitconf.model.dao.LinkRepository
import org.mixitconf.model.dao.SpeakerRepository
import org.mixitconf.model.entity.Talk
import org.mixitconf.service.synchronization.dto.SpeakerApiDto
import retrofit2.Response

class SpeakerSynchronizationService(
    val talkApiRepository: TalkApiRepository,
    val speakerRepository: SpeakerRepository,
    val linkRepository: LinkRepository
) : SynchronizationService<SpeakerApiDto>() {

    override suspend fun read(): Response<List<SpeakerApiDto>> =
        talkApiRepository.speakers()

    @Transaction
    override suspend fun save(result: List<SpeakerApiDto>, mode: Companion.SyncMode) {
        val logins = result.map { it.login }

        var updated = false
        val speakersToDelete = speakerRepository.readAll()
            .filterNot { logins.contains(it.login) }
            .map { it.login }
        if (speakersToDelete.isNotEmpty()) {
            speakerRepository.deleteAllById(speakersToDelete)
            updated = true
        }

        result.map { it }.forEach { speaker ->
            speakerRepository.readOne(speaker.login).also { existingSpeaker ->
                if (existingSpeaker == null) {
                    speakerRepository.create(speaker.toEntity())
                    upsertLinks(speaker)
                    updated = true
                } else if (existingSpeaker != speaker.toEntity()) {
                    speakerRepository.update(speaker.toEntity())
                    upsertLinks(speaker)
                    updated = true
                }
            }
        }
        if (updated || mode == Companion.SyncMode.MANUAL) {
            sendNotification<Talk>(Companion.Result.Success)
        }
    }

    private suspend fun upsertLinks(speaker: SpeakerApiDto) {
        linkRepository.deleteBySpeaker(speaker.login)
        speaker.links
            .map { it.toEntity(speaker.login) }
            .forEach { linkRepository.create(it) }
    }
}