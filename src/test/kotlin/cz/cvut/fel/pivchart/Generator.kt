package cz.cvut.fel.pivchart

import cz.cvut.fel.pivchart.model.entity.*
import cz.cvut.fel.pivchart.model.entity.embeddable.Address
import cz.cvut.fel.pivchart.model.entity.enum.CompetitionEndType
import cz.cvut.fel.pivchart.model.request.*
import java.time.LocalDateTime

object Generator {
    fun generatePub(pubOwner: PubOwner): Pub {

        val pub = Pub()
        val address = Address()

        address.apply {
            city = "Prague"
            street = "Pštrossova 220/29"
            country = "Czechia"
            postalCode = "11000"
        }

        pub.apply {
            this.name = "Propaganda"
            this.address = address
            this.pubOwner = pubOwner
        }

        return pub
    }

    fun generatePubDrink(): PubDrink {
        return PubDrink().apply {
            price = 70.0
            volume = 40
        }
    }

    fun generateDrink(): Drink {
        return Drink().apply {
            name = "Teleport"
            alcoholVolume = 55.0
        }
    }

    fun generateUser(): User {
        return User().apply {
            this.email = "name@fel.cvut.cz"
            this.username = "username"
            this.birthDate = LocalDateTime.now()
        }
    }

    fun generateOwner(user: User): PubOwner{
        return PubOwner().apply {
            this.user = user
            this.bio = "some information bio"
        }
    }

    fun generateGatheringMember(user: User): GatheringMember{
        return GatheringMember().apply {
            this.isOwner = true
            this.user = user
        }
    }

    fun generateGathering(pub: Pub): Gathering{
        return Gathering().apply {
            this.title = "Test gathering"
            this.pub = pub
        }
    }

    fun generateCompetition(pub: Pub, owner: User): Competition{
        return Competition().apply{
            this.title = "Test competition"
            this.pub = pub
            this.owner = owner
            this.competitionEndType = CompetitionEndType.LEADER_STOPS
        }
    }

    fun generateCompetitionTeam(competition: Competition): CompetitionTeam{
        return CompetitionTeam().apply {
            this.title = "Test competitionTeam"
            this.competition = competition
        }
    }

    fun generateCompetitionTeamMember(user: User, competitionTeam: CompetitionTeam): CompetitionTeamMember{
        return CompetitionTeamMember().apply {
            this.isCaptain = true
            this.competitionTeam = competitionTeam
            this.user = user
        }
    }

    fun generateCompetitionDrankDrink(user: User ,competition: Competition, pubDrink: PubDrink): CompetitionDrankDrink{
        return CompetitionDrankDrink().apply {
            this.competition = competition
            this.pubDrink = pubDrink
            this.user = user
        }
    }

    fun generatePubDrinkRequest(drinkId: Long): CreatePubDrinkRequest {
        return CreatePubDrinkRequest(drinkId, 70.0, 40)
    }

    fun generateUpdateCompetitionTeamRequest(members: HashSet<Long>): UpdateCompetitionTeamRequest{
        val set: MutableSet<Long> = members

        return UpdateCompetitionTeamRequest("Updated team title", set)
    }

    fun generateTeamRequest(user: User): TeamRequest{
        return TeamRequest("Test team title", user.id!!)
    }

    fun generateCreateCompetitionRequest(pub: Pub, list: List<TeamRequest>): CreateCompetitionRequest{
        return CreateCompetitionRequest("Test create competition", LocalDateTime.now(), LocalDateTime.now(), CompetitionEndType.LEADER_STOPS, 12,pub.id!!, list)
    }

    fun generateUpdateCompetitionRequest(): UpdateCompetitionRequest{
        return UpdateCompetitionRequest("Updated title")
    }

    fun generateGatheringRequest(): CreateGatheringRequest {
        return CreateGatheringRequest("Test gathering", 1, setOf(1, 2, 3))
    }

    fun generateUpdatePubDrinkRequest(pubDrinkId: Long): UpdatePubDrinkRequest {
        return UpdatePubDrinkRequest(34.0, 34, pubDrinkId)
    }
}