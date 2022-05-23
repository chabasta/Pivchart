package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.Competition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Competition repository
 */
@Repository
interface CompetitionRepository: JpaRepository<Competition, Long>