package org.mixitconf.database

import androidx.room.*
import org.mixitconf.model.Link

@Dao
interface LinkRepository {
    @Insert
    suspend fun create(link: Link)

    @Query("select * from Link")
    suspend fun readAll(): List<Link>

    @Query("select * from Link where speakerId = :id")
    suspend fun readAllBySpeakerId(id: String): List<Link>

    @Query("select * from Link where id = :id")
    suspend fun readOne(id: String): Link

    @Update
    suspend fun update(link: Link)

    @Delete
    suspend fun delete(link: Link)

    @Query("delete from Link")
    suspend fun deleteAll()

    @Query("delete from Link where speakerId=:speakerId")
    suspend fun deleteBySpeaker(speakerId: String)
}
