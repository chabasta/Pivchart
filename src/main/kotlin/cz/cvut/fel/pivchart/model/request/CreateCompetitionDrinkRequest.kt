package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Required attributes in json format to create competition drink
 */
data class CreateCompetitionDrinkRequest(
    @JsonProperty("pubDrinkId")
    val pubDrinkId: Long
)