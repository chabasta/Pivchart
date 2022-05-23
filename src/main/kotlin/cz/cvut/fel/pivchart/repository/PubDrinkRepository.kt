package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.PubDrink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Pub drink repository
 */
@Repository
interface PubDrinkRepository : JpaRepository<PubDrink, Long>