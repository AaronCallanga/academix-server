package com.academix.academix.user.controller;

import com.academix.academix.user.dto.UserDTO;
import com.academix.academix.user.entity.User;
import com.academix.academix.user.mapper.UserMapper;
import com.academix.academix.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userMapper.usersToUserDTOs(userRepository.findAll()), HttpStatus.OK);
    }
    @GetMapping("/url")
    public ResponseEntity<String> getUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(url, HttpStatus.OK);
    }
}
