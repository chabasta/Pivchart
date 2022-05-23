package cz.cvut.fel.pivchart.utils

import cz.cvut.fel.pivchart.model.entity.CompetitionTeamMember
import cz.cvut.fel.pivchart.model.response.CompetitionMemberResponse

object CompetitionMemberUtils {
    fun prepareMemberResponse(member: CompetitionTeamMember): CompetitionMemberResponse {
        return CompetitionMemberResponse(
            isCaptain = member.isCaptain,
            user = UserUtils.prepareUserResponse(member.user)
        )
    }
}