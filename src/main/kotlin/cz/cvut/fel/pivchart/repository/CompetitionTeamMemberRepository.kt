package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.CompetitionTeamMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Competition team member repository
 */
@Repository
interface CompetitionTeamMemberRepository : JpaRepository<CompetitionTeamMember, Long> {
    fun getByUserId(userId: Long): CompetitionTeamMember?
}