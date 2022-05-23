package cz.cvut.fel.pivchart.model.entity

import cz.cvut.fel.pivchart.model.entity.base.AbstractValidEntity
import javax.persistence.*

/**
 * Competition team member entity
 */
@Entity
@Table(name = "competition_team_members")
open class CompetitionTeamMember : AbstractValidEntity<Long>() {

    @Column(name = "is_captain", nullable = false)
    open var isCaptain = false

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "competition_team_id")
    open lateinit var competitionTeam: CompetitionTeam

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    open lateinit var user: User
}