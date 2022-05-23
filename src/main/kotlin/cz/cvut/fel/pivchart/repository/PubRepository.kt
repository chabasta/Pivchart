package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.Pub
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Pub repository
 */
@Repository
interface PubRepository : JpaRepository<Pub, Long>, PubRepositoryFilterable<Pub, Long>