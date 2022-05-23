package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.exceptions.api.BadRequestException
import cz.cvut.fel.pivchart.model.entity.Gathering
import cz.cvut.fel.pivchart.model.entity.GatheringMember
import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.CreateGatheringMemberRequest
import cz.cvut.fel.pivchart.repository.GatheringMemberRepository
import cz.cvut.fel.pivchart.repository.GatheringRepository
import cz.cvut.fel.pivchart.repository.UserRepository
import cz.cvut.fel.pivchart.service.interfaces.GatheringMemberServiceI
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Gathering member service
 */
@Service
class GatheringMemberService(
    private val userRepository: UserRepository,
    private val gatheringRepository: GatheringRepository,
    private val gatheringMemberRepository: GatheringMemberRepository
): GatheringMemberServiceI {
    @Transactional
    override fun getAll(gatheringId: Long): MutableList<GatheringMember> {
        return gatheringRepository.getById(gatheringId).members
    }

    @Transactional
    override fun create(gatheringId: Long, request: CreateGatheringMemberRequest): GatheringMember {
        val gathering = gatheringRepository.getById(gatheringId)
        val user = userRepository.getById(request.userId)

        val gatheringMember = gatheringMemberRepository.getByUserId(request.userId) ?: createGatheringMember(gathering, user)

        if (!gatheringMember.approved) {
            gatheringMember.approved = true
        } else {
            throw BadRequestException("Already member")
        }

        return gatheringMember
    }

    @Transactional
    override fun delete(gatheringId: Long, gatheringMemberId: Long) {
        gatheringMemberRepository.deleteById(gatheringMemberId)
    }

    private fun createGatheringMember(gathering: Gathering, user: User): GatheringMember {
        val gatheringMember = GatheringMember().apply {
            this.gathering = gathering
            this.user = user
        }
        return gatheringMemberRepository.save(gatheringMember)
    }
}