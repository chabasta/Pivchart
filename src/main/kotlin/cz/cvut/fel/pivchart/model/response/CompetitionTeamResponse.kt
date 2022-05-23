package cz.cvut.fel.pivchart.model.response

/**
 * Competition team response in json format
 */
data class CompetitionTeamResponse(
    val id: Long,
    val title: String,
    val competitionId: Long,
    val members: List<CompetitionMemberResponse>
)