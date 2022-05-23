package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.model.entity.*
import cz.cvut.fel.pivchart.repository.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class GatheringMemberValidatorTest @Autowired constructor(
        private val gatheringMemberValidator: GatheringMemberValidator,
        private val gatheringMemberRepository: GatheringMemberRepository,
        private val pubRepository: PubRepository,
        private val pubOwnerRepository: PubOwnerRepository,
        private val gatheringRepository: GatheringRepository,
        private val userRepository: UserRepository
){

    lateinit var gathering: Gathering
    lateinit var gatheringMember: GatheringMember
    lateinit var otherUnknownGathering: Gathering

    @BeforeEach
    fun setUp(){
        val user = userRepository.save(Generator.generateUser())
        val pubOwner = pubOwnerRepository.save(Generator.generateOwner(user))
        val pub = pubRepository.save(Generator.generatePub(pubOwner))
        gathering = gatheringRepository.save(Generator.generateGathering(pub))
        otherUnknownGathering = gatheringRepository.save(Generator.generateGathering(pub))
        gatheringMember = Generator.generateGatheringMember(user)
    }

    @Test
    fun testValidateOnGetGatheringMember_OK(){
        gatheringMember.gathering = gathering
        gatheringMember = gatheringMemberRepository.save(gatheringMember)
        gathering.members.add(gatheringMember)
        gathering = gatheringRepository.save(gathering)

        assertDoesNotThrow {
            gatheringMemberValidator.validateOnGetAll(gatheringMember.gathering.id!!)
        }
    }

    @Test
    fun testValidateOnGetGatheringMember_GatheringDoesNotExists() {
        assertThrows<NotFoundException> {
            gatheringMemberValidator.validateOnGetAll(123)
        }
    }
}