package cz.cvut.fel.pivchart.utils

import cz.cvut.fel.pivchart.model.entity.Drink
import cz.cvut.fel.pivchart.model.response.DrinkResponse

object DrinkUtils {
    fun prepareDrinkResponse(drink: Drink): DrinkResponse {
        return DrinkResponse(
            id = drink.id!!,
            name = drink.name,
            alcoholVolume = drink.alcoholVolume!!
        )
    }
}