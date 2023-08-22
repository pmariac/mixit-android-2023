package org.mixitconf.ui.talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import org.mixitconf.Properties
import org.mixitconf.database.SpeakerRepository
import org.mixitconf.database.TalkRepository
import org.mixitconf.model.Speaker
import org.mixitconf.model.Talk
import org.mixitconf.model.enums.TalkFormat
import org.mixitconf.service.planning.PlanningAlarmService
import org.mixitconf.toString

class TalksViewModel(
    private val repository: TalkRepository,
    private val speakerRepository: SpeakerRepository,
    private val alarmService: PlanningAlarmService
) : ViewModel() {

    val allTalksByDate: Flow<List<Talk>> =
        repository.readAll().map { list ->
            list.sortedWith(compareBy<Talk> { it.start }.thenBy { it.end }.thenBy { it.room })
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(3000), 1)

    val searchFavorites: Flow<List<Talk>> = liveData {
            val result = repository.readAll()
            val favorites = result.filter { it.favorite }
            val favoriteDays = favorites.map { it.start.toString(Properties.TALK_LOCALDATE_FORMAT) }.distinct()
            val days = result
                .filter { it.format == TalkFormat.PLANNING_DAY }
                .filter { favoriteDays.contains(it.start.toString(Properties.TALK_LOCALDATE_FORMAT)) }
            emit(favorites.plus(days).sortedWitlmh(compareBy<Talk> { it.start }.thenBy { it.end }.thenBy { it.room }))
        }

    fun getOne(id: Long): LiveData<Talk?> = liveData {
        emit(repository.readOne(id))
    }

    fun getTalkSpeakers(speakerIds: List<String>): LiveData<List<Speaker>?> = liveData {
        emit(speakerRepository.readAllByIds(speakerIds).sortedBy { it.fullname })
    }

    fun update(talk: Talk): LiveData<Talk?> = liveData {
        if (talk.favorite) {
            alarmService.setTalkAlarm(talk)
        }
        else {
            alarmService.cancelTalkAlarm(talk)
        }
        repository.update(talk)
        emit(repository.readOne(talk.id!!))
    }
}