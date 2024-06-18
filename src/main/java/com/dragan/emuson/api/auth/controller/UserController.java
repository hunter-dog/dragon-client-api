package com.dragan.emuson.api.auth.controller;

import com.dragan.emuson.api.auth.exception.ResourceNotFoundException;
import com.dragan.emuson.api.auth.model.User;
import com.dragan.emuson.api.auth.payload.UserDTO;
import com.dragan.emuson.api.auth.repository.UserRepository;
import com.dragan.emuson.security.CurrentUser;
import com.dragan.emuson.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserDTO getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setRole("USER");

        return userDTO;
    }
}
