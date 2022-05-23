package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to update pub drink
 */
data class UpdatePubDrinkRequest(
    @JsonProperty("price")
    val price: Double,
    @JsonProperty("volume")
    val volume: Int,
    @JsonProperty("drinkId")
    val drinkId: Long
)