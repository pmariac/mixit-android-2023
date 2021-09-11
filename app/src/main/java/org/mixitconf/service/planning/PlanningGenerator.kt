package org.mixitconf.service.planning

import android.content.Context
import org.mixitconf.Properties
import org.mixitconf.R
import org.mixitconf.addMinutes
import org.mixitconf.api.dto.TalkApiDto
import org.mixitconf.model.enums.Language
import org.mixitconf.model.enums.Room
import org.mixitconf.model.enums.TalkFormat
import org.mixitconf.model.enums.TalkFormat.PLANNING_DAY
import java.util.*

object PlanningGenerator {
    private enum class Day(val day: Int) { One(Properties.MIXIT_DAY_1), Two(Properties.MIXIT_DAY_2) }

    fun generatePlanningEvents(context: Context): List<TalkApiDto> = listOf(
        create(context, Day.One, PLANNING_DAY, 8, 29, R.string.event_day1),
        create(context, Day.One, TalkFormat.PLANNING_WELCOME, 8, 30),
        create(context, Day.One, TalkFormat.PLANNING_ORGA_SPEECH, 9, 15),
        create(context, Day.One, TalkFormat.PLANNING_INTRODUCTION_SESSION, 10, 0),
        create(context, Day.One, TalkFormat.PLANNING_PAUSE_25_MIN, 10, 15),
        create(context, Day.One, TalkFormat.PLANNING_PAUSE_10_MIN, 11, 30),
        create(context, Day.One, TalkFormat.PLANNING_LUNCH, 12, 30),
        create(context, Day.One, TalkFormat.PLANNING_PAUSE_10_MIN, 15, 20),
        create(context, Day.One, TalkFormat.PLANNING_PAUSE_30_MIN, 16, 20),

        create(context, Day.Two, PLANNING_DAY, 8, 29, R.string.event_day2),
        create(context, Day.Two, TalkFormat.PLANNING_WELCOME, 8, 30),
        create(context, Day.Two, TalkFormat.PLANNING_ORGA_SPEECH, 9, 15),
        create(context, Day.Two, TalkFormat.PLANNING_INTRODUCTION_SESSION, 10, 0),
        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_25_MIN, 10, 15),
        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_10_MIN, 11, 30),
        create(context, Day.Two, TalkFormat.PLANNING_LUNCH, 12, 30),
        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_10_MIN, 15, 20),
        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_30_MIN, 16, 20)
    )

    private fun create(
        context: Context,
        day: Day,
        talkFormat: TalkFormat,
        startHour: Int,
        startMinute: Int,
        title: Int = talkFormat.label
    ) = TalkApiDto(
        id = day.name + talkFormat.name + startHour,
        format = talkFormat,
        event = "2020",
        title = context.getString(title),
        summary = "",
        speakerIds = emptyList(),
        language = Language.FRENCH,
        addedAt = Date(),
        description = "",
        topic = "",
        room = Room.UNKNOWN.name,
        start = createDate(day.day, startHour, startMinute),
        end = createDate(day.day, startHour, startMinute).addMinutes(talkFormat.duration),
    )

    private fun createDate(day: Int, hour: Int, minute: Int): Date {
        val calendar = Calendar.getInstance(Locale.FRANCE)
        calendar.set(2020, 3, day, hour, minute, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}