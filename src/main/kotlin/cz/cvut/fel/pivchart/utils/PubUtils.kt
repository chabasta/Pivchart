package cz.cvut.fel.pivchart.utils

import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.response.PubResponse

object PubUtils {
    fun preparePubResponse(pub: Pub): PubResponse {
        return PubResponse(
            id = pub.id,
            name = pub.name,
            city = pub.address.city,
            street = pub.address.street,
            country = pub.address.country,
            postalCode = pub.address.postalCode,
            latitude = pub.latitude,
            longitude = pub.longitude
        )
    }
}