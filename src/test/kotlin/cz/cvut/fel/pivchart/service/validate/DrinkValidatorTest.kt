package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.model.entity.Drink
import cz.cvut.fel.pivchart.repository.DrinkRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class DrinkValidatorTest @Autowired constructor(
    private val drinkValidator: DrinkValidator,
    private val drinkRepository: DrinkRepository
) {
    lateinit var drink: Drink

    @BeforeEach
    fun setUp() {
        drink = drinkRepository.save(Generator.generateDrink())
    }

    @Test
    fun testValidateDrinkExists_drinkExists_ok() {
        assertDoesNotThrow { drinkValidator.validateDrinkExists(drink.id!!) }
    }

    @Test
    fun testValidateDrinkExists_drinkDoesNotExist_NotFoundException() {
        assertThrows<NotFoundException> { drinkValidator.validateDrinkExists(420) }
    }
}