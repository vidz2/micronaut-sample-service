package com.app.electric.services;

import com.app.electric.domain.UserDto;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    UserDto save(@Valid UserDto user);
    List<UserDto> findByLastName(@NonNull String lastName);
}

