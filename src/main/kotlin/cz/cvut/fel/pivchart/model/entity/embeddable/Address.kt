package cz.cvut.fel.pivchart.model.entity.embeddable

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class Address {

    @Column(name = "street", nullable = false)
    open lateinit var street: String

    @Column(name = "city", nullable = false)
    open lateinit var city: String

    @Column(name = "postal_code", nullable = false)
    open lateinit var postalCode: String

    @Column(name = "country", nullable = false)
    open lateinit var country: String
}