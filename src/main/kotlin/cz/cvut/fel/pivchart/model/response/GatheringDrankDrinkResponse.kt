package cz.cvut.fel.pivchart.model.response

import java.time.LocalDateTime

/**
 * Gathering drank drink response in json format
 */
data class GatheringDrankDrinkResponse(
        val id: Long,
        val createdAt: LocalDateTime,
        val drankBy: UserResponse,
        val pubDrink: PubDrinkResponse
)
