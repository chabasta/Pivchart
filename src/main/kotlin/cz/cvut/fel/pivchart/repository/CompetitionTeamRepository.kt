package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.CompetitionTeam
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Competition team repository
 */
@Repository
interface CompetitionTeamRepository: JpaRepository<CompetitionTeam, Long> {
    fun findByTitle(title: String): CompetitionTeam?
}