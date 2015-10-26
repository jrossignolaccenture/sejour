package fr.minint.sief.web.rest;

import static fr.minint.sief.domain.enumeration.StatutDemande.identity_verified;
import static fr.minint.sief.domain.enumeration.StatutDemande.receivable;
import static fr.minint.sief.domain.enumeration.TypeDemande.premiere;

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
import fr.minint.sief.domain.enumeration.NatureDemande;
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

/**
 * REST controller for managing Demande.
 */
@RestController
@RequestMapping("/api")
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

    @Inject
    private DemandeRepository applicationRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MailService mailService;
    
    @Inject
    private DemandeService demandeService;

    @Inject
    private DemandeMapper demandeMapper;

    /**
     * POST  /application -> create an application
     * 
     * @param type The type of the application to create
     * @param nature The nature of the application to create
     * @return The id of the new created application
     */
    @RequestMapping(value = "/application", 
    				method = RequestMethod.POST,
    				produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<String> create(@Valid @RequestParam TypeDemande type, @Valid @RequestParam NatureDemande nature) {
        log.debug("REST request to create application : {} {}", type, nature);
        String id = demandeService.createApplication(type, nature);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    
	/**
	 * DELETE /application/{id} -> Delete the application corresponding to the specified id
	 * 
     * @param id The id of the application to delete
     * @return HttpStatus
     */
	@RequestMapping(value = "/application/{id}", 
					method = RequestMethod.DELETE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> delete(@PathVariable String id) {
		log.debug("REST request to delete application : {}", id);
		return Optional.ofNullable(applicationRepository.findOne(id))
				.filter(application -> application.getEmail().equals(SecurityUtils.getCurrentLogin()))
				.map(application -> {
					applicationRepository.delete(application.getId());
					return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

    /**
     * GET /application -> Get all application owned by current user
     * 
     * @return List of application owned by current user
     */
	@RequestMapping(value = "/application", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<DemandeDTO> get() {
		log.debug("REST request to get all application owned by current user");
		return demandeService.getUserDemandes().stream()
				.map(demandeMapper::demandeToDemandeDTO)
	            .collect(Collectors.toCollection(LinkedList::new));
	}
    
	/**
	 * GET /application/{id} -> Get an application corresponding to the specified id
	 * 
     * @param id The id of the application to look for
     * @return An existing application
     */
	@RequestMapping(value = "/application/{id}", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<DemandeDTO> get(@PathVariable String id) {
		log.debug("REST request to get application : {}", id);
		return Optional.ofNullable(applicationRepository.findOne(id))
				.map(demandeMapper::demandeToDemandeDTO)
				.map(applicationDTO -> new ResponseEntity<>(applicationDTO, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	/**
	 * Get /application/statut -> Get application corresponding to the specified status
	 * 
	 * @param status The status of the application to look for
	 * @return All application that have the status specified
	 */
	@RequestMapping(value = "/application/statut", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<DemandeDTO> getByStatus(@RequestParam StatutDemande status, String email) {
		log.debug("REST request to get application by statut {} and email {}", status, email);
		List<Demande> applications = email == null ? applicationRepository.findByStatutOrderByCreationDateAsc(status) 
				: applicationRepository.findByStatutAndEmailOrderByCreationDateAsc(status, email);
		return applications
				.stream()
				.map(demandeMapper::demandeToDemandeDTO)
				.collect(Collectors.toCollection(LinkedList::new));
	}
    
	/**
	 * GET /application/count -> get count of all application in three status : recevability, identification and decision
	 * 
	 * @return The count of application
	 */
	@RequestMapping(value = "/application/count", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<DemandeCountDTO> getCount() {
		log.debug("REST request to get count of application not archived");
		// TODO Tout mettre en une seule requete & accessoirement revoir tout ça pour gérer plus de statut
		// -> on renvoit une liste de type (?) et on laisse le front gérer le count qu'il veut
		DemandeCountDTO count = new DemandeCountDTO();
		count.setNbPaid(applicationRepository.countByStatut(StatutDemande.paid));
		count.setNbScheduled(applicationRepository.countByStatut(StatutDemande.scheduled));
		count.setNbIdentityVerified(applicationRepository.countByStatut(StatutDemande.identity_verified));
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
	
	/**
     * PUT  /application -> Update an existing application and update user info with application info
     * 
	 * @param applicationDTO The application to update
	 * @return HttpStatus
	 */
    @RequestMapping(value = "/application",
			        method = RequestMethod.PUT,
			        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> update(@Valid @RequestBody DemandeDTO applicationDTO) {
        log.debug("REST request to update application : {}", applicationDTO);
        return Optional.ofNullable(applicationDTO)
        		.filter(application -> application.getEmail().equals(SecurityUtils.getCurrentLogin()))
        		.map(demandeMapper::demandeDTOToDemande)
        		.map(application -> {
        			application.setModificationDate(DateTime.now());
        			applicationRepository.save(application);
        			
        			// Update user infos
                    return userRepository.findOneByEmail(application.getEmail())
	                    	.map(user -> {
	                    		if(application.getIdentity() != null) {
		                    		user.setFirstName(application.getIdentity().getFirstName());
		                    		user.setLastName(application.getIdentity().getLastName());
		                    		//TODO A CLONER
		                    		user.setIdentity(application.getIdentity());
	                    		}
	                    		if(application.getProject() != null) {
	                    			user.setComingDate(application.getProject().getComingDate());
	                    		}
	                    		if(application.getAddress() != null) {
	                    			//TODO A CLONER
	                    			user.setFrenchAddress(application.getAddress());
	                    		}
	                    		userRepository.save(user);
	                    		
	                    		return new ResponseEntity<String>(HttpStatus.OK);
	                    	})
	                    	.orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        		})
        		.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
	/**
     * PUT  /application -> Pay for an application and send a mail
     * 
	 * @param id The id of the application that need payment
	 * @param request The HttpServletRequest used to get base url
	 * @return HttpStatus
	 */
    @RequestMapping(value = "/application/pay",
			        method = RequestMethod.PUT,
			        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> pay(@Valid @RequestBody String id, HttpServletRequest request) {
        log.debug("REST request to submit application : {}", id);
        return Optional.ofNullable(applicationRepository.findOne(id))
        		.filter(application -> application.getEmail().equals(SecurityUtils.getCurrentLogin()))
            	.map(application -> {
            		application.setStatut(StatutDemande.paid);
            		application.setPaymentDate(DateTime.now());
                    applicationRepository.save(application);
                    mailService.sendApplicationPaidEmail(application, getBaseUrl(request));
                    return new ResponseEntity<String>(HttpStatus.OK);
            	})
            	.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

	/**
	 * PUT /application/admissibility -> Make an application receivable and send a mail
	 * 
	 * @param id The id of the application that need to be made receivable
	 * @param request The HttpServletRequest used to get base url
     * @return HttpStatus
     */
	@RequestMapping(value = "/application/admissibility", 
					method = RequestMethod.PUT, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> verify(@Valid @RequestBody String id, HttpServletRequest request) {
		log.debug("REST request to make receivable application : {}", id);
		return Optional.ofNullable(applicationRepository.findOne(id))
				.map(application -> {
					application.setStatut(application.getType() == premiere ? receivable : identity_verified);
					application.setAdmissibilityDate(DateTime.now());
					applicationRepository.save(application);
					if(application.getType() == premiere) {
						mailService.sendApplicationReceivableEmail(application, getBaseUrl(request));
					}
                    return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	/**
	 * PUT /application/schedule -> Schedule an application to a specified date and send a mail
	 * 
	 * @param id The id of the application to scheduled
	 * @param rdvDate The scheduled date for the application
	 * @param request The HttpServletRequest used to get base url
	 * @return HttpStatus
	 */
	@RequestMapping(value = "/application/rdv", 
					method = RequestMethod.PUT, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> rdv(@Valid @RequestBody DemandeDTO applicationDTO, HttpServletRequest request) {
		log.debug("REST request to schedule application : {}", applicationDTO);
		return Optional.ofNullable(applicationRepository.findOne(applicationDTO.getId()))
        		.filter(application -> application.getEmail().equals(SecurityUtils.getCurrentLogin()))
				.map(application -> {
					application.setStatut(StatutDemande.scheduled);
					application.setRdvDate(applicationDTO.getRdvDate());
					applicationRepository.save(application);
					mailService.sendApplicationScheduledEmail(application, getBaseUrl(request));
                    return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	/**
	 * PUT /application/identification/documents -> Update application with documents identification
	 * 
	 * @param id The id of the documents' application that need to be made identified
     * @return HttpStatus
     */
	@RequestMapping(value = "/application/identification/documents", 
					method = RequestMethod.PUT, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> identifyDocuments(@Valid @RequestBody String id) {
		log.debug("REST request to make identified documents' application : {}", id);
		return Optional.ofNullable(applicationRepository.findOne(id))
				.map(application -> {
					application.setDocumentsDate(DateTime.now());
					if(application.getBiometricsDate() != null) {
						application.setStatut(StatutDemande.identity_verified);
					}
					applicationRepository.save(application);
                    return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	/**
	 * PUT /application/identification/biometrics -> Update application with biometrics identification
	 * 
	 * @param id The id of the biometrics' application that need to be made identified
     * @return HttpStatus
     */
	@RequestMapping(value = "/application/identification/biometrics", 
					method = RequestMethod.PUT, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> identifyBiometrics(@Valid @RequestBody String id) {
		log.debug("REST request to make identified biometrics' application : {}", id);
		return Optional.ofNullable(applicationRepository.findOne(id))
				.map(application -> {
					application.setBiometricsDate(DateTime.now());
					if(application.getDocumentsDate() != null) {
						application.setStatut(StatutDemande.identity_verified);
					}
					applicationRepository.save(application);
                    return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	/**
	 * PUT /application/validation -> Make an application validated and send a mail
	 * 
	 * @param id The id of the application that need to be validated
	 * @param request The HttpServletRequest used to get base url
     * @return HttpStatus
     */
	@RequestMapping(value = "/application/validation", 
					method = RequestMethod.PUT, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> validate(@Valid @RequestBody String id, HttpServletRequest request) {
		log.debug("REST request to validate application : {}", id);
		return Optional.ofNullable(applicationRepository.findOne(id))
				.map(application -> {
					application.setStatut(StatutDemande.validated);
					application.setDecisionDate(DateTime.now());
					applicationRepository.save(application);
					mailService.sendApplicationValidatedEmail(application, getBaseUrl(request));
					// Send more emails to have to be send by batch in reality
					mailService.sendPermitEmail(application, getBaseUrl(request));
					if(application.getType() == premiere) {
						mailService.sendArrivalEmail(application, getBaseUrl(request));
					}
                    return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}
    
    private String getBaseUrl(HttpServletRequest request) {
    	return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
