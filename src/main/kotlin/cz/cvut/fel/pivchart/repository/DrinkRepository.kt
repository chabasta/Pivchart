package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.Drink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Drinks repository
 */
@Repository
interface DrinkRepository : JpaRepository<Drink, Long>