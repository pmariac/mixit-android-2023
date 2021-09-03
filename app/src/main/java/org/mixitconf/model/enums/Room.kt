package org.mixitconf.model.enums

import org.mixitconf.R

enum class Room(val i18nId: Int,
                val capacity: Int,
                val filmed: Boolean = false,
                val scribo: Boolean = false,
                val scriboUrl: String? = null,
                val risp: Boolean = false,
                val rispUrl: String? = null) {
    AMPHI1(R.string.room_amphi1, 500),
    AMPHI2(R.string.room_amphi2, 200),
    AMPHIC(R.string.room_amphic,445),
    AMPHID(R.string.room_amphid,445),
    AMPHIK(R.string.room_amphik,300),
    ROOM1(R.string.room1, 198),
    ROOM2(R.string.room2,108),
    ROOM3(R.string.room3,30),
    ROOM4(R.string.room4,30),
    ROOM5(R.string.room5,30),
    ROOM6(R.string.room6,30),
    ROOM7(R.string.room7,30),
    ROOM8(R.string.room8,30),
    OUTSIDE(R.string.room_outside, 50),
    MUMMY(R.string.room_mummy, 30),
    SPEAKER(R.string.room_speaker, 16),
    UNKNOWN(R.string.room_unknown, 0),
    SURPRISE(R.string.room_surprise, 0);

    val hardOfHearingSytem
        get() = this.risp || this.scribo

    companion object {
        fun of(string: String?): Room =
            values().firstOrNull { it.name == string } ?: Room.UNKNOWN
    }
}


