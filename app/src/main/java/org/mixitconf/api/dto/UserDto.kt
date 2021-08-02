package org.mixitconf.api.dto

import com.squareup.moshi.Json
import org.mixitconf.model.User

data class UserDto(
    @field:Json(name = "login")
    var login: String,
    @field:Json(name = "firstname")
    var firstname: String? = null,
    @field:Json(name = "lastname")
    var lastname: String? = null
) {
    fun toUser(email: String?, token: String?): User =
        User(login, firstname, lastname, email, token)
}
