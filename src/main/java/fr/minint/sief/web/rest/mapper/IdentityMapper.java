package fr.minint.sief.web.rest.mapper;

import org.mapstruct.Mapper;

import fr.minint.sief.domain.Identity;
import fr.minint.sief.web.rest.dto.IdentityDTO;

/**
 * Mapper for the entity Identity and its DTO IdentityDTO.
 */
@Mapper(componentModel = "spring", uses = {DocumentMapper.class})
public interface IdentityMapper {

    IdentityDTO identityToIdentityDTO(Identity identity);

    Identity identityDTOToIdentity(IdentityDTO identityDTO);
}
