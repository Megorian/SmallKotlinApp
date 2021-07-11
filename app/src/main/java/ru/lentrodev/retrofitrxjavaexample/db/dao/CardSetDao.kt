package ru.lentrodev.retrofitrxjavaexample.db.dao

import androidx.room.*
import ru.lentrodev.retrofitrxjavaexample.db.model.CardSet

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
@Dao
interface CardSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cardSet: CardSet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBatch(cardSets: List<CardSet>?)

    @Query("SELECT * FROM cardset WHERE setId = :id")
    fun loadById(id: Long): CardSet?

    @Query("SELECT * FROM cardset")
    fun loadAll(): List<CardSet>

    @Delete
    fun delete(role: CardSet?)
}