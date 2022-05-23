package cz.cvut.fel.pivchart.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import cz.cvut.fel.pivchart.model.entity.enum.CompetitionEndType
import java.time.LocalDateTime

/**
 * Requires attributes in json format to create competition
 */
data class CreateCompetitionRequest (
    @JsonProperty("title")
    val title: String,
    @JsonProperty("startDateTime")
    val startDateTime: LocalDateTime?,
    @JsonProperty("endDateTime")
    val endDateTime: LocalDateTime?,
    @JsonProperty("endType")
    val endType: CompetitionEndType,
    @JsonProperty("amount")
    val amount: Int?,
    @JsonProperty("pubId")
    val pubId: Long,
    @JsonProperty("teams")
    val teams: List<TeamRequest>
)