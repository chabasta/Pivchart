package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.exceptions.api.BadRequestException
import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.entity.PubDrink
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
class PubDrinkValidatorTest @Autowired constructor(
    private val pubDrinkValidator: PubDrinkValidator,
    private val pubRepository: PubRepository,
    private val pubDrinkRepository: PubDrinkRepository,
    private val pubOwnerRepository: PubOwnerRepository,
    private val userRepository: UserRepository,
    private val drinkRepository: DrinkRepository
) {
    lateinit var pub: Pub
    lateinit var otherUnknownPub: Pub
    lateinit var pubDrink: PubDrink

    @BeforeEach
    fun setUp() {
        val user = userRepository.save(Generator.generateUser())
        val pubOwner = pubOwnerRepository.save(Generator.generateOwner(user))
        val drink = drinkRepository.save(Generator.generateDrink())

        pub = pubRepository.save(Generator.generatePub(pubOwner))
        otherUnknownPub = pubRepository.save(Generator.generatePub(pubOwner))

        pubDrink = Generator.generatePubDrink().apply {
            this.drink = drink
        }
    }

    @Test
    fun testValidateOnGetPubDrink_pubDrinkBelongsToPub_Ok() {
        pubDrink.pub = pub
        pubDrink = pubDrinkRepository.save(pubDrink)
        pub.pubDrinks.add(pubDrink)
        pubRepository.save(pub)

        assertDoesNotThrow {
            pubDrinkValidator.validateOnGet(pub.id!!, pubDrink.id!!)
        }
    }

    @Test
    fun testValidateOnGetPubDrink_pubDrinkDoesNotBelongToPub_BadRequestException() {
        pubDrink.pub = otherUnknownPub
        pubDrink = pubDrinkRepository.save(pubDrink)

        assertThrows<BadRequestException> {
            pubDrinkValidator.validateOnGet(pub.id!!, pubDrink.id!!)
        }
    }
}