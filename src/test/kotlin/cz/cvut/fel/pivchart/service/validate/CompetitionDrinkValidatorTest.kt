package cz.cvut.fel.pivchart.service.validate

import cz.cvut.fel.pivchart.Generator
import cz.cvut.fel.pivchart.model.entity.Pub
import cz.cvut.fel.pivchart.model.entity.PubDrink
import cz.cvut.fel.pivchart.repository.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class CompetitionDrinkValidatorTest @Autowired constructor(
        private val pubRepository: PubRepository,
        private val pubOwnerRepository: PubOwnerRepository,
        private val userRepository: UserRepository,
        private val drinkRepository: DrinkRepository
) {
    lateinit var pub: Pub
    lateinit var otherUnknownPub: Pub
    lateinit var pubDrink: PubDrink

    @BeforeEach
    fun setUp() {
        val user = userRepository.save(Generator.generateUser())
        val pubOwner = pubOwnerRepository.save(Generator.generateOwner(user))
        val drink = drinkRepository.save(Generator.generateDrink())

        pub = pubRepository.save(Generator.generatePub(pubOwner))
        otherUnknownPub = pubRepository.save(Generator.generatePub(pubOwner))

        pubDrink = Generator.generatePubDrink().apply {
            this.drink = drink
        }
    }
}