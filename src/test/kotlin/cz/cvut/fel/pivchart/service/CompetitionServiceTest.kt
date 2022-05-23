package cz.cvut.fel.pivchart.service

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.UserContextSetter
import cz.cvut.fel.pivchart.exceptions.api.BadRequestException
import cz.cvut.fel.pivchart.model.entity.*
import cz.cvut.fel.pivchart.model.entity.enum.CompetitionEndType
import cz.cvut.fel.pivchart.model.request.CreateCompetitionRequest
import cz.cvut.fel.pivchart.model.request.TeamRequest
import cz.cvut.fel.pivchart.model.request.UpdateCompetitionRequest
import cz.cvut.fel.pivchart.repository.*
import cz.cvut.fel.pivchart.utils.Auth
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import javax.transaction.Transactional


@SpringBootTest
@Transactional
class CompetitionServiceTest @Autowired constructor(
    private val pubRepository: PubRepository,
    private val userRepository: UserRepository,
    private val pubOwnerRepository: PubOwnerRepository,
    private val competitionService: CompetitionService,
    private val auth: Auth,
    private val competitionRepository: CompetitionRepository
) {
    lateinit var user: User
    lateinit var otherUser: User
    lateinit var pub: Pub
    lateinit var createCompetitionRequest: CreateCompetitionRequest
    lateinit var updateCompetitionRequest: UpdateCompetitionRequest

    @BeforeEach
    fun setUp() {
        user = userRepository.save(Generator.generateUser())
        otherUser = userRepository.save(Generator.generateUser().apply {
            this.email = "newuser@gmail.com" }
        )
        val pubOwner = pubOwnerRepository.save(Generator.generateOwner(user))
        pub = pubRepository.save(Generator.generatePub(pubOwner))

    }

    @Test
    fun testCreateCompetition_Ok() {
        UserContextSetter.setUser(user.email)

        val teams: List<TeamRequest> = listOf(Generator.generateTeamRequest(auth.get()))
        createCompetitionRequest = Generator.generateCreateCompetitionRequest(pub, teams)

        val result = competitionService.create(createCompetitionRequest)

        val currentCaptain: User = result.competitionTeams[0].competitionTeamMembers.stream()
            .filter { it.isCaptain }
            .map { it.user }
            .findFirst()
            .orElse(null)


        Assertions.assertEquals("Test create competition", result.title)
        Assertions.assertEquals(CompetitionEndType.LEADER_STOPS, result.competitionEndType)
        Assertions.assertEquals(user.id!!, currentCaptain.id)
    }

    @Test
    fun testUpdateCompetition_Ok(){
        UserContextSetter.setUser(user.email)

        val teams: List<TeamRequest> = listOf(Generator.generateTeamRequest(auth.get()))
        createCompetitionRequest = Generator.generateCreateCompetitionRequest(pub, teams)

        val result = competitionService.create(createCompetitionRequest)

        updateCompetitionRequest = Generator.generateUpdateCompetitionRequest()

        competitionService.update(result.id!!, updateCompetitionRequest)

        Assertions.assertEquals("Updated title", competitionRepository.getById(result.id!!).title)

    }

    @Test
    fun testDeleteCompetition_OK(){
        UserContextSetter.setUser(user.email)

        val teams: List<TeamRequest> = listOf(Generator.generateTeamRequest(auth.get()))
        createCompetitionRequest = Generator.generateCreateCompetitionRequest(pub, teams)

        val result = competitionService.create(createCompetitionRequest)

        competitionService.delete(result.id!!)

        assertThrows<JpaObjectRetrievalFailureException> {
            competitionService.get(result.id!!)
        }
    }

    @Test
    fun testFinishCompetition_OK(){
        UserContextSetter.setUser(user.email)

        val teams: List<TeamRequest> = listOf(Generator.generateTeamRequest(auth.get()))
        createCompetitionRequest = Generator.generateCreateCompetitionRequest(pub, teams)

        val result = competitionService.create(createCompetitionRequest)

        competitionService.finish(result.id!!)

        Assertions.assertEquals(true, competitionRepository.getById(result.id!!).isFinished)

        assertThrows<BadRequestException> {
            competitionService.finish(result.id!!)
        }

    }
}