package fr.minint.sief.web.rest.mapper;

import org.mapstruct.Mapper;

import fr.minint.sief.domain.Application;
import fr.minint.sief.web.rest.dto.ApplicationDTO;

/**
 * Mapper for the entity Application and its DTO ApplicationDTO.
 */
@Mapper(componentModel = "spring", uses = {IdentityMapper.class, AddressMapper.class, ProjectMapper.class})
public interface ApplicationMapper {

    ApplicationDTO applicationToApplicationDTO(Application application);

    Application applicationDTOToApplication(ApplicationDTO applicationDTO);
}
