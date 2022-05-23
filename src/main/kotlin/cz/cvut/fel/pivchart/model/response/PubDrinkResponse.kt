package cz.cvut.fel.pivchart.model.response

/**
 * Pub drink response in json format
 */
data class PubDrinkResponse(
    val id: Long,
    val drink: DrinkResponse,
    val pub: PubResponse,
    val price: Double,
    val volume: Int
)
