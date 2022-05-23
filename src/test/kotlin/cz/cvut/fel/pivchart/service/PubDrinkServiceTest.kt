package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.model.entity.*
import cz.cvut.fel.pivchart.repository.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class PubDrinkServiceTest @Autowired constructor(
    private val pubDrinkRepository: PubDrinkRepository,
    private val pubRepository: PubRepository,
    private val userRepository: UserRepository,
    private val pubOwnerRepository: PubOwnerRepository,
    private val pubDrinkService: PubDrinkService,
    private val drinkRepository: DrinkRepository
) {
    lateinit var pub: Pub
    lateinit var user: User
    lateinit var owner: PubOwner
    lateinit var drink: Drink

    @BeforeEach
    fun setUp() {
        user = userRepository.save(Generator.generateUser())
        owner = pubOwnerRepository.save(Generator.generateOwner(user))
        pub = pubRepository.save(Generator.generatePub(owner))
        drink = drinkRepository.save(Generator.generateDrink())
    }

    @Test
    fun testCreatePubDrink() {
        val request = Generator.generatePubDrinkRequest(drink.id!!)

        val result = pubDrinkService.create(pub.id!!, request)

        // Assertion
        assertAll({
            assertEquals(drink.id, result.drink.id)
            assertEquals(drink.alcoholVolume, result.drink.alcoholVolume)
            assertEquals(drink.name, result.drink.name)
        })
    }
    @Test
    fun testDeletePubDrink_DoesNotExistsInDatabase() {
        var pubDrink = Generator.generatePubDrink()
        pubDrink.drink = drink
        pubDrink.pub = pub
        pubDrink = pubDrinkRepository.save(pubDrink)

        pubDrinkService.delete(pub.id!!, pubDrink.id!!)

        assertFalse(pubDrinkRepository.existsById(pubDrink.id!!))
    }

    @Test
    fun testCreateAndUpdate_ReturnsUpdatePubDrink() {
        var pubDrink = Generator.generatePubDrink()
        pubDrink.drink = drink
        pubDrink.pub = pub
        pubDrink = pubDrinkRepository.save(pubDrink)
        val request = Generator.generateUpdatePubDrinkRequest(drink.id!!)

        val updated = pubDrinkService.update(pub.id!!, pubDrink.id!!, request)

        assertAll({
            assertEquals(request.price, updated.price)
            assertEquals(request.volume, updated.volume)
        })
    }
}