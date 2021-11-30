package org.mixitconf

object Properties {

    const val TALK_ID = "TALK_ID"
    const val TALK_LOCALDATE_FORMAT = "yyyy/MM/dd"
    const val TALK_DATE_FORMAT = "yyyy/MM/dd'T'HH:mm:ss"
    const val MIXIT_EVENT_API = BuildConfig.MIXIT_URL + BuildConfig.EVENT_API
    const val MIXIT_USER_API = BuildConfig.MIXIT_URL + BuildConfig.USER_API
    const val MIXIT_DAY_1 = 18
    const val MIXIT_DAY_2 = 19
    const val MIXIT_DAY_3 = 20


    val DATA_SYNC_INTERVAL: Long = BuildConfig.DATA_SYNCHRONIZATION_INTERVAL
    const val DATA_SYNC_INTERVAL_TIMEUNIT = BuildConfig.DATA_SYNCHRONIZATION_INTERVAL_TIMEUNIT
    const val DATA_SYNC_WORKER_NAME = "DATA_SYNCHRONIZATION_WORKER"

    /**
     * Default timeout for API requests in seconds.
     */
    const val DEFAULT_TIMEOUT_SECONDS = 30L

    val SPECIAL_SLUG_CHARACTERS = mapOf(
        Pair('é', 'e'), Pair('è', 'e'), Pair('ï', 'i'), Pair(' ', '_'), Pair('ê', 'e'),
        Pair('.', '_'), Pair('\'', '_'), Pair('ô', 'o'), Pair('à', 'a'), Pair('-', '_')
    )

    fun isDevMode() = BuildConfig.DEBUG
}

/**
 * Http status codes
 */
object HttpStatus {
    const val OK = 200
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val NO_INTERNET = 999
}