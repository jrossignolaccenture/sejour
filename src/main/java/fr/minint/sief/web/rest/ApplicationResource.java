package fr.minint.sief.web.rest;

import static fr.minint.sief.domain.enumeration.ApplicationNature.naturalisation;
import static fr.minint.sief.domain.enumeration.ApplicationNature.sejour_etudiant;
import static fr.minint.sief.domain.enumeration.ApplicationStatus.favorable_proposal;
import static fr.minint.sief.domain.enumeration.ApplicationStatus.identity_verified;
import static fr.minint.sief.domain.enumeration.ApplicationStatus.paid;
import static fr.minint.sief.domain.enumeration.ApplicationStatus.receivable;
import static fr.minint.sief.domain.enumeration.ApplicationStatus.scheduled;
import static fr.minint.sief.domain.enumeration.ApplicationStatus.validated;
import static fr.minint.sief.domain.enumeration.ApplicationType.premiere;
import static fr.minint.sief.domain.enumeration.ApplicationType.renouvellement;
import static java.util.Arrays.asList;

import java.util.ArrayList;
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

import fr.minint.sief.domain.Application;
import fr.minint.sief.domain.enumeration.ApplicationNature;
import fr.minint.sief.domain.enumeration.ApplicationStatus;
import fr.minint.sief.domain.enumeration.ApplicationType;
import fr.minint.sief.repository.ApplicationRepository;
import fr.minint.sief.repository.UserRepository;
import fr.minint.sief.security.SecurityUtils;
import fr.minint.sief.service.ApplicationService;
import fr.minint.sief.service.MailService;
import fr.minint.sief.web.rest.dto.ApplicationCountDTO;
import fr.minint.sief.web.rest.dto.ApplicationDTO;
import fr.minint.sief.web.rest.mapper.ApplicationMapper;

/**
 * REST controller for managing Application.
 */
