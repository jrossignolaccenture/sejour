package fr.minint.sief.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.minint.sief.domain.Demande;
import fr.minint.sief.repository.DemandeRepository;
import fr.minint.sief.web.rest.util.HeaderUtil;
import fr.minint.sief.web.rest.dto.DemandeDTO;
import fr.minint.sief.web.rest.mapper.DemandeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Demande.
 */
@RestController
@RequestMapping("/api")
public class DemandeResource {

    private final Logger log = LoggerFactory.getLogger(DemandeResource.class);

    @Inject
    private DemandeRepository demandeRepository;

    @Inject
    private DemandeMapper demandeMapper;

    /**
     * POST  /demandes -> Create a new demande.
     */
    @RequestMapping(value = "/demandes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DemandeDTO> create(@Valid @RequestBody DemandeDTO demandeDTO) throws URISyntaxException {
        log.debug("REST request to save Demande : {}", demandeDTO);
        if (demandeDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new demande cannot already have an ID").body(null);
        }
        Demande demande = demandeMapper.demandeDTOToDemande(demandeDTO);
        Demande result = demandeRepository.save(demande);
        return ResponseEntity.created(new URI("/api/demandes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("demande", result.getId().toString()))
                .body(demandeMapper.demandeToDemandeDTO(result));
    }

    /**
     * PUT  /demandes -> Updates an existing demande.
     */
    @RequestMapping(value = "/demandes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DemandeDTO> update(@Valid @RequestBody DemandeDTO demandeDTO) throws URISyntaxException {
        log.debug("REST request to update Demande : {}", demandeDTO);
        if (demandeDTO.getId() == null) {
            return create(demandeDTO);
        }
        Demande demande = demandeMapper.demandeDTOToDemande(demandeDTO);
        Demande result = demandeRepository.save(demande);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("demande", demandeDTO.getId().toString()))
                .body(demandeMapper.demandeToDemandeDTO(result));
    }

    /**
     * GET  /demandes -> get all the demandes.
     */
    @RequestMapping(value = "/demandes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<DemandeDTO> getAll() {
        log.debug("REST request to get all Demandes");
        return demandeRepository.findAll().stream()
            .map(demande -> demandeMapper.demandeToDemandeDTO(demande))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /demandes/:id -> get the "id" demande.
     */
    @RequestMapping(value = "/demandes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DemandeDTO> get(@PathVariable String id) {
        log.debug("REST request to get Demande : {}", id);
        return Optional.ofNullable(demandeRepository.findOne(id))
            .map(demandeMapper::demandeToDemandeDTO)
            .map(demandeDTO -> new ResponseEntity<>(
                demandeDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /demandes/:id -> delete the "id" demande.
     */
    @RequestMapping(value = "/demandes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete Demande : {}", id);
        demandeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("demande", id.toString())).build();
    }
}
