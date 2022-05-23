package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.model.entity.PubDrink
import cz.cvut.fel.pivchart.model.request.CreatePubDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubDrinkRequest
import cz.cvut.fel.pivchart.repository.DrinkRepository
import cz.cvut.fel.pivchart.repository.PubDrinkRepository
import cz.cvut.fel.pivchart.repository.PubRepository
import cz.cvut.fel.pivchart.service.interfaces.PubDrinkServiceI
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Pub drink service
 */
@Service
class PubDrinkService(
    private val pubDrinkRepository: PubDrinkRepository,
    private val pubRepository: PubRepository,
    private val drinkRepository: DrinkRepository
): PubDrinkServiceI {
    @Transactional
    override fun getAll(pubId: Long): MutableList<PubDrink> {
        return pubRepository.getById(pubId).pubDrinks
    }

    @Transactional
    override fun get(pubId: Long, pubDrinkId: Long): PubDrink {
        return pubDrinkRepository.getById(pubDrinkId)
    }

    @Transactional
    override fun create(pubId: Long, request: CreatePubDrinkRequest): PubDrink {
        val pub = pubRepository.getById(pubId)

        var pubDrink = PubDrink().apply {
            this.pub = pub
            this.drink = drinkRepository.getById(request.drinkId)
            this.price = request.price
            this.volume = request.volume
        }
        pubDrink = pubDrinkRepository.save(pubDrink)

        pubRepository.save(pub.apply {
            this.pubDrinks.add(pubDrink)
        })

        return pubDrink
    }

    @Transactional
    override fun update(pubId: Long, pubDrinkId: Long, request: UpdatePubDrinkRequest): PubDrink {

        val pubDrink = pubDrinkRepository.getById(pubDrinkId)
        pubDrink.apply {
            this.drink = drinkRepository.getById(request.drinkId)
            this.price = request.price
            this.volume = request.volume
        }

        return pubDrinkRepository.save(pubDrink)
    }

    @Transactional
    override fun delete(pubId: Long, pubDrinkId: Long) {
        pubDrinkRepository.deleteById(pubDrinkId)
    }
}