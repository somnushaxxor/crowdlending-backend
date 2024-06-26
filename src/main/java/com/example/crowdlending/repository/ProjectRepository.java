package com.example.crowdlending.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.example.crowdlending.repository.model.Project;
import com.example.crowdlending.repository.model.User;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

    Optional<Project> findByName(String name);

    List<Project> findAllByParent(User parent);
}
