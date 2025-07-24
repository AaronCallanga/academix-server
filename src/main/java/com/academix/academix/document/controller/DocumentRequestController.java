package com.academix.academix.document.controller;

import com.academix.academix.document.service.api.DocumentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentRequestController {

    private final DocumentRequestService documentRequestService;

}

/*
 When to use <?>:
When you're writing generic controllers or flexible endpoints.
When the response might return different types depending on the situation.

Example:
@GetMapping("/{id}")
public ResponseEntity<?> getById(@PathVariable Long id) {
    Optional<User> user = userService.findById(id);
    if (user.isPresent()) {
        return ResponseEntity.ok(user.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("User not found");
    }
}
 */
