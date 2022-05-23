package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import javax.persistence.*

/**
 * Gathering member entity
 */
@Entity
@Table(name = "gathering_members")
open class GatheringMember : AbstractValidEntity<Long>() {

    @Column(name = "is_owner", nullable = false)
    open var isOwner = false

    @Column(name = "approved")
    open var approved = false

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "gathering_id")
    open lateinit var gathering: Gathering

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    open lateinit var user: User
}