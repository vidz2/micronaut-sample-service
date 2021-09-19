package com.app.electric.utils

import com.app.electric.domain.User
import com.app.electric.domain.UserDto
import spock.lang.Specification

class ConversionsSpec extends Specification {
    Conversions conversions
    UserDto userDto
    User user

    def setup () {
        conversions = new Conversions()
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

}
