package fr.minint.sief.web.rest.mapper;

import fr.minint.sief.domain.*;
import fr.minint.sief.web.rest.dto.IdentityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Identity and its DTO IdentityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IdentityMapper {

    IdentityDTO identityToIdentityDTO(Identity identity);

    Identity identityDTOToIdentity(IdentityDTO identityDTO);
}
