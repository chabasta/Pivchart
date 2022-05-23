package cz.cvut.fel.pivchart.model.response

/**
 * Pub response in json format
 */
data class PubResponse(
    val id: Long?,
    val name: String,
    val city: String,
    val street: String,
    val country: String,
    val postalCode: String,
    val latitude: Double?,
    val longitude: Double?
)
