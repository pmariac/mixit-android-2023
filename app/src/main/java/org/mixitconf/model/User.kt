package org.mixitconf.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing an User. All data will be deleted on user sign out
 */
@Entity
data class User(
    /**
     * Eeach user is identified in Mixit API by a login
     */
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    val login: String,

    val firstname: String?,

    val lastname: String?,

    /**
     * This email is required for authentication. We store it to avoid user to resend it at each
     * sign out
     */
    val email: String?,

    /**
     * The temporary token to use in our API
     */
    val token: String?,
)
