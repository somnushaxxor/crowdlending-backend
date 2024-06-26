package com.example.crowdlending.controller;

import com.example.crowdlending.payload.request.LoginRequest;
import com.example.crowdlending.payload.request.SignupRequest;
import com.example.crowdlending.payload.response.UserInfoResponse;
import com.example.crowdlending.repository.mapper.CrowdfundingMapper;
import com.example.crowdlending.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.crowdlending.repository.model.User;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final CrowdfundingMapper mapper;

    @PostMapping("/register")
    public UserInfoResponse register(@RequestBody SignupRequest signupRequest) {
        User newUser = mapper.importUser(signupRequest);
        return userService.registerUser(newUser);
    }

    @PostMapping("/login")
    public UserInfoResponse login(@RequestBody LoginRequest loginRequest) {
        User authUser = userService.findUserByEmail(loginRequest.getEmail());
        return mapper.exportUser(authUser);
    }
}
