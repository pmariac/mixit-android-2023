package org.mixitconf.ui.speaker

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import org.mixitconf.model.dao.SpeakerRepository
import org.mixitconf.model.dao.TalkRepository
import org.mixitconf.model.entity.Speaker
import org.mixitconf.model.entity.Talk

class SpeakersViewModel(
    private val repository: SpeakerRepository,
    private val talkRepository: TalkRepository) : ViewModel() {

    fun search(): LiveData<List<Speaker>> = liveData {
        val result = repository.readAll()
        emit(result.sortedBy { it.fullname })
    }

    fun getOne(login: String): LiveData<Speaker?> = liveData {
        emit(repository.readOne(login))
    }

    fun getSpeakerTalks(login: String): LiveData<List<Talk>?> = liveData {
        emit(talkRepository.readAllBySpeakerId(login).sortedBy { it.start })
    }
}