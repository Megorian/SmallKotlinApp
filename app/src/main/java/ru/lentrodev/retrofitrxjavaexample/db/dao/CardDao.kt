package ru.lentrodev.retrofitrxjavaexample.db.dao

import androidx.room.*
import ru.lentrodev.retrofitrxjavaexample.db.model.Card
import ru.lentrodev.retrofitrxjavaexample.db.model.CardSet

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBatch(cards: List<Card>?)

    @Query("SELECT * FROM card WHERE id = :id")
    fun loadById(id: Long): Card?

    @Query("SELECT * FROM card")
    fun loadAll(): List<Card>

    @Delete
    fun delete(role: Card?)
}