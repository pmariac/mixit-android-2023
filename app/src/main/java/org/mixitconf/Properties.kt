package org.mixitconf

object Properties {
    const val MIXIT_EVENT_API = BuildConfig.MIXIT_URL + BuildConfig.EVENT_API
    const val MIXIT_USER_API = BuildConfig.MIXIT_URL + BuildConfig.USER_API
    /**
     * Default timeout for API requests in seconds.
     */
    const val DEFAULT_TIMEOUT_SECONDS = 30L
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