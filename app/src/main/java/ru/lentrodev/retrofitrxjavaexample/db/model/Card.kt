package ru.lentrodev.retrofitrxjavaexample.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Igor Gusakov on 06.07.2021.
 */

@Entity
class Card {

    @PrimaryKey
    @SerializedName("id")
    var id: String = ""

    @ColumnInfo(name = "card_name")
    @SerializedName("name")
    var cardName: String = ""

    @ColumnInfo(name = "card_author")
    @SerializedName("artist")
    var cardAuthor: String = ""

    @ColumnInfo(name = "card_image")
    @SerializedName("imageUrl")
    var cardImageUrl: String = ""

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Card) {
            return other.id == this.id
        }
        return false
    }

}