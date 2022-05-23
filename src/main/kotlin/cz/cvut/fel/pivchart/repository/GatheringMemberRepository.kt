package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.GatheringMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Gathering member repository
 */
@Repository
interface GatheringMemberRepository : JpaRepository<GatheringMember, Long> {
    fun getByUserId(userId: Long): GatheringMember?
}