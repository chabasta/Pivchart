package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to create pub drink
 */
data class CreatePubDrinkRequest(
    @JsonProperty("drinkId")
    val drinkId: Long,
    @JsonProperty("price")
    val price: Double,
    @JsonProperty("volume")
    val volume: Int
)
