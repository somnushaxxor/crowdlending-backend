package com.example.crowdlending.repository.mapper;

import com.example.crowdlending.payload.request.ProjectInfoRequest;
import com.example.crowdlending.payload.request.SignupRequest;
import com.example.crowdlending.payload.response.ProjectInfoResponse;
import com.example.crowdlending.payload.response.ProjectUnitPreviewResponse;
import com.example.crowdlending.payload.response.UserInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.crowdlending.repository.model.Project;
import com.example.crowdlending.repository.model.User;

@Mapper(componentModel = "spring", imports = UserInfoResponse.class)
public interface CrowdfundingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "projects", ignore = true)
    User importUser(SignupRequest signupRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    UserInfoResponse exportUser(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "collectedAmount", ignore = true)
    @Mapping(target = "parent", ignore = true)
    Project importProject(ProjectInfoRequest projectInfoResponse);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "parentId", expression = "java(project.getParent().getId())")
    @Mapping(target = "parentName", expression = "java(project.getParent().getName())")
    @Mapping(target = "date", source = "date")
    ProjectInfoResponse exportProject(Project project);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "parentName", ignore = true)
    ProjectUnitPreviewResponse exportProjectPreview(Project project);
}
