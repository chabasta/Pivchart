package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.UserContextSetter
import cz.cvut.fel.pivchart.model.entity.Gathering
import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.entity.PubOwner
import cz.cvut.fel.pivchart.model.entity.User
import cz.cvut.fel.pivchart.model.request.CreateGatheringRequest
import cz.cvut.fel.pivchart.repository.GatheringRepository
import cz.cvut.fel.pivchart.repository.PubOwnerRepository
import cz.cvut.fel.pivchart.repository.PubRepository
import cz.cvut.fel.pivchart.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class GatheringServiceTest @Autowired constructor(
    private val gatheringRepository: GatheringRepository,
    private val userRepository: UserRepository,
    private val pubOwnerRepository: PubOwnerRepository,
    private val pubRepository: PubRepository,
    private val gatheringService: GatheringService
) {
    lateinit var pub: Pub
    lateinit var user: User
    lateinit var pubOwner: PubOwner

    @BeforeEach
    fun setUp() {
        user = userRepository.save(Generator.generateUser())
        pubOwner = pubOwnerRepository.save(Generator.generateOwner(user))
        pub = pubRepository.save(Generator.generatePub(pubOwner))
    }

    @Test
    fun deleteGathering_DoesNotExist() {
        UserContextSetter.setUser(user.email)
        val gathering = gatheringRepository.save(Generator.generateGathering(pub))

        gatheringService.delete(gathering.id!!)

        Assertions.assertFalse(gatheringRepository.existsById(gathering.id!!))
    }
}