package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Requires attributes in json format to create gathering drink
 */
data class CreateGatheringDrinkRequest(
    @JsonProperty("pubDrinkId")
    val pubDrinkId: Long
)