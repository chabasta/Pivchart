package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.PubDrinkUtils
import cz.cvut.fel.pivchart.model.entity.PubDrink
import cz.cvut.fel.pivchart.model.request.CreatePubDrinkRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubDrinkRequest
import cz.cvut.fel.pivchart.model.response.PubDrinkResponse
import cz.cvut.fel.pivchart.service.interfaces.PubDrinkServiceI
import cz.cvut.fel.pivchart.service.validate.PubDrinkValidator
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Pub drink service facade
 */
@Service
class PubDrinkFacade(
    private val pubDrinkService: PubDrinkServiceI,
    private val pubDrinkValidator: PubDrinkValidator
) {
    fun getAll(pubId: Long): MutableList<PubDrinkResponse> {
        pubDrinkValidator.validateOnGetAll(pubId)
        val pubDrinks = pubDrinkService.getAll(pubId)

        return pubDrinks
            .stream()
            .map { preparePubDrinkResponse(it) }
            .collect(Collectors.toList())
    }

    fun get(pubId: Long, pubDrinkId: Long): PubDrinkResponse {
        pubDrinkValidator.validateOnGet(pubId, pubDrinkId)
        val pubDrink = pubDrinkService.get(pubId, pubDrinkId)

        return preparePubDrinkResponse(pubDrink)
    }

    fun create(pubId: Long, request: CreatePubDrinkRequest): PubDrinkResponse {
        pubDrinkValidator.validateOnCreate(pubId, request)
        val pubDrink = pubDrinkService.create(pubId, request)

        return preparePubDrinkResponse(pubDrink)
    }

    fun update(pubId: Long, pubDrinkId: Long, request: UpdatePubDrinkRequest): PubDrinkResponse {
        pubDrinkValidator.validateOnUpdate(pubId, pubDrinkId, request)
        val pubDrink = pubDrinkService.update(pubId, pubDrinkId, request)

        return preparePubDrinkResponse(pubDrink)
    }

    fun delete(pubId: Long, pubDrinkId: Long) {
        pubDrinkValidator.validateOnDelete(pubId, pubDrinkId)
        pubDrinkService.delete(pubId, pubDrinkId)
    }

    private fun preparePubDrinkResponse(pubDrink: PubDrink): PubDrinkResponse {
        return PubDrinkUtils.preparePubDrinkResponse(pubDrink)
    }
}