package cz.cvut.fel.pivchart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

/**
 * @author Pivchart Team
 *
 * Main class of pivchart monolith service.
 * The application is designed for leisure activities with alcohol
 * More information about the structure of the project,
 * its purpose and technologies used can be found on the gitlab page
 *
 * Thank you for being with us!
 */
@EnableEurekaClient
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class PivchartApplication

fun main(args: Array<String>) {
    runApplication<PivchartApplication>(*args)
}