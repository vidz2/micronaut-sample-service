package com.app.electric.services;

import com.app.electric.domain.User;
import com.app.electric.domain.UserDto;
import com.app.electric.repositories.UserRepository;
import com.app.electric.utils.Conversions;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    private final Conversions conversionUtils;

    public UserServiceImpl(UserRepository userRepository, Conversions conversionUtils) {
        this.userRepository = userRepository;
        this.conversionUtils = conversionUtils;

    }

    @Override
    public UserDto save(UserDto userDto) {
        User domainUser = conversionUtils.toUser(userDto);
        User savedUser = userRepository.save(domainUser);
        return conversionUtils.toUserDto(savedUser);
    }

    @Override
    public List<UserDto> findByLastName(String lastName) {
        List<User> users = userRepository.findByLastNameIgnoreCase(lastName);
        return users.stream().map(conversionUtils::toUserDto).collect(Collectors.toList());
    }
}
