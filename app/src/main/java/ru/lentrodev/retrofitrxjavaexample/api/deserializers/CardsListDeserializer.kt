package ru.lentrodev.retrofitrxjavaexample.api.deserializers

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.lentrodev.retrofitrxjavaexample.db.model.Card
import java.lang.reflect.Type

/**
 * Created by Igor Gusakov on 07.07.2021.
 */
class CardsListDeserializer: JsonDeserializer<List<Card>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Card> {
        val listOfCards = json?.asJsonObject?.get("cards")?.asJsonArray
        val result = mutableListOf<Card>()
        if (listOfCards != null) {
            for (obj in listOfCards) {
                result.add(Gson().fromJson(obj, Card::class.java))
            }
        }
        return result

    }
}