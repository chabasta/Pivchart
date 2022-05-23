package cz.cvut.fel.pivchart.model.response

/**
 * Competition drank drink response in json format
 */
data class CompetitionDrankDrinkResponse(
    val id: Long,
    val createdAt: String,
    val addedBy: UserResponse,
    val pubDrink: PubDrinkResponse
)