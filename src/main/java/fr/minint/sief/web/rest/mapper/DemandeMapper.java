package fr.minint.sief.web.rest.mapper;

import org.mapstruct.Mapper;

import fr.minint.sief.domain.Demande;
import fr.minint.sief.web.rest.dto.DemandeDTO;

/**
 * Mapper for the entity Demande and its DTO DemandeDTO.
 */
@Mapper(componentModel = "spring", uses = {IdentityMapper.class})
public interface DemandeMapper {

    DemandeDTO demandeToDemandeDTO(Demande demande);

    Demande demandeDTOToDemande(DemandeDTO demandeDTO);
}
