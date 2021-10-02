package org.mixitconf.ui.speaker

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import org.mixitconf.model.dao.SpeakerRepository
import org.mixitconf.model.entity.Speaker

class SpeakersViewModel(private val repository: SpeakerRepository) : ViewModel() {

    fun search(): LiveData<List<Speaker>> = liveData {
        val result = repository.readAll()
        emit(result.sortedBy { it.fullname })
    }
}