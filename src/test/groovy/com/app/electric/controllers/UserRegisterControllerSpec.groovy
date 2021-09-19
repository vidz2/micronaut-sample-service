package com.app.electric.controllers

import com.app.electric.domain.UserDto
import com.app.electric.repositories.IUserRepository
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class UserRegisterControllerSpec extends Specification {
    @Inject
    IUserRepository userRepository

    @MockBean
    @Replaces(IUserRepository)
    IUserRepository mockRepo() {
        return Mock(IUserRepository);
    }

    UserRegisterController userRegisterController
    UserDto alfie

    def setup(){
        userRegisterController = new UserRegisterController(userRepository)
        alfie = new UserDto("Alfie", "MyCat", "alfie@cat.com")
    }

    def "getUser returns ok status and correct user"() {
        given:
            def expected = [alfie]
            def lastName = "MyCat"
        when:
            def response = userRegisterController.getUser(lastName)
        then:
            1 * userRepository.findByLastNameIgnoreCase(lastName) >> expected
            response.status == HttpStatus.OK
            response.body() == expected
    }

    def "getUser returns not found status"() {
        when:
            def response = userRegisterController.getUser("Alfie")
        then:
            response.status == HttpStatus.NOT_FOUND
            1 * userRepository.findByLastNameIgnoreCase(_) >> []
            response.body() == null
    }

    def "addUser returns created status"() {
        given:
            UUID id = UUID.randomUUID()
            def expected = alfie
            expected.id = id
        when:
        def response = userRegisterController.addUser(alfie)
        then:
            1 * userRepository.save(alfie) >> expected
            response.status == HttpStatus.CREATED
            response.body() == expected
    }

    def "addUser returns service unavailable status"() {
        when:
        def response = userRegisterController.addUser(alfie)
        then:
        response.status == HttpStatus.SERVICE_UNAVAILABLE
        1 * userRepository.save(_) >> null
        response.body() == null
    }

}
