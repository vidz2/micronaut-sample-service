package com.app.electric.controllers

import com.app.electric.domain.UserDto
import com.app.electric.services.UserService
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class UserRegisterControllerSpec extends Specification {
    @Inject
    UserService userService

    @MockBean
    @Replaces(UserService)
    UserService mockService() {
        return Mock(UserService);
    }

    UserRegisterController userRegisterController
    UserDto alfie

    def setup(){
        userRegisterController = new UserRegisterController(userService)
        alfie = new UserDto("Alfie", "MyCat", "alfie@cat.com")
    }

    def "getUser returns ok status and correct user"() {
        given:
            def expected = [alfie]
            def lastName = "MyCat"
        when:
            userService.findByLastName(lastName) >> expected
            def response = userRegisterController.getUser(lastName)
        then:
//            1 * userService.findByLastName(lastName) >> expected
            response.status == HttpStatus.OK
            response.body() == expected
    }

    def "getUser returns not found status"() {
        when:
            def response = userRegisterController.getUser("Alfie")
        then:
            response.status == HttpStatus.NOT_FOUND
            1 * userService.findByLastName(_) >> []
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
            1 * userService.save(alfie) >> expected
            response.status == HttpStatus.CREATED
            response.body() == expected
    }

    def "addUser returns service unavailable status"() {
        when:
        def response = userRegisterController.addUser(alfie)
        then:
        response.status == HttpStatus.SERVICE_UNAVAILABLE
        1 * userService.save(_) >> null
        response.body() == null
    }

}
