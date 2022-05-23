package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.CompetitionDrankDrink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Competition drink repository
 */
@Repository
interface CompetitionDrinkRepository: JpaRepository<CompetitionDrankDrink, Long> {
}