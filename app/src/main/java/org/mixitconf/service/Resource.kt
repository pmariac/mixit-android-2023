package org.mixitconf.service

import org.mixitconf.HttpStatus
import retrofit2.Response

class UnknownUserException : Exception()
class InvalidCredentialException : Exception()
class ForbiddenException : Exception()
class NoInternetException : Exception()

/**
 * Sealed class containing data of type [T]. It can have three different statuses:
 * - Success
 * - Loading
 * - Error
 */
sealed class Resource<out T : Any> {
    /**
     * Represents a [Resource] with Success status. It contains non-nullable data.
     */
    data class Success<T : Any>(val data: T) : Resource<T>()

    /**
     * Represents a [Resource] with Loading status. It may contain some data.
     */
    data class Loading<T : Any>(val data: T? = null) : Resource<T>()

    /**
     * Represents a [Resource] with Error status. It may contain an error message.
     */
    data class Error(val exception: Exception) : Resource<Nothing>()
}

fun <T : Any> Response<T>.asError(): Resource.Error {
    // Check if the response was successful:
    if (isSuccessful) {
        throw RuntimeException(".asError is used even though the response is successful.")
    }
    return when (code()) {
        // Check if failed due to no internet:
        HttpStatus.NO_INTERNET -> Resource.Error(NoInternetException())
        HttpStatus.UNAUTHORIZED -> Resource.Error(ForbiddenException())
        HttpStatus.BAD_REQUEST -> Resource.Error(IllegalArgumentException())
        else -> Resource.Error(RuntimeException("An unknown exception occurred"))
    }
}
