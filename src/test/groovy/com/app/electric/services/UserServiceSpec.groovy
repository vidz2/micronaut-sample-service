package com.app.electric.services

import com.app.electric.domain.User
import com.app.electric.domain.UserDto
import com.app.electric.repositories.UserRepository
import com.app.electric.utils.Conversions
import com.app.electric.utils.ConversionsImpl
import io.micronaut.context.annotation.Replaces
import io.micronaut.test.annotation.MockBean
import jakarta.inject.Inject
import spock.lang.Specification

class UserServiceSpec extends Specification {
    UserService userService

    @Inject
    UserRepository userRepository

    @Inject
    Conversions conversions

//    @MockBean
//    @Replaces(UserRepository)
//    UserRepository mockRepo() {
//        return Mock(UserRepository);
//    }

    def setup() {
        userRepository = Mock(UserRepository)
        conversions = new ConversionsImpl()
        userService = new UserServiceImpl(userRepository, conversions)
    }

    def "i can save the user"() {
        given: "i have an user"
            UserDto user1 = new UserDto("Spongebob", "Squarepants","spongebob@bikinibottom.com")
        and:
            User user = new User(user1.firstName, user1.lastName, user1.email)
            userRepository.save(_) >> user
        when: "i save the user"
            UserDto savedUser = userService.save(user1)
        then:
            savedUser.firstName == user.firstName
    }

    def "i can get the user by last name"() {
        given: "i have an user"
            User user1 = new User("Spongebob", "Squarepants","spongebob@bikinibottom.com")
        and:
            userRepository.findByLastNameIgnoreCase(user1.lastName) >> [user1]
        when: "i get the user by last name"
            List<User> users = userService.findByLastName(user1.lastName);
        then:
            users
            users[0].lastName == user1.lastName
            users[0].firstName == user1.firstName
            users[0].email == user1.email

    }
}
