package com.app.electric.utils

import com.app.electric.domain.User
import com.app.electric.domain.UserDto
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.micronaut.validation.Validated
import spock.lang.Specification

@Validated
@MicronautTest
class ConversionsSpec extends Specification {
    Conversions conversions
    def setup () {
        conversions = new ConversionsImpl()
    }

    def "converts User to UserDto"() {
        given:
            User user = new User("Monty", "ACat", "monty@cat.com")
        when:
            def converted = conversions.toUserDto(user)
        then:
            converted.lastName == user.lastName
            converted.firstName == user.firstName
            converted.email == user.email
            converted.id == user.id
    }

    def "returns null when user is null"() {
        when:
            def converted = conversions.toUserDto(null)
        then:
            converted == null
    }

    def "converts UserDto to User"() {
        given:
            UserDto userDto = new UserDto("Monty", "ACat", "monty@cat.com")
        when:
            def converted = conversions.toUser(userDto)
        then:
            converted.lastName == userDto.lastName
            converted.firstName == userDto.firstName
            converted.email == userDto.email
    }

}
