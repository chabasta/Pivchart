package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.PubOwner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Pub owner repository
 */
@Repository
interface PubOwnerRepository : JpaRepository<PubOwner, Long>