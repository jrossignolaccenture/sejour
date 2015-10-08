package fr.minint.sief.web.rest;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import fr.minint.sief.domain.Demande;
import fr.minint.sief.domain.enumeration.StatutDemande;
import fr.minint.sief.domain.enumeration.TypeDemande;
import fr.minint.sief.repository.DemandeRepository;
import fr.minint.sief.repository.UserRepository;
import fr.minint.sief.security.SecurityUtils;
import fr.minint.sief.service.DemandeService;
import fr.minint.sief.service.MailService;
import fr.minint.sief.web.rest.dto.DemandeCountDTO;
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
    private UserRepository userRepository;

    @Inject
    private MailService mailService;
    
    @Inject
    private DemandeService demandeService;

    @Inject
    private DemandeMapper demandeMapper;

    /**
     * PUT  /demande/init -> init a premiere demande.
     */
    @RequestMapping(value = "/demande/init",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> init() throws URISyntaxException {
        log.debug("REST request to init Demande : {}");
        demandeService.initWithCampus();
        return ResponseEntity.ok().body(null);
    }

    /**
     * PUT  /demande/initRenewal -> init a renewal demande.
     */
    @RequestMapping(value = "/demande/initRenewal",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> initRenewal() throws URISyntaxException {
        log.debug("REST request to init Demande : {}");
        demandeService.initWithUserInfo();
        return ResponseEntity.ok().body(null);
    }
    
	/**
	 * GET /demandes/count -> get count of all demande.
	 */
	@RequestMapping(value = "/demandes/count", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<DemandeCountDTO> getCount() {
		log.debug("REST request to get all Demande");
		// TODO Tout mettre en une seule requete
		DemandeCountDTO count = new DemandeCountDTO();
		count.setNbRecevability(demandeRepository.countByStatut(StatutDemande.recevability));
		count.setNbIdentification(demandeRepository.countByStatut(StatutDemande.identification));
		count.setNbDecision(demandeRepository.countByStatut(StatutDemande.decision));
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
    
	/**
	 * GET /demandes/currentCount -> get count of all demande of current user.
	 */
	@RequestMapping(value = "/demandes/currentCount", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Long> getCurrentCount() {
		log.debug("REST request to get all Demande");
		// TODO Tout mettre en une seule requete
		Long count = demandeRepository.countByEmailAndStatutNot(SecurityUtils.getCurrentLogin(), StatutDemande.archive);
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
    
	/**
	 * GET /demandes -> get the all demande.
	 */
	@RequestMapping(value = "/demandes", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<DemandeDTO> get() {
		log.debug("REST request to get all Demande");
		return demandeService.getUserDemandes().stream()
				.map(demandeMapper::demandeToDemandeDTO)
	            .collect(Collectors.toCollection(LinkedList::new));
	}
    
	/**
	 * GET /demande/demande/id -> get the "id" demande.
	 */
	@RequestMapping(value = "/demande/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<DemandeDTO> get(@PathVariable String id) {
		log.debug("REST request to get Demande : {}", id);
		return Optional.ofNullable(demandeRepository.findOne(id))
				.map(demandeMapper::demandeToDemandeDTO)
				.map(demandeDTO -> new ResponseEntity<>(demandeDTO, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

    /**
     * GET  /demande -> get demande.
     */
    @RequestMapping(value = "/demande",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DemandeDTO> getDemande(@RequestParam(value = "email") String email, @RequestParam(value = "statut") StatutDemande statut) {
        log.debug("REST request to get initiated demande with mail {} and statut {}", email, statut);
        return Optional.ofNullable(demandeRepository.findOneByEmailAndStatut(email, statut))
	        .map(demandeMapper::demandeToDemandeDTO)
	        .map(demandeDTO -> new ResponseEntity<>(
	            demandeDTO,
	            HttpStatus.OK))
	        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * PUT  /demande/statut -> Get demande by statut.
     */
    @RequestMapping(value = "/demande/statut",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DemandeDTO> getDemande(@RequestParam(value = "statut") StatutDemande statut) throws URISyntaxException {
        log.debug("REST request to get application by statut {}", statut);
        return demandeRepository.findByStatutOrderByCreationDateAsc(statut).stream()
            .map(demande -> demandeMapper.demandeToDemandeDTO(demande))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * PUT  /demande/update -> Update an existing demande.
     */
    @RequestMapping(value = "/demande/update",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DemandeDTO> update(@Valid @RequestBody DemandeDTO demandeDTO) throws URISyntaxException {
        log.debug("REST request to update Demande : {}", demandeDTO);
        Demande demande = demandeMapper.demandeDTOToDemande(demandeDTO);
        demande.setStatut(StatutDemande.draft);
        demande.setModificationDate(DateTime.now());
        Demande result = demandeRepository.save(demande);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("demande", demandeDTO.getId().toString()))
                .body(demandeMapper.demandeToDemandeDTO(result));
    }

    /**
     * PUT  /demande/validate -> Validate an existing demande.
     */
    @RequestMapping(value = "/demande/validate",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> validate(@Valid @RequestBody DemandeDTO demandeDTO) throws URISyntaxException {
        log.debug("REST request to update Demande : {}", demandeDTO);
        return userRepository.findOneByEmail(demandeDTO.getEmail())
        	.filter(u -> u.getEmail().equals(SecurityUtils.getCurrentLogin()))
        	.map(u -> {
        		// Sauvegarde de la demande
        		Demande demande = demandeMapper.demandeDTOToDemande(demandeDTO);
                demande.setStatut(StatutDemande.payment);
                demande.setModificationDate(DateTime.now());
                demandeRepository.save(demande);
                
                //Mise à jour des données utilisateurs
        		u.setFirstName(demande.getIdentity().getFirstName());
        		u.setLastName(demande.getIdentity().getLastName());
        		u.setComingDate(demande.getProject().getComingDate());
        		//TODO A CLONER
        		u.setFrenchAddress(demande.getAddress());
        		u.setIdentity(demande.getIdentity());
        		userRepository.save(u);
        		
        		return new ResponseEntity<String>(HttpStatus.OK);
        	})
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        
    }

    /**
     * PUT  /demande/prepaid -> Updates an existing demande.
     */
    @RequestMapping(value = "/demande/prepaid",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> prepaid(HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to prepaid current demande");
        Demande demande = demandeService.getCurrentDemande(StatutDemande.payment);
        demande.setStatut(StatutDemande.recevability);
        demande.setPaymentDate(DateTime.now());
        demandeRepository.save(demande);
        return Optional.ofNullable(userRepository.findOne(demande.getUserId()))
                .map(user -> {
                    String baseUrl = request.getScheme() +
                        "://" +
                        request.getServerName() +
                        ":" +
                        request.getServerPort();
                    mailService.sendPaymentEmail(user, demande.getPaymentDate(), baseUrl);
                    return new ResponseEntity<String>(HttpStatus.OK);
                }).orElse(new ResponseEntity<String>(HttpStatus.BAD_REQUEST));
    }

    /**
     * PUT  /demande/verify -> Updates an existing demande.
     */
    @RequestMapping(value = "/demande/verify",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> verify(@Valid @RequestBody DemandeDTO demandeDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to verify current demande");
        Demande demande = demandeMapper.demandeDTOToDemande(demandeDTO);
        if(demande.getType() == TypeDemande.premiere) {
        	demande.setStatut(StatutDemande.rdv);
        } else {
        	demande.setStatut(StatutDemande.decision);
        }
        demande.setRecevabilityDate(DateTime.now());
        demandeRepository.save(demande);
        
        if(demande.getType() == TypeDemande.premiere) {
	        return Optional.ofNullable(userRepository.findOne(demande.getUserId()))
	                .map(user -> {
	                    String baseUrl = request.getScheme() +
	                        "://" +
	                        request.getServerName() +
	                        ":" +
	                        request.getServerPort();
	                    mailService.sendRecevabilityEmail(user, baseUrl, demande.getType() == TypeDemande.renouvellement);
	                    return new ResponseEntity<String>(HttpStatus.OK);
	                }).orElse(new ResponseEntity<String>(HttpStatus.BAD_REQUEST));
        } else {
        	return new ResponseEntity<String>(HttpStatus.OK);
        }
    }

    /**
     * PUT  /demande/rdv -> Updates an existing demande.
     */
    @RequestMapping(value = "/demande/rdv",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> rdv(@Valid @RequestBody DemandeDTO demandeDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to rdv");
        Demande demande = demandeMapper.demandeDTOToDemande(demandeDTO);
        demande.setStatut(StatutDemande.identification);
        demandeRepository.save(demande);
        return Optional.ofNullable(userRepository.findOne(demande.getUserId()))
                .map(user -> {
                    String baseUrl = request.getScheme() +
                        "://" +
                        request.getServerName() +
                        ":" +
                        request.getServerPort();
                    mailService.sendRdvEmail(user, demande.getRdvDate(), baseUrl);
                    return new ResponseEntity<String>(HttpStatus.OK);
                }).orElse(new ResponseEntity<String>(HttpStatus.BAD_REQUEST));
    }

    /**
     * PUT  /demande/identification -> Updates an existing demande.
     */
    @RequestMapping(value = "/demande/identification/documents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> documents(@Valid @RequestBody DemandeDTO demandeDTO) throws URISyntaxException {
        log.debug("REST request to identification");
        Demande demande = demandeMapper.demandeDTOToDemande(demandeDTO);
        demande.setDocumentsDate(DateTime.now());
        if(demande.getBiometricsDate() != null) {
        	demande.setStatut(StatutDemande.decision);
        }
        demandeRepository.save(demande);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * PUT  /demande/identification -> Updates an existing demande.
     */
    @RequestMapping(value = "/demande/identification/biometrics",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> biometrics(@Valid @RequestBody DemandeDTO demandeDTO) throws URISyntaxException {
        log.debug("REST request to identification");
        Demande demande = demandeMapper.demandeDTOToDemande(demandeDTO);
        demande.setBiometricsDate(DateTime.now());
        if(demande.getDocumentsDate() != null) {
        	demande.setStatut(StatutDemande.decision);
        }
        demandeRepository.save(demande);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * PUT  /demande/finalDecision -> Updates an existing demande.
     */
    @RequestMapping(value = "/demande/finalDecision",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> finalDecision(@Valid @RequestBody DemandeDTO demandeDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to final decision");
        Demande demande = demandeMapper.demandeDTOToDemande(demandeDTO);
        demande.setStatut(StatutDemande.archive);
        demande.setDecisionDate(DateTime.now());
        demandeRepository.save(demande);
        
        return Optional.ofNullable(userRepository.findOne(demande.getUserId()))
            .map(user -> {
                String baseUrl = request.getScheme() +
                    "://" +
                    request.getServerName() +
                    ":" +
                    request.getServerPort();
                mailService.sendDecisionEmail(user, baseUrl, demande.getType() == TypeDemande.renouvellement);
                mailService.sendPermitEmail(user, baseUrl, demande.getType() == TypeDemande.renouvellement);
                if(demande.getType() == TypeDemande.premiere) {
                	mailService.sendArrivalEmail(user, baseUrl);
                }
                return new ResponseEntity<String>(HttpStatus.OK);
            }).orElse(new ResponseEntity<String>(HttpStatus.BAD_REQUEST));
    }
    
	/**
	 * DELETE /demandes/:id -> delete the "id" demande.
	 */
	@RequestMapping(value = "/demande/{id}", 
			method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable String id) {
		log.debug("REST request to delete Demande : {}", id);
		demandeRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("demande", id.toString())).build();
	}

}
