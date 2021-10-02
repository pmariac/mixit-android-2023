package org.mixitconf.service.planning

import android.content.Context
import org.mixitconf.Properties
import org.mixitconf.Properties.TALK_DATE_FORMAT
import org.mixitconf.R
import org.mixitconf.addMinutes
import org.mixitconf.api.dto.TalkApiDto
import org.mixitconf.model.enums.Language
import org.mixitconf.model.enums.Room
import org.mixitconf.model.enums.TalkFormat
import org.mixitconf.model.enums.TalkFormat.PLANNING_DAY
import org.mixitconf.toString
import java.util.*

object PlanningGenerator {
    private enum class Day(val day: Int) {
        One(Properties.MIXIT_DAY_1),
        Two(Properties.MIXIT_DAY_2),
        Three(Properties.MIXIT_DAY_3)
    }

    fun generatePlanningEvents(context: Context): List<TalkApiDto> = listOf(
        create(context, Day.One, PLANNING_DAY, 16, 29, R.string.event_day1),
        create(context, Day.One, TalkFormat.PLANNING_ORGA_SPEECH, 16, 30),
        create(context, Day.One, TalkFormat.PLANNING_INTRODUCTION_SESSION, 16, 45),
        create(context, Day.One, TalkFormat.PLANNING_PAUSE_10_MIN, 17, 50),
        create(context, Day.One, TalkFormat.PLANNING_PAUSE_10_MIN, 18, 50),
        create(context, Day.One, TalkFormat.PLANNING_PAUSE_10_MIN, 19, 50),

        create(context, Day.Two, PLANNING_DAY, 16, 29, R.string.event_day2),
        create(context, Day.Two, TalkFormat.PLANNING_ORGA_SPEECH, 16, 30),
        create(context, Day.Two, TalkFormat.PLANNING_INTRODUCTION_SESSION, 16, 45),
        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_10_MIN, 17, 50),
        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_10_MIN, 18, 50),
        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_10_MIN, 19, 50),

        create(context, Day.Three, PLANNING_DAY, 16, 29, R.string.event_day3),
        create(context, Day.Three, TalkFormat.PLANNING_ORGA_SPEECH, 16, 30),
        create(context, Day.Three, TalkFormat.PLANNING_INTRODUCTION_SESSION, 16, 45),
        create(context, Day.Three, TalkFormat.PLANNING_PAUSE_10_MIN, 17, 50),
        create(context, Day.Three, TalkFormat.PLANNING_PAUSE_10_MIN, 18, 50),
        create(context, Day.Three, TalkFormat.PLANNING_PAUSE_10_MIN, 19, 50),
//        create(context, Day.Two, PLANNING_DAY, 8, 29, R.string.event_day2),
//        create(context, Day.Two, TalkFormat.PLANNING_WELCOME, 8, 30),
//        create(context, Day.Two, TalkFormat.PLANNING_ORGA_SPEECH, 9, 15),
//        create(context, Day.Two, TalkFormat.PLANNING_INTRODUCTION_SESSION, 10, 0),
//        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_25_MIN, 10, 15),
//        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_10_MIN, 11, 30),
//        create(context, Day.Two, TalkFormat.PLANNING_LUNCH, 12, 30),
//        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_10_MIN, 15, 20),
//        create(context, Day.Two, TalkFormat.PLANNING_PAUSE_30_MIN, 16, 20)
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
        event = "2021",
        title = context.getString(title),
        summary = "",
        speakerIds = emptyList(),
        language = Language.FRENCH,
        addedAt = Date().toString(),
        description = "",
        topic = "",
        room = Room.UNKNOWN.name,
        start = createDate(day.day, startHour, startMinute).toString(TALK_DATE_FORMAT),
        end = createDate(day.day, startHour, startMinute).addMinutes(talkFormat.duration).toString(TALK_DATE_FORMAT),
    )

    private fun createDate(day: Int, hour: Int, minute: Int): Date {
        val calendar = Calendar.getInstance(Locale.FRANCE)
        calendar.set(2021, 4, day, hour, minute, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}