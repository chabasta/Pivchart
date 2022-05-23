package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import javax.persistence.*

/**
 * Drank drink abstract entity
 */
@Entity(name="drank_drinks")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="drank_drink_type", discriminatorType = DiscriminatorType.STRING)
abstract class DrankDrink : AbstractValidEntity<Long>() {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    open lateinit var user: User

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pub_drink_id")
    open lateinit var pubDrink: PubDrink
}