package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.model.entity.*
import cz.cvut.fel.pivchart.model.request.TeamRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamCaptainRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionTeamRequest
import cz.cvut.fel.pivchart.repository.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional


@SpringBootTest
@Transactional
class CompetitionTeamServiceTest @Autowired constructor(
        private val pubRepository: PubRepository,
        private val userRepository: UserRepository,
        private val pubOwnerRepository: PubOwnerRepository,
        private val competitionTeamService: CompetitionTeamService,
        private val competitionRepository: CompetitionRepository,
        private val competitionTeamRepository: CompetitionTeamRepository
){
    lateinit var user: User
    lateinit var otherUser: User
    lateinit var competition: Competition
    lateinit var competitionTeam: CompetitionTeam
    lateinit var teamRequest: TeamRequest
    lateinit var updateCompetitionTeamRequest: UpdateCompetitionTeamRequest

    @BeforeEach
    fun setUp() {
        user = userRepository.save(Generator.generateUser())
        otherUser = userRepository.save(Generator.generateUser().apply { this.email = "newuser.gmail.com" })
        val pubOwner = pubOwnerRepository.save(Generator.generateOwner(user))
        val pub = pubRepository.save(Generator.generatePub(pubOwner))
        competition = competitionRepository.save(Generator.generateCompetition(pub, user))
        competitionTeam = competitionTeamRepository.save(Generator.generateCompetitionTeam(competition))
        competition.competitionTeams.add(competitionTeam)
        competitionRepository.save(competition)
    }

    @Test
    fun testCreateCompetitionTeam_Ok() {
        teamRequest = Generator.generateTeamRequest(user)

        val result = competitionTeamService.create(competition.id!!, teamRequest)


        Assertions.assertEquals(competition.id, result.competition.id)
        Assertions.assertEquals(competition.title, competitionRepository.getById(competition.id!!).title)
        Assertions.assertEquals(result, competitionTeamRepository.getById(result.id!!))
        Assertions.assertEquals(result.title, competitionTeamRepository.getById(result.id!!).title)
    }

    @Test
    fun testUpdateCompetitionTeam_Ok() {
        updateCompetitionTeamRequest = Generator.generateUpdateCompetitionTeamRequest(HashSet(user.id!!.toInt(), otherUser.id!!.toFloat()))

        val result = competitionTeamService.update(competition.id!!, competitionTeam.id!! ,updateCompetitionTeamRequest)
        val resultTitle = "Updated team title"

        Assertions.assertEquals(competition.id, result.competition.id)
        Assertions.assertEquals(resultTitle, competitionTeamRepository.getById(competitionTeam.id!!).title)
        Assertions.assertEquals(competition.title, competitionRepository.getById(competition.id!!).title)
        Assertions.assertEquals(competitionTeam, competitionTeamRepository.getById(competitionTeam.id!!))
    }
}