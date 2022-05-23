package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.model.entity.Drink
import cz.cvut.fel.pivchart.model.request.CreateDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdateDrinkRequest
import cz.cvut.fel.pivchart.repository.DrinkRepository
import cz.cvut.fel.pivchart.service.interfaces.DrinkServiceI
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Drink service
 */
@Service
class DrinkService(
    private val drinkRepository: DrinkRepository
): DrinkServiceI {
    @Transactional
    override fun getAll(): MutableList<Drink> {
        return drinkRepository.findAll()
    }

    @Transactional
    override fun get(id: Long): Drink {
        return drinkRepository.getById(id)
    }

    @Transactional
    override fun create(request: CreateDrinkRequest): Drink {
        val drink = Drink().apply {
            name = request.name
            alcoholVolume = request.alcoholVolume
        }

        return drinkRepository.save(drink)
    }

    @Transactional
    override fun update(id: Long, request: UpdateDrinkRequest): Drink {
        val drink = drinkRepository.getById(id)
        drink.apply {
            name = request.name
            alcoholVolume = request.alcoholVolume
        }

        return drinkRepository.save(drink)
    }

    @Transactional
    override fun delete(id: Long) {
        drinkRepository.deleteById(id)
    }
}