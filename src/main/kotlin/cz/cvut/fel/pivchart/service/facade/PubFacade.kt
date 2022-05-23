package cz.cvut.fel.pivchart.service.facade

import cz.cvut.fel.pivchart.utils.PubUtils
import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.request.CreatePubRequest
import cz.cvut.fel.pivchart.model.request.IndexPubsRequest
import cz.cvut.fel.pivchart.model.request.UpdatePubRequest
import cz.cvut.fel.pivchart.model.response.PubResponse
import cz.cvut.fel.pivchart.service.interfaces.PubServiceI
import cz.cvut.fel.pivchart.service.validate.PubValidator
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * Pub service facade
 */
@Service
class PubFacade(
    private val pubService: PubServiceI,
    private val pubValidator: PubValidator
) {
    fun getAll(request: IndexPubsRequest): List<PubResponse> {
        pubValidator.validateOnGetAll()
        val pubs = pubService.getAll(request)

        return pubs
            .stream()
            .map { preparePubResponse(it) }
            .collect(Collectors.toList())
    }

    fun get(pubId: Long): PubResponse {
        pubValidator.validateOnGet(pubId)
        val pub = pubService.get(pubId)

        return preparePubResponse(pub)
    }

    fun create(request: CreatePubRequest): PubResponse {
        pubValidator.validateOnCreate(request)
        val pub = pubService.create(request)

        return preparePubResponse(pub)
    }

    fun update(pubId: Long, request: UpdatePubRequest): PubResponse {
        pubValidator.validateOnUpdate(pubId, request)
        val pub = pubService.update(pubId, request)

        return preparePubResponse(pub)
    }

    fun delete(pubId: Long) {
        pubService.delete(pubId)
    }

    private fun preparePubResponse(pub: Pub): PubResponse {
        return PubUtils.preparePubResponse(pub)
    }
}