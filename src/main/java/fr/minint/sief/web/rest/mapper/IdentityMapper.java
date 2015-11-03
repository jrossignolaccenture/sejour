package fr.minint.sief.web.rest.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.mapstruct.Mapper;

import fr.minint.sief.domain.Identity;
import fr.minint.sief.domain.Person;
import fr.minint.sief.domain.enumeration.PersonType;
import fr.minint.sief.web.rest.dto.IdentityDTO;
import fr.minint.sief.web.rest.dto.PersonDTO;

/**
 * Mapper for the entity Identity and its DTO IdentityDTO.
 */
@Mapper(componentModel = "spring", uses = {DocumentMapper.class})
public abstract class IdentityMapper {
	
	@Inject
	private PersonMapper personMapper;

    public abstract IdentityDTO identityToIdentityDTO(Identity identity);

    public abstract Identity identityDTOToIdentity(IdentityDTO identityDTO);
    
    public Map<PersonType,List<PersonDTO>> mapFamily(Map<PersonType,List<Person>> value) {
    	return value
    			.entrySet()
    			.stream()
    			.collect(
    					Collectors.toMap(
    							Map.Entry::getKey, 
    							e -> e.getValue()
    									.stream()
    									.map(personMapper::personToPersonDTO)
    									.collect(Collectors.toList())));
    }
    
    public Map<PersonType,List<Person>> mapFamilyDTO(Map<PersonType,List<PersonDTO>> value) {
    	return value
    			.entrySet()
    			.stream()
    			.collect(
    					Collectors.toMap(
    							Map.Entry::getKey, 
    							e -> e.getValue()
    									.stream()
    									.map(personMapper::personDTOToPerson)
    									.collect(Collectors.toList())));
    }
}
