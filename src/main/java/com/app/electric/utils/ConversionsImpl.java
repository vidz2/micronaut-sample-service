package com.app.electric.utils;

import com.app.electric.domain.User;
import com.app.electric.domain.UserDto;
import io.micronaut.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean
public class ConversionsImpl implements Conversions {
    Logger log = LoggerFactory.getLogger(ConversionsImpl.class);

    public ConversionsImpl() {
    }

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            log.error("Cannot create UserDto using null user!");
            return null;
        }
        return new UserDto(user.getFirstName(),user.getLastName(), user.getEmail());
    }

    @Override
    public User toUser(UserDto userDto) {
        return new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail());
    }

}
