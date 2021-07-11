package ru.lentrodev.retrofitrxjavaexample.db.dao

import androidx.room.*
import ru.lentrodev.retrofitrxjavaexample.db.model.User

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User?)

    @Delete
    fun delete(proj: User?)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun loadById(id: Long): User?

    @Query("SELECT * FROM user ORDER BY id DESC")
    suspend fun loadAll(): List<User>?
}