package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.DrinkUtils
import cz.cvut.fel.pivchart.model.entity.Drink
import cz.cvut.fel.pivchart.model.request.CreateDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdateDrinkRequest
import cz.cvut.fel.pivchart.model.response.DrinkResponse
import cz.cvut.fel.pivchart.service.interfaces.DrinkServiceI
import cz.cvut.fel.pivchart.service.validate.DrinkValidator
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Drink service facade
 */
@Service
class DrinkFacade(
    private val drinkService: DrinkServiceI,
    private val drinkValidator: DrinkValidator
) {
    fun getAll(): MutableList<DrinkResponse> {
        val drinks = drinkService.getAll()

        return drinks
            .stream()
            .map { prepareDrinkResponse(it) }
            .collect(Collectors.toList())
    }

    fun get(id: Long): DrinkResponse {
        drinkValidator.validateOnGet(id)
        val drink = drinkService.get(id)

        return prepareDrinkResponse(drink)
    }

    fun create(request: CreateDrinkRequest): DrinkResponse {
        drinkValidator.validateOnCreate(request)
        val drink = drinkService.create(request)

        return prepareDrinkResponse(drink)
    }

    fun update(id: Long, request: UpdateDrinkRequest): DrinkResponse {
        drinkValidator.validateOnUpdate(id, request)
        val drink = drinkService.update(id, request)

        return prepareDrinkResponse(drink)
    }

    fun delete(id: Long) {
        drinkValidator.validateOnDelete(id)
        drinkService.delete(id)
    }

    private fun prepareDrinkResponse(drink: Drink): DrinkResponse {
        return DrinkUtils.prepareDrinkResponse(drink)
    }
}