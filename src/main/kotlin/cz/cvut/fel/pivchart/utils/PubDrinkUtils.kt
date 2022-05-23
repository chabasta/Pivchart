package cz.cvut.fel.pivchart.utils

import cz.cvut.fel.pivchart.model.entity.PubDrink
import cz.cvut.fel.pivchart.model.response.PubDrinkResponse

object PubDrinkUtils {
    fun preparePubDrinkResponse(pubDrink: PubDrink) : PubDrinkResponse {
        return PubDrinkResponse(
                id = pubDrink.id!!,
                drink = DrinkUtils.prepareDrinkResponse(pubDrink.drink),
                pub = PubUtils.preparePubResponse(pubDrink.pub),
                price = pubDrink.price!!,
                volume = pubDrink.volume!!
        )
    }
}