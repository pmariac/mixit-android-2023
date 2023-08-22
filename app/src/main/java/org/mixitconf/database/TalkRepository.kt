package org.mixitconf.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.mixitconf.model.Talk

@Dao
interface TalkRepository {
    @Insert
    suspend fun create(talk: Talk)

    @Query("select * from Talk")
    fun readAll(): Flow<List<Talk>>

    @Query("select * from Talk where favorite = 1")
    suspend fun readFavorites(): List<Talk>

    @Query("select * from Talk where speakerIds like '%' || :id || '%'")
    suspend fun readAllBySpeakerId(id: String): List<Talk>

    @Query("select * from Talk where id=:id")
    suspend fun readOne(id: Long): Talk?

    @Update
    suspend fun update(talk: Talk)

    @Delete
    suspend fun delete(talk: Talk)

    @Query("delete from Talk")
    suspend fun deleteAll()

    @Query("delete from Talk where id in (:ids)")
    suspend fun deleteAllById(ids: List<Long>)
}
