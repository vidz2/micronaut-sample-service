package com.app.electric.repositories;

import com.app.electric.domain.User;
import com.app.electric.domain.UserDto;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface IUserRepository extends CrudRepository<User, UUID> {
    List<UserDto> findByLastNameIgnoreCase(@NonNull String lastName);

    UserDto save(@Valid UserDto user);
}
