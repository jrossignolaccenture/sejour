package fr.minint.sief.web.rest.mapper;

import org.mapstruct.Mapper;

import fr.minint.sief.domain.Address;
import fr.minint.sief.web.rest.dto.AddressDTO;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {DocumentMapper.class})
public interface AddressMapper {

    AddressDTO addressToAddressDTO(Address address);

    Address addressDTOToAddress(AddressDTO addressDTO);
}
