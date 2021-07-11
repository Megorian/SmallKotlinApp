package ru.lentrodev.retrofitrxjavaexample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.lentrodev.retrofitrxjavaexample.db.dao.CardDao
import ru.lentrodev.retrofitrxjavaexample.db.dao.CardSetDao
import ru.lentrodev.retrofitrxjavaexample.db.model.Card
import ru.lentrodev.retrofitrxjavaexample.db.model.CardSet

/**
 * Created by Igor Gusakov on 06.07.2021.
 */
@Database(
    entities = [Card::class, CardSet::class],
    version = 1
)
 abstract class RoomDB: RoomDatabase() {

    abstract fun cardDao(): CardDao?
    abstract fun cardSetDao(): CardSetDao?

}