package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.GatheringDrankDrink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Gathering drink repository
 */
@Repository
interface GatheringDrinkRepository : JpaRepository<GatheringDrankDrink, Long>