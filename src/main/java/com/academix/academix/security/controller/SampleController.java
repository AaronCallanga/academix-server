package com.academix.academix.security.controller;

import com.academix.academix.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/sample")
    public ResponseEntity<String> sample(Authentication auth) {
        return new ResponseEntity<>("jwt required, you have accessed", HttpStatus.OK);
    }
}
