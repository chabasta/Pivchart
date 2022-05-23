package cz.cvut.fel.pivchart.model.response

/**
 * Drink response in json format
 */
data class DrinkResponse(
    val id: Long,
    val name: String,
    val alcoholVolume: Double
)