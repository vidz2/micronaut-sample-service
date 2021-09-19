package com.app.electric.repositories

import com.app.electric.domain.UserDto
import io.micronaut.context.BeanContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import javax.validation.ConstraintViolationException

@MicronautTest
class UserRepositorySpec extends Specification {
    @Inject
    BeanContext beanContext;

    @Inject
    UserRepository userRepository;

    def cleanup(){
        userRepository.deleteAll()
    }

    def "save method saves a user in db"() {
        given:
            UserDto user1 = new UserDto("Spongebob", "Squarepants","spongebob@bikinibottom.com")
        when:
            UserDto savedUser = userRepository.save(user1)
        then:
            savedUser.id != null
            savedUser.lastName == user1.lastName
    }

    def "findByLastName method returns user"() {
        given:
            UserDto user1 = new UserDto("Molly", "AnotherCat","molly@playing.com")
            userRepository.save(user1)
        when:
            List<UserDto> createdUsers = userRepository.findByLastNameIgnoreCase(user1.lastName)
        then:
            createdUsers.size() == 1
            createdUsers.get(0).lastName == user1.lastName
    }

    def "findByLastName method returns multiple users with same lastName"() {
        given:
            UserDto user1 = new UserDto("Molly", "Cat","molly@playing.com")
            userRepository.save(user1)

            UserDto user2 = new UserDto("Alfie", "Cat","alfie@playing.com")
            userRepository.save(user2)
        when:
            List<UserDto> createdUsers = userRepository.findByLastNameIgnoreCase(user1.lastName)
        then:
            createdUsers.size() == 2
            createdUsers.get(0).lastName == user1.lastName
            createdUsers.get(1).lastName == user2.lastName
    }

    def "findByLastName method returns empty list when user with lastName not found"() {
        given:
            UserDto user1 = new UserDto("Molly", "Cat","molly@playing.com")
            userRepository.save(user1)
        when:
            List<UserDto> createdUsers = userRepository.findByLastNameIgnoreCase("DoesNotExit")
        then:
            createdUsers.size() == 0
    }

    def "null email is validated when trying to save user"() {
        when:
            UserDto user1 = new UserDto("Molly", "Cat",null)
            userRepository.save(user1)
        then:
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("UserDto.email: must not be null")
    }

    def "blank email is validated when trying to save user"() {
        when:
        UserDto user1 = new UserDto("Molly", "Cat","")
        userRepository.save(user1)
        then:
        def exception = thrown(ConstraintViolationException)
        exception.message.contains("UserDto.email: must not be blank")
    }

    def "email format is validated when trying to save user"() {
        when:
        UserDto user1 = new UserDto("Molly", "Cat","notAnEmail")
        userRepository.save(user1)
        then:
        def exception = thrown(ConstraintViolationException)
        exception.message == "UserDto.email: must be a well-formed email address"
    }

    def "null lastName is validated when trying to save user"() {
        when:
            UserDto user1 = new UserDto("Molly", null, "molly@thecat.com")
            userRepository.save(user1)
        then:
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("UserDto.lastName: must not be null")
    }

    def "blank lastName is validated when trying to save user"() {
        when:
        UserDto user1 = new UserDto("Molly", "", "molly@thecat.com")
        userRepository.save(user1)
        then:
        def exception = thrown(ConstraintViolationException)
        exception.message.contains("UserDto.lastName: must not be blank")
    }

    def "null firstName is validated when trying to save user"() {
        when:
        UserDto user1 = new UserDto(null, "TheCat", "molly@thecat.com")
        userRepository.save(user1)
        then:
        def exception = thrown(ConstraintViolationException)
        exception.message.contains("UserDto.firstName: must not be null")
    }

    def "blank firstName is validated when trying to save user"() {
        when:
        UserDto user1 = new UserDto("", "TheCat", "molly@thecat.com")
        userRepository.save(user1)
        then:
        def exception = thrown(ConstraintViolationException)
        exception.message.contains("UserDto.firstName: must not be blank")
    }

}
