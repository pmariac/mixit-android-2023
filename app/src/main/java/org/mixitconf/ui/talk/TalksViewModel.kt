package org.mixitconf.ui.talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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

    fun search(): LiveData<List<Talk>> = liveData {
        val result = repository.readAll()
        emit(result.sortedWith(compareBy<Talk> { it.start }.thenBy { it.end }.thenBy { it.room }))
    }

    fun searchFavorites(): LiveData<List<Talk>> = liveData {
        val result = repository.readAll()
        val favorites = result.filter { it.favorite }
        val favoriteDays = favorites.map { it.start.toString(Properties.TALK_LOCALDATE_FORMAT) }.distinct()
        val days = result
            .filter { it.format == TalkFormat.PLANNING_DAY }
            .filter { favoriteDays.contains(it.start.toString(Properties.TALK_LOCALDATE_FORMAT)) }
        emit(favorites.plus(days).sortedWith(compareBy<Talk> { it.start }.thenBy { it.end }.thenBy { it.room }))
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