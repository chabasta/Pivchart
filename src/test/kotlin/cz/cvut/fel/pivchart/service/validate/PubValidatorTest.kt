package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.repository.PubOwnerRepository
import cz.cvut.fel.pivchart.repository.PubRepository
import cz.cvut.fel.pivchart.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class PubValidatorTest @Autowired constructor(
    private val pubValidator: PubValidator,
    private val pubRepository: PubRepository,
    private val pubOwnerRepository: PubOwnerRepository,
    private val userRepository: UserRepository
) {
    lateinit var pub: Pub

    @BeforeEach
    fun setUp() {
        val user = userRepository.save(Generator.generateUser())
        val pubOwner = pubOwnerRepository.save(Generator.generateOwner(user))

        pub = pubRepository.save(Generator.generatePub(pubOwner))
    }

    @Test
    fun testValidatePubExists_Ok() {
        assertDoesNotThrow {
            pubValidator.validatePubExists(pub.id!!)
        }
    }

    @Test
    fun testValidatePubDoesNotExist_EntityNotFoundException() {
        assertThrows<NotFoundException> {
            pubValidator.validatePubExists(420)
        }
    }
}