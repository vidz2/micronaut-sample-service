package com.app.electric.utils;

import com.app.electric.domain.User;
import com.app.electric.domain.UserDto;
import io.micronaut.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@Bean
public class Conversions implements IConversions{
    Logger log = LoggerFactory.getLogger(Conversions.class);

    public Conversions() {
    }

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            log.error("Cannot create UserDto using null user!");
            return null;
        }
        UserDto userDto = new UserDto(user.getFirstName(),user.getLastName(), user.getEmail());
        userDto.setId(user.getId());
        return userDto;
    }

}
