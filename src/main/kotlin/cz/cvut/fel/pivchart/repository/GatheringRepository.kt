package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.Gathering
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Gathering repository
 */
@Repository
interface GatheringRepository : JpaRepository<Gathering, Long>