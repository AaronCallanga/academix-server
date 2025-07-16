package com.academix.academix.user.mapper;

import com.academix.academix.user.dto.UserDTO;
import com.academix.academix.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "verified", target = "verified")
    UserDTO userToUserDTO(User user);
    List<UserDTO> usersToUserDTOs(List<User> users);
}
