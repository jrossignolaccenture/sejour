package fr.minint.sief.web.rest.mapper;

import fr.minint.sief.domain.*;
import fr.minint.sief.web.rest.dto.DemandeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Demande and its DTO DemandeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DemandeMapper {

    DemandeDTO demandeToDemandeDTO(Demande demande);

    Demande demandeDTOToDemande(DemandeDTO demandeDTO);
}
