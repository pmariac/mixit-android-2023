package org.mixitconf.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mixitconf.model.User

@Dao
interface UserRepository {

    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun getUserById(userId: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user")
    suspend fun clearAll()
}