package fr.minint.sief.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import fr.minint.sief.domain.Address;
import fr.minint.sief.repository.AddressRepository;
import fr.minint.sief.web.rest.dto.AddressDTO;
import fr.minint.sief.web.rest.mapper.AddressMapper;
import fr.minint.sief.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    @Inject
    private AddressRepository addressRepository;

    @Inject
    private AddressMapper addressMapper;

    /**
     * POST  /addresss -> Create a new address.
     */
    @RequestMapping(value = "/addresss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressDTO> create(@Valid @RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to save Address : {}", addressDTO);
        if (addressDTO.getPhone() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new address cannot already have an ID").body(null);
        }
        Address address = addressMapper.addressDTOToAddress(addressDTO);
        Address result = addressRepository.save(address);
        return ResponseEntity.created(new URI("/api/addresss/" + result.getPhone()))
                .headers(HeaderUtil.createEntityCreationAlert("address", result.getPhone().toString()))
                .body(addressMapper.addressToAddressDTO(result));
    }

    /**
     * PUT  /addresss -> Updates an existing address.
     */
    @RequestMapping(value = "/addresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressDTO> update(@Valid @RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to update Address : {}", addressDTO);
        if (addressDTO.getPhone() == null) {
            return create(addressDTO);
        }
        Address address = addressMapper.addressDTOToAddress(addressDTO);
        Address result = addressRepository.save(address);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("address", addressDTO.getPhone().toString()))
                .body(addressMapper.addressToAddressDTO(result));
    }

    /**
     * GET  /addresss -> get all the addresss.
     */
    @RequestMapping(value = "/addresss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<AddressDTO> getAll() {
        log.debug("REST request to get all Addresss");
        return addressRepository.findAll().stream()
            .map(address -> addressMapper.addressToAddressDTO(address))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /addresss/:id -> get the "id" address.
     */
    @RequestMapping(value = "/addresss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressDTO> get(@PathVariable String id) {
        log.debug("REST request to get Address : {}", id);
        return Optional.ofNullable(addressRepository.findOne(id))
            .map(addressMapper::addressToAddressDTO)
            .map(addressDTO -> new ResponseEntity<>(
                addressDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /addresss/:id -> delete the "id" address.
     */
    @RequestMapping(value = "/addresss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete Address : {}", id);
        addressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("address", id.toString())).build();
    }
}
