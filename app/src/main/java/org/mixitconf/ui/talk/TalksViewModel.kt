package org.mixitconf.ui.talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import org.mixitconf.model.dao.SpeakerRepository
import org.mixitconf.model.dao.TalkRepository
import org.mixitconf.model.entity.Speaker
import org.mixitconf.model.entity.Talk

class TalksViewModel(private val repository: TalkRepository,
                     private val speakerRepository: SpeakerRepository) : ViewModel() {

    fun search(): LiveData<List<Talk>> = liveData {
        val result = repository.readAll()
        emit(result.sortedWith(compareBy<Talk> { it.start }.thenBy { it.end }.thenBy { it.room }))
    }

    fun getOne(id: String): LiveData<Talk?> = liveData {
        emit(repository.readOne(id))
    }

    fun getTalkSpeakers(speakerIds: List<String>): LiveData<List<Speaker>?> = liveData {
        emit(speakerRepository.readAllByIds(speakerIds).sortedBy { it.fullname })
    }

    fun update(talk: Talk): LiveData<Talk?> = liveData {
        repository.update(talk)
        emit(repository.readOne(talk.id))
    }
}