package fr.minint.sief.web.rest.mapper;

import org.mapstruct.Mapper;

import fr.minint.sief.domain.Project;
import fr.minint.sief.web.rest.dto.ProjectDTO;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {DocumentMapper.class})
public interface ProjectMapper {

    ProjectDTO projectToProjectDTO(Project project);

    Project projectDTOToProject(ProjectDTO projectDTO);
}
