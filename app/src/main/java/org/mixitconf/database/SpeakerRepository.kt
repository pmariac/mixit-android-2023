package org.mixitconf.database

import androidx.room.*
import org.mixitconf.model.Speaker

@Dao
interface SpeakerRepository {
    @Insert
    suspend fun create(speaker: Speaker)

    @Query("select * from Speaker")
    suspend fun readAll(): List<Speaker>

    @Query("select * from Speaker where login in (:ids)")
    suspend fun readAllByIds(ids: List<String>): List<Speaker>

    @Query("select * from Speaker where login = :login")
    suspend fun readOne(login: String): Speaker?

    @Update
    suspend fun update(speaker: Speaker): Int

    @Delete
    suspend fun delete(speaker: Speaker)

    @Query("delete from Speaker")
    suspend fun deleteAll()

    @Query("delete from Speaker where login in (:ids)")
    suspend fun deleteAllById(ids: List<String>)
}
