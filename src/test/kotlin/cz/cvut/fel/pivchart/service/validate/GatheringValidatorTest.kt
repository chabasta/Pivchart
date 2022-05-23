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
class GatheringValidatorTest @Autowired constructor(
        private val gatheringValidator: GatheringValidator,
        private val pubRepository: PubRepository,
        private val pubOwnerRepository: PubOwnerRepository,
        private val gatheringRepository: GatheringRepository,
        private val userRepository: UserRepository
) {

    lateinit var gathering: Gathering

    @BeforeEach
    fun setUp() {
        val user = userRepository.save(Generator.generateUser())
        val pubOwner = pubOwnerRepository.save(Generator.generateOwner(user))
        val pub = pubRepository.save(Generator.generatePub(pubOwner))
        gathering = Generator.generateGathering(pub)
    }

    @Test
    fun testValidateGatheringExist_Ok(){
        gathering = gatheringRepository.save(gathering)

        assertDoesNotThrow { gatheringValidator.validateOnGet(gathering.id!!)}
    }

    @Test
    fun testValidateGathering_gatheringDouesNotExist_NotFoundException() {
        assertThrows<NotFoundException> {
            gatheringValidator.validateOnGet(123)
        }
    }
}