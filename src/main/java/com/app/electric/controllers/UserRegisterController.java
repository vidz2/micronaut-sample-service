package com.app.electric.controllers;

import com.app.electric.domain.UserDto;
import com.app.electric.services.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/user")
public class UserRegisterController {
    @Inject
    UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @Get("/{lastName}")
    HttpResponse<List<UserDto>> getUser(String lastName){
        List<UserDto> users = userService
                .findByLastName(lastName);
        if (users.size() == 0)
            return HttpResponse.notFound();
        return HttpResponse.ok(users);
    }

    @Post
    HttpResponse<UserDto> addUser(@Body UserDto user ) {
        UserDto createdUser = userService.save(user);
        if (createdUser == null)
            return HttpResponse.status(HttpStatus.SERVICE_UNAVAILABLE);
        return HttpResponse.created(createdUser);
    }
}
