package com.app.electric.controllers

import com.app.electric.domain.UserDto
import com.app.electric.repositories.UserRepository
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class UserRegisterControllerIntSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient client

    @Inject
    UserRepository userRepository

    def cleanup() {
        userRepository.deleteAll()
    }

    def "can save user"() {
        given:
            def userData = new UserDto("Alfie", "TheCat", "alfie@sleeping.com")
        when:
            HttpRequest request = HttpRequest.POST('/user', userData)
            HttpResponse response = client.toBlocking().exchange(request)
        then:
            response.getStatus() == HttpStatus.CREATED
    }

    def "returns 400 response when null value is passed"() {
        given:
            def userData = new UserDto("Alfie", null, "alfie@sleeping.com")
        when:
            HttpRequest request = HttpRequest.POST('/user', userData)
            HttpResponse<UserDto> response = client.toBlocking().exchange(request, Argument.of(UserDto.class), Argument.of(UserDto.class));
        then:
            response.getStatus() == HttpStatus.BAD_REQUEST
    }

    def "can get user by lastName"() {
        given:
            def userData = new UserDto("Alfie", "TheCat", "alfie@sleeping.com")
            HttpRequest request = HttpRequest.POST('/user', userData)
            UserDto createdUser = client.toBlocking().retrieve(request, UserDto)
            createdUser != null
        when:
            String lastName = createdUser.lastName
            HttpRequest getRequest = HttpRequest.GET("/user/$lastName")
            List<UserDto> userList = client.toBlocking().retrieve(getRequest, Argument.of(List.class,UserDto.class))
        then:
            userList.size() == 1
            userList.get(0).getLastName() == userData.getLastName()
            userList.get(0).getFirstName() == userData.getFirstName()
            userList.get(0).getEmail() == userData.getEmail()
    }

    def "returns NotFound error when lastName is not found"() {
        when:
            HttpRequest getRequest = HttpRequest.GET("/user/nonExistingUserName")
            HttpResponse<List<UserDto>> response = client.toBlocking().exchange(getRequest, Argument.of(List.class, UserDto.class), Argument.of(List.class,UserDto.class))
        then:
            response.status == HttpStatus.NOT_FOUND

    }
}
