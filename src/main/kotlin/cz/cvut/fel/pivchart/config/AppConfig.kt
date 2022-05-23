package cz.cvut.fel.pivchart.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.support.StandardServletMultipartResolver

@Configuration
class AppConfig {
    /**
     * [RestTemplate] can be used to communicate with web services of another application - see for example [http://www.baeldung.com/rest-template](http://www.baeldung.com/rest-template).
     */
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    /**
     * [MultipartResolver] is used to support file upload.
     */
    @Bean(name = ["multipartResolver"])
    fun multipartResolver(): MultipartResolver {
        return StandardServletMultipartResolver()
    }

    /**
     * Overrides the default [ObjectMapper] bean created by Spring Boot to allow further customization.
     *
     *
     * Basic configuration could be also done in `application.properties`, see [Spring
 * Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-spring-mvc.html#howto-customize-the-jackson-objectmapper).
     *
     * @return `ObjectMapper` bean
     */
    @Bean
    @Primary // Override the default instance created by Spring Boot
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        // Support for Java 8 Date/Time API
        objectMapper.registerModule(JavaTimeModule())
        // Only non-null properties are serialized
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        // This is necessary for our way of working with Java 8 Date/Time API. If it were not configured, the
        // datetime object in JSON would consist of several attributes, each for the respective date/time part, i.e. year, day etc.
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        // Ignore unknown properties in JSON
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return objectMapper
    }
}