@RestController
@RequestMapping("/api")
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

    @Inject
    private ApplicationRepository applicationRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MailService mailService;
    
    @Inject
    private ApplicationService applicationService;

    @Inject
    private ApplicationMapper applicationMapper;

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
    public ResponseEntity<String> create(@Valid @RequestParam ApplicationType type, @Valid @RequestParam ApplicationNature nature) {
        log.debug("REST request to create application : {} {}", type, nature);
        String id = applicationService.createApplication(type, nature);
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
	public List<ApplicationDTO> get() {
		log.debug("REST request to get all application owned by current user");
		return applicationService.getUserApplications().stream()
				.map(applicationMapper::applicationToApplicationDTO)
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
	public ResponseEntity<ApplicationDTO> get(@PathVariable String id) {
		log.debug("REST request to get application : {}", id);
		return Optional.ofNullable(applicationRepository.findOne(id))
				.map(applicationMapper::applicationToApplicationDTO)
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
	public List<ApplicationDTO> getByStatus(@RequestParam List<ApplicationStatus> status, String email) {
		log.debug("REST request to get application by statut {} and email {}", status, email);
		List<Application> applications = email == null ? applicationRepository.findByStatutInOrderByCreationDateAsc(status) 
				: applicationRepository.findByStatutInAndEmailOrderByCreationDateAsc(status, email);
		return applications
				.stream()
				.map(applicationMapper::applicationToApplicationDTO)
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
	public ResponseEntity<ApplicationCountDTO> getCount() {
		log.debug("REST request to get count of application not archived");
		// TODO Tout mettre en une seule requete & accessoirement revoir tout ça pour gérer plus de statut
		// -> on renvoit une liste de type (?) et on laisse le front gérer le count qu'il veut
		ApplicationCountDTO count = new ApplicationCountDTO();
		count.setNbPaid(applicationRepository.countByStatutIn(asList(paid)));
		count.setNbScheduled(applicationRepository.countByStatutIn(asList(scheduled)));
		count.setNbIdentityVerified(applicationRepository.countByStatutIn(asList(identity_verified, favorable_proposal)));
		count.setNbCivilStateToReconstruct(applicationRepository.countByStatutInAndNatureAndReconstructionDateIsNull(validated, naturalisation));
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
    public ResponseEntity<?> update(@Valid @RequestBody ApplicationDTO applicationDTO) {
        log.debug("REST request to update application : {}", applicationDTO);
        Application newApplication = applicationMapper.applicationDTOToApplication(applicationDTO);
        return Optional.ofNullable(applicationRepository.findOne(applicationDTO.getId()))
        		.filter(application -> application.getEmail().equals(SecurityUtils.getCurrentLogin()))
        		.map(application -> {
        			application.setModificationDate(DateTime.now());
        			application.setIdentity(newApplication.getIdentity());
        			application.setAddress(newApplication.getAddress());
        			application.setProject(newApplication.getProject());
        			applicationRepository.save(application);
        			return new ResponseEntity<>(HttpStatus.OK);
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
            		application.setStatut(ApplicationStatus.paid);
            		application.setPaymentDate(DateTime.now());
            		
                    applicationRepository.findFirstByStatutInAndEmailOrderByDecisionDateDesc(validated, application.getEmail())
                    	.ifPresent(lastApplication -> {
                    		if(!application.getIdentity().equals(lastApplication.getIdentity())) {
                    			application.getIdentity().setAdmissible(null);
                    			application.getIdentity().setValidateOn(null);
                    			// TODO Voir pour gérer la famille séparemment du reste de l'identité
                    			application.getIdentity().setFamilyAdmissible(null);
                    			application.getIdentity().setFamilyValidateOn(null);
                    		}
                    		if(!application.getAddress().equals(lastApplication.getAddress())) {
                    			application.getAddress().setAdmissible(null);
                    			application.getAddress().setValidateOn(null);
                    		}
                    	});
                    
                    applicationRepository.save(application);
                    mailService.sendApplicationPaidEmail(application, getBaseUrl(request));
                    
                    // Update user infos
                    return userRepository.findOneByEmail(application.getEmail())
	                    	.map(user -> {
	                    		user.setIdentity(application.getIdentity());
                    			user.setComingDate(application.getProject().getComingDate());
                    			user.setAddress(application.getAddress());
	                    		userRepository.save(user);
	                    		
	                    		return new ResponseEntity<>(HttpStatus.OK);
	                    	})
	                    	.orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
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
					application.setStatut(application.getType() == renouvellement ? identity_verified : receivable);
					application.setAdmissibility(DateTime.now());
					applicationRepository.save(application);
					
					if(application.getStatut() == receivable) {
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
	public ResponseEntity<?> rdv(@Valid @RequestBody ApplicationDTO applicationDTO, HttpServletRequest request) {
		log.debug("REST request to schedule application : {}", applicationDTO);
		return Optional.ofNullable(applicationRepository.findOne(applicationDTO.getId()))
        		.filter(application -> application.getEmail().equals(SecurityUtils.getCurrentLogin()))
				.map(application -> {
					application.setStatut(ApplicationStatus.scheduled);
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
					DateTime now = DateTime.now();
					application.getIdentity().validateNewDocuments(now);
					// TODO Revoir moyen de décider que l'étape est passée
					if((application.getNature() != naturalisation && application.getBiometricsDate() != null)
							|| (application.getNature() == naturalisation && application.getInterviewDate() != null)) {
						application.setStatut(ApplicationStatus.identity_verified);
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
					// TODO Revoir moyen de décider que l'étape est passée
					if(!application.getIdentity().hasDocumentToValidate()) {
						application.setStatut(ApplicationStatus.identity_verified);
					}
					applicationRepository.save(application);
                    return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

    /**
     * POST  /application/interview -> Update application with interview report
     * 
     * @param id The id of the application to update
     * @param report The interview report
     * @return HttpStatus
     */
    @RequestMapping(value = "/application/interview", 
    				method = RequestMethod.POST,
    				produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> interview(@Valid @RequestParam String id, @Valid @RequestParam String report) {
        log.debug("REST request to interview : {} {}", id, report);
        return Optional.ofNullable(applicationRepository.findOne(id))
        		.map(application -> {
        			application.setInterviewDate(DateTime.now());
        			application.setInterviewReport(report);
        			// TODO Revoir moyen de décider que l'étape est passée
					if(!application.getIdentity().hasDocumentToValidate()) {
						application.setStatut(ApplicationStatus.identity_verified);
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
					DateTime now = DateTime.now();
					if(application.getNature() == naturalisation && application.getStatut() == identity_verified) {
						application.setStatut(favorable_proposal);
						application.getProject().setValidateOn(now);
					} else {
						application.setStatut(validated);
						application.setDecisionDate(now);
					}
					applicationRepository.save(application);
					mailService.sendApplicationValidatedEmail(application, getBaseUrl(request));
					// Send more emails to have to be send by batch in reality
					if(application.getNature() == sejour_etudiant) {
						mailService.sendPermitEmail(application, getBaseUrl(request));
						if(application.getType() == premiere) {
							mailService.sendArrivalEmail(application, getBaseUrl(request));
						}
					}
                    return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	/**
	 * GET /application/reconstruction -> Get applications that need to be reconstruct
	 * 
     * @return List of application that need to be reconstruct
     */
	@RequestMapping(value = "/application/reconstruct", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<ApplicationDTO> getApplicationToReconstruct() {
		log.debug("REST request to get applications that need to reconstruct civil state");
		return applicationRepository.findByStatutAndNatureAndReconstructionDateIsNull(validated, naturalisation)
				.stream()
				.map(applicationMapper::applicationToApplicationDTO)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * PUT /application/reconstruction -> Reconstruct a civil state
	 * 
	 * @param id The id of the application that need to be reconstruct
     * @return HttpStatus
     */
	@RequestMapping(value = "/application/reconstruct", 
					method = RequestMethod.PUT, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> reconstruct(@Valid @RequestBody String id) {
		log.debug("REST request to application that need to reconstruct civil state : {}", id);
		return Optional.ofNullable(applicationRepository.findOne(id))
				.map(application -> {
					application.setReconstructionDate(DateTime.now());
					applicationRepository.save(application);
                    return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}
    
    private String getBaseUrl(HttpServletRequest request) {
    	return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
