package org.mixitconf.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import org.mixitconf.model.dao.TalkRepository
import org.mixitconf.model.entity.Talk
import org.mixitconf.service.planning.PlanningAlarmService

class FavoritesViewModel(
    private val repository: TalkRepository,
    private val alarmService: PlanningAlarmService
) : ViewModel() {

    /**
     * This function is used to cancel all alarms or to activate these alarms when a preference is updated
     */
    fun updateFavoriteAlarms(active: Boolean): LiveData<List<Talk>?> = liveData {
        emit(repository
            .readFavorites()
            .onEach {
                if (active) {
                    alarmService.setTalkAlarm(it)
                } else {
                    alarmService.cancelTalkAlarm(it)
                }
            }
        )
    }
}