package fr.minint.sief.web.rest.mapper;

import org.mapstruct.Mapper;

import fr.minint.sief.domain.Person;
import fr.minint.sief.web.rest.dto.PersonDTO;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {IdentityMapper.class, AddressMapper.class})
public interface PersonMapper {

    PersonDTO personToPersonDTO(Person person);

    Person personDTOToPerson(PersonDTO personDTO);
}
