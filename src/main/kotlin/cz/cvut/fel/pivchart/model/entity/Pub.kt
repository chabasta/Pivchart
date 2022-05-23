package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import cz.cvut.fel.pivchart.model.entity.embeddable.Address
import javax.persistence.*

/**
 * Pub entity
 */
@Entity
@Table(name = "pubs")
open class Pub : AbstractValidEntity<Long>() {

    @Column(name = "name", length = 128, nullable = false)
    open lateinit var name: String

    @Embedded
    open lateinit var address: Address

    @Column(name = "latitude")
    open var latitude: Double? = null

    @Column(name = "longitude")
    open var longitude: Double? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pub_owner_id")
    open lateinit var pubOwner: PubOwner

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pub")
    open var competitions: MutableList<Competition> = ArrayList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pub")
    open var gatherings: MutableList<Gathering> = ArrayList()

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pub")
    open var pubDrinks: MutableList<PubDrink> = ArrayList()
}