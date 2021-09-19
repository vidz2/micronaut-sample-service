package com.app.electric.repositories;

import com.app.electric.domain.User;
import com.app.electric.domain.UserDto;
import com.app.electric.utils.IConversions;
import io.micronaut.context.annotation.Executable;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Repository
public abstract class UserRepository implements IUserRepository {
    Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Inject
    private final EntityManager entityManager;

    @Inject
    private final IConversions conversionUtils;

    public UserRepository(EntityManager entityManager, IConversions conversionUtils) {
        this.entityManager = entityManager;
        this.conversionUtils = conversionUtils;
    }

    @Executable
    public abstract List<UserDto> findByLastNameIgnoreCase(@NonNull String lastName);

    @Executable
    @Transactional
    @Valid
    public UserDto save(UserDto user){
        User domainUser = new User(user.getFirstName(), user.getLastName(), user.getEmail());
        entityManager.persist(domainUser);
        return conversionUtils.toUserDto(domainUser);
    }
}
