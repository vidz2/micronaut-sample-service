package com.app.electric.repositories;

import com.app.electric.domain.User;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    List<User> findByLastNameIgnoreCase(@NonNull String lastName);

    User save(@Valid User user);
}
