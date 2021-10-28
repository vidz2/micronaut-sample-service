package com.app.electric.utils;

import com.app.electric.domain.User;
import com.app.electric.domain.UserDto;

public interface Conversions {
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
}
