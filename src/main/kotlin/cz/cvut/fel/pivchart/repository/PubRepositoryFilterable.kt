package cz.cvut.fel.pivchart.repository

import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.request.IndexPubsRequest
import org.springframework.stereotype.Repository

/**
 * Pub filter repository
 */
@Repository
interface PubRepositoryFilterable<T, ID> {
    fun filter(request: IndexPubsRequest): MutableList<Pub>
}