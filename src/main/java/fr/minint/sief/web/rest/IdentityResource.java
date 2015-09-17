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

import fr.minint.sief.domain.Identity;
import fr.minint.sief.repository.IdentityRepository;
import fr.minint.sief.web.rest.dto.IdentityDTO;
import fr.minint.sief.web.rest.mapper.IdentityMapper;
import fr.minint.sief.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Identity.
 */
@RestController
@RequestMapping("/api")
public class IdentityResource {

    private final Logger log = LoggerFactory.getLogger(IdentityResource.class);

    @Inject
    private IdentityRepository identityRepository;

    @Inject
    private IdentityMapper identityMapper;

    /**
     * POST  /identitys -> Create a new identity.
     */
    @RequestMapping(value = "/identitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IdentityDTO> create(@Valid @RequestBody IdentityDTO identityDTO) throws URISyntaxException {
        log.debug("REST request to save Identity : {}", identityDTO);
        if (identityDTO.getPassportNumber() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new identity cannot already have an ID").body(null);
        }
        Identity identity = identityMapper.identityDTOToIdentity(identityDTO);
        Identity result = identityRepository.save(identity);
        return ResponseEntity.created(new URI("/api/identitys/" + result.getPassportNumber()))
                .headers(HeaderUtil.createEntityCreationAlert("identity", result.getPassportNumber().toString()))
                .body(identityMapper.identityToIdentityDTO(result));
    }

    /**
     * PUT  /identitys -> Updates an existing identity.
     */
    @RequestMapping(value = "/identitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IdentityDTO> update(@Valid @RequestBody IdentityDTO identityDTO) throws URISyntaxException {
        log.debug("REST request to update Identity : {}", identityDTO);
        if (identityDTO.getPassportNumber() == null) {
            return create(identityDTO);
        }
        Identity identity = identityMapper.identityDTOToIdentity(identityDTO);
        Identity result = identityRepository.save(identity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("identity", identityDTO.getPassportNumber().toString()))
                .body(identityMapper.identityToIdentityDTO(result));
    }

    /**
     * GET  /identitys -> get all the identitys.
     */
    @RequestMapping(value = "/identitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<IdentityDTO> getAll() {
        log.debug("REST request to get all Identitys");
        return identityRepository.findAll().stream()
            .map(identity -> identityMapper.identityToIdentityDTO(identity))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /identitys/:id -> get the "id" identity.
     */
    @RequestMapping(value = "/identitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IdentityDTO> get(@PathVariable String id) {
        log.debug("REST request to get Identity : {}", id);
        return Optional.ofNullable(identityRepository.findOne(id))
            .map(identityMapper::identityToIdentityDTO)
            .map(identityDTO -> new ResponseEntity<>(
                identityDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /identitys/:id -> delete the "id" identity.
     */
    @RequestMapping(value = "/identitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete Identity : {}", id);
        identityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("identity", id.toString())).build();
    }
}
