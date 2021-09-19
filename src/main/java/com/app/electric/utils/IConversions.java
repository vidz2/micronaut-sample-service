package com.app.electric.utils;

import com.app.electric.domain.User;
import com.app.electric.domain.UserDto;

public interface IConversions {
    UserDto toUserDto(User user);
}
