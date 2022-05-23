package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.utils.Auth
import cz.cvut.fel.pivchart.exceptions.api.BadRequestException
import cz.cvut.fel.pivchart.model.entity.Gathering
import cz.cvut.fel.pivchart.model.entity.GatheringMember
import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.CreateGatheringRequest
import cz.cvut.fel.pivchart.model.request.UpdateGatheringRequest
import cz.cvut.fel.pivchart.repository.GatheringMemberRepository
import cz.cvut.fel.pivchart.repository.GatheringRepository
import cz.cvut.fel.pivchart.repository.PubRepository
import cz.cvut.fel.pivchart.repository.UserRepository
import cz.cvut.fel.pivchart.service.interfaces.GatheringServiceI
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

/**
 * Gathering service
 */
@Service
class GatheringService(
    private val gatheringRepository: GatheringRepository,
    private val userRepository: UserRepository,
    private val gatheringMemberRepository: GatheringMemberRepository,
    private val pubRepository: PubRepository,
    private val auth: Auth
): GatheringServiceI {

    @Transactional
    override fun getAll(): MutableList<Gathering> {
        return auth.get().gatheringMemberships
            .stream()
            .map { it.gathering }
            .collect(Collectors.toList())
    }

    @Transactional
    override fun get(gatheringId: Long): Gathering {
        return gatheringRepository.getById(gatheringId)
    }

    @Transactional
    override fun create(request: CreateGatheringRequest): Gathering {

        val authUser = auth.get()

        var gathering = Gathering().apply {
            this.title = request.title
            this.pub = pubRepository.getById(request.pubId)
        }
        gathering = gatheringRepository.save(gathering)

        var owner = GatheringMember().apply {
            this.user = authUser
            this.gathering = gathering
            this.isOwner = true
        }
        owner = gatheringMemberRepository.save(owner)

        authUser.gatheringMemberships.add(owner)
        gatheringRepository.save(gathering)
  
        gathering.members.add(owner)
        userRepository.save(authUser)

        applyMembers(gathering, request.memberIds)

        return gatheringRepository.save(gathering)
    }

    @Transactional
    override fun update(gatheringId: Long, request: UpdateGatheringRequest): Gathering {

        val gathering = gatheringRepository.getById(gatheringId)

        if(gathering.isFinished) throw BadRequestException("Unable to edit finished gathering")

        gathering.apply {
            this.title = request.title
        }
        applyMembers(gathering, request.memberIds)

        return gatheringRepository.save(gathering)
    }

    @Transactional
    override fun delete(gatheringId: Long) {
        val toDelete = gatheringRepository.findById(gatheringId).get()
        toDelete.members.forEach {
            it.user.gatheringMemberships.remove(it)
            userRepository.save(it.user)
        }

        gatheringRepository.delete(toDelete)
    }

    @Transactional
    override fun finish(gatheringId: Long): Gathering {
        var gathering = gatheringRepository.getById(gatheringId)
        gathering = gathering.apply {
            isFinished = true
        }

        return gatheringRepository.save(gathering)
    }

    private fun applyMembers(gathering: Gathering, requestMemberIds: Set<Long>) {
        val requestMembers = userRepository.findAllById(requestMemberIds)
        val currentMembers = gathering.members

        val membersToRemove: MutableList<GatheringMember> = membersToRemove(currentMembers, requestMembers)
        val membersToAdd: MutableList<User> = membersToAdd(currentMembers, requestMembers)

        membersToRemove.forEach {
            gathering.members.remove(it)
            it.user.gatheringMemberships.remove(it)
            userRepository.save(it.user)
            gatheringMemberRepository.delete(it)
        }

        membersToAdd.forEach {
            val newMember = GatheringMember().apply {
                this.user = it
                this.gathering = gathering
                this.isOwner = false
            }
            gatheringMemberRepository.save(newMember)
            gathering.members.add(newMember)
            newMember.user.gatheringMemberships.add(newMember)
            userRepository.save(newMember.user)
        }
    }

    private fun membersToRemove(current: MutableList<GatheringMember>, request: MutableList<User>): MutableList<GatheringMember> {
        return current
            .stream()
            .filter{ !request.contains(it.user) && !it.isOwner }
            .collect(Collectors.toList())
    }

    private fun membersToAdd(current: MutableList<GatheringMember>, request: MutableList<User>): MutableList<User> {
        return request
            .stream()
            .filter { requestUser ->
                current
                    .stream()
                    .map { it.user }
                    .noneMatch{ currentUser ->
                        currentUser.id == requestUser.id
                    }
            }.collect(Collectors.toList())
    }
}