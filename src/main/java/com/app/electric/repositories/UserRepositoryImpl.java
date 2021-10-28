package com.app.electric.repositories;

import com.app.electric.domain.User;
import com.app.electric.domain.UserDto;
import com.app.electric.utils.Conversions;
import io.micronaut.context.annotation.Executable;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Repository;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Repository
public abstract class UserRepositoryImpl implements UserRepository {
    Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Inject
    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager, Conversions conversionUtils) {
        this.entityManager = entityManager;
    }

    @Executable
    public abstract List<User> findByLastNameIgnoreCase(@NonNull String lastName);

    @Executable
    @Transactional
    public User save(@Valid User user){
        entityManager.persist(user);
        return user;
    }
}
