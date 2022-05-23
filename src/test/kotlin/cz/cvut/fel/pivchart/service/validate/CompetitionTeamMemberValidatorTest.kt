package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.exceptions.api.NotFoundException
import cz.cvut.fel.pivchart.model.entity.*
import cz.cvut.fel.pivchart.repository.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional


@SpringBootTest
@Transactional
class CompetitionTeamMemberValidatorTest @Autowired constructor(

        private val competitionTeamMemberValidator: CompetitionTeamMemberValidator,
        private val pubRepository: PubRepository,
        private val pubOwnerRepository: PubOwnerRepository,
        private val competitionRepository: CompetitionRepository,
        private val competitionTeamRepository: CompetitionTeamRepository,
        private val competitionTeamMemberRepository: CompetitionTeamMemberRepository,
        private val userRepository: UserRepository
){

    lateinit var competition: Competition
    lateinit var competitionTeam: CompetitionTeam
    lateinit var competitionTeamMember: CompetitionTeamMember

    @BeforeEach
    fun setUp(){
        val user = userRepository.save(Generator.generateUser())
        val pubOwner = pubOwnerRepository.save(Generator.generateOwner(user))
        val pub = pubRepository.save(Generator.generatePub(pubOwner))
        competition = competitionRepository.save(Generator.generateCompetition(pub, user))
        competitionTeam = competitionTeamRepository.save(Generator.generateCompetitionTeam(competition))
        competition.competitionTeams.add(competitionTeam)
        competitionRepository.save(competition)
        competitionTeamMember = competitionTeamMemberRepository.save(Generator.generateCompetitionTeamMember(user, competitionTeam))

    }

    @Test
    fun testValidateOnGetCompetitionTeamMember_Ok(){
        assertDoesNotThrow {
            competitionTeamMemberValidator.validateOnGetAll(competition.id!!)
        }
    }

    @Test
    fun testValidateOnGetCompetitionTeamMember_CompetitionDouesNotExist_NotFoundException(){
        assertThrows<NotFoundException> {
            competitionTeamMemberValidator.validateOnGetAll(123)
        }
    }
}