package org.mixitconf.ui.speaker

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mixitconf.database.SpeakerRepository
import org.mixitconf.database.TalkRepository
import org.mixitconf.model.Speaker
import org.mixitconf.model.Talk

class SpeakersViewModel(
    private val repository: SpeakerRepository,
    private val talkRepository: TalkRepository
) : ViewModel() {

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