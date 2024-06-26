package com.example.crowdlending.controller;

import com.example.crowdlending.payload.request.ProjectInfoRequest;
import com.example.crowdlending.payload.request.UpdateProjectInfoRequest;
import com.example.crowdlending.payload.response.ProjectInfoResponse;
import com.example.crowdlending.service.DonationService;
import com.example.crowdlending.service.ProjectService;
import com.example.crowdlending.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.crowdlending.repository.model.Project;
import com.example.crowdlending.repository.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final DonationService donationService;
    private final UserService userService;

    @PostMapping("/new")
    public ProjectInfoResponse addProject(@RequestBody @Valid ProjectInfoRequest project) {
        return projectService.createProject(project);
    }

    @GetMapping
    public List<ProjectInfoResponse> getAllProjects(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return projectService.findAll(pageable);
    }

    @GetMapping("/{projectId}")
    public ProjectInfoResponse getProjectById(@PathVariable Long projectId) {
        return projectService.getProjectResponseById(projectId);
    }

    @PostMapping("/{projectId}/donate")
    public ResponseEntity<?> donateToProject(@PathVariable Long projectId,
                                             @RequestBody Long donateAmount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User donater = userService.findUserByEmail(auth.getName());
        projectService.donateToProject(projectId, donateAmount);
        Project project = projectService.getProjectById(projectId);
        donationService.saveDonation(donater, project, donateAmount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectId}/edit")
    public ResponseEntity<ProjectInfoResponse> editProject(@PathVariable Long projectId,
                                                           @RequestBody UpdateProjectInfoRequest updatedProject) {
        return ResponseEntity.ok(projectService.updateProjectByName(projectId, updatedProject));
    }
}
