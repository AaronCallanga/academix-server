package com.academix.academix.user.mapper;

import com.academix.academix.user.dto.UserDTO;
import com.academix.academix.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
}
