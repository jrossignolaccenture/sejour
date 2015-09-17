package fr.minint.sief.web.rest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import fr.minint.sief.domain.Demande;
import fr.minint.sief.domain.enumeration.StatutDemande;
import fr.minint.sief.repository.DemandeRepository;
import fr.minint.sief.service.DemandeService;
import fr.minint.sief.web.rest.dto.DemandeDTO;
import fr.minint.sief.web.rest.mapper.DemandeMapper;
import fr.minint.sief.web.rest.util.HeaderUtil;

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
    private DemandeService demandeService;

    @Inject
    private DemandeMapper demandeMapper;

    /**
     * PUT  /demandes -> Updates an existing demande.
     */
    @RequestMapping(value = "/demande/init",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void init() throws URISyntaxException {
        log.debug("REST request to init Demande : {}");
        demandeService.initWithCampus();
    }

    /**
     * GET  /service -> get all the services of a team.
     */
    @RequestMapping(value = "/demande/inprogress",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Demande getInProgressDemande(@RequestParam(value = "email") String email) {
        log.debug("REST request to get initiated demande with mail {}", email);
        return demandeRepository.findOneByEmailAndStatut(email, StatutDemande.init);
    }

    /**
     * PUT  /demandes -> Updates an existing demande.
     */
    @RequestMapping(value = "/demande",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DemandeDTO> update(@Valid @RequestBody DemandeDTO demandeDTO) throws URISyntaxException {
        log.debug("REST request to update Demande : {}", demandeDTO);
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
