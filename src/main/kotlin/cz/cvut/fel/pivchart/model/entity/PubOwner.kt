package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import javax.persistence.*

/**
 * Pub owner entity
 */
@Entity
@Table(name = "pub_owners")
open class PubOwner : AbstractValidEntity<Long>(){

    @Column(name = "bio")
    open lateinit var bio: String

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pubOwner")
    open var pubs: MutableList<Pub> = ArrayList()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    open lateinit var user: User
}