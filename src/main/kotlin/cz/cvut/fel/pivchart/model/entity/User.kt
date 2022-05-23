package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import cz.cvut.fel.pivchart.model.entity.enum.Sex
import java.time.LocalDateTime
import javax.persistence.*

/**
 * User entity
 */
@Entity
@Table(name = "users")
open class User : AbstractValidEntity<Long>() {
    @Column(name = "username", length = 128)
    open lateinit var username: String

    @Column(name = "email", unique = true)
    open lateinit var email: String

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    open var sex: Sex? = null

    @Column(name = "birth_date")
    open lateinit var birthDate: LocalDateTime

    @Column(name = "weight")
    open var weight: Double? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    open var competitionDrankDrinks: MutableList<CompetitionDrankDrink> = ArrayList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    open var gatheringDrankDrinks: MutableList<GatheringDrankDrink> = ArrayList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    open var competitionTeamMembers: MutableList<CompetitionTeamMember> = ArrayList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    @OrderBy("createdAt DESC")
    open var gatheringMemberships: MutableList<GatheringMember> = ArrayList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    open var pubOwner: MutableList<PubOwner> = ArrayList()
}