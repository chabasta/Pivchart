package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.model.request.CreateDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdateDrinkRequest
import cz.cvut.fel.pivchart.repository.DrinkRepository
import org.springframework.stereotype.Component

/**
 * Drink validation
 */
@Component
class DrinkValidator(
    val drinkRepository: DrinkRepository
) {
    fun validateOnGet(drinkId: Long) {
        validateDrinkExists(drinkId)
    }

    fun validateOnCreate(request: CreateDrinkRequest) {
        // nothing to check
    }

    fun validateOnUpdate(drinkId: Long, request: UpdateDrinkRequest) {
        validateDrinkExists(drinkId)
    }

    fun validateOnDelete(id: Long) {
        validateDrinkExists(id)
    }

    /**
     * @param drinkId is Id of the drink
     *
     * Validate if drink exists. If it doesn't exist -> Exception
     */
    @Throws(NotFoundException::class)
    fun validateDrinkExists(drinkId: Long) {
        if (!drinkRepository.existsById(drinkId)) throw NotFoundException("Drink doesnt exit.")
    }
}