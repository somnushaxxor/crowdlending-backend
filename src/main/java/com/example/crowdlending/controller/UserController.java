package com.example.crowdlending.controller;

import com.example.crowdlending.payload.request.SignupRequest;
import com.example.crowdlending.payload.response.ProjectsUnitPreviewResponse;
import com.example.crowdlending.payload.response.UserInfoResponse;
import com.example.crowdlending.service.ProjectService;
import com.example.crowdlending.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/{userId}")
    public UserInfoResponse getUserInfo(@PathVariable Long userId) {
        return userService.findUserDTOById(userId);
    }

    @GetMapping("/profile")
    public UserInfoResponse getUserInfoAfterAuth() {
        return userService.findUserDTOAfterAuth();
    }

    @PutMapping("/profile/edit")
    public UserInfoResponse update(@RequestBody @Valid SignupRequest updatedUser) {
        return userService.updateUserInfo(updatedUser);
    }

    @GetMapping(value = "/{userId}/projects")
    public ProjectsUnitPreviewResponse findAllChildrenProjects(@PathVariable Long userId) {
        return projectService.findAllByParent(userId);
    }
}
