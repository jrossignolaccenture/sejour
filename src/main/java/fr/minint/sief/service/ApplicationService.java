package fr.minint.sief.service;

import static fr.minint.sief.domain.enumeration.ApplicationNature.naturalisation;
import static fr.minint.sief.domain.enumeration.ApplicationNature.sejour_etudiant;
import static fr.minint.sief.domain.enumeration.ApplicationStatus.validated;
import static fr.minint.sief.domain.enumeration.ApplicationType.premiere;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.minint.sief.domain.Address;
import fr.minint.sief.domain.Application;
import fr.minint.sief.domain.Identity;
import fr.minint.sief.domain.Project;
import fr.minint.sief.domain.User;
import fr.minint.sief.domain.enumeration.ApplicationNature;
import fr.minint.sief.domain.enumeration.ApplicationStatus;
import fr.minint.sief.domain.enumeration.ApplicationType;
import fr.minint.sief.domain.enumeration.SexType;
import fr.minint.sief.repository.ApplicationRepository;
import fr.minint.sief.security.SecurityUtils;

/**
 * Service class for managing application.
 */
@Service
public class ApplicationService {

	private final Logger log = LoggerFactory.getLogger(ApplicationService.class);

	@Inject
	private UserService userService;

	@Inject
	private ApplicationRepository applicationRepository;

	/**
	 * Get applications owned by logged user
	 * @return list of applications
	 */
	public List<Application> getUserApplications() {
		User currentUser = userService.getUser();
		return applicationRepository.findByEmailOrderByCreationDateDesc(currentUser.getEmail());
	}

	/**
	 * Get the application with specific status owned by logged user
	 * @param status Status of the application to look for
	 * @return An application
	 */
	public Application getCurrentApplication(ApplicationStatus status) {
		User currentUser = userService.getUser();
		return applicationRepository.findOneByEmailAndStatut(currentUser.getEmail(), status);
	}

	/**
	 * Create an application
	 * 
	 * @param type
	 *            The type of the application to create
	 * @param nature
	 *            The nature of the application to create
	 * @return the id of the new created application
	 */
	public String createApplication(ApplicationType type, ApplicationNature nature) {
		// Delete old draft application
		Application oldDraftApplication = getCurrentApplication(ApplicationStatus.draft);
		if (oldDraftApplication != null) {
			applicationRepository.delete(oldDraftApplication);
		}

		// Create new application
		Application application = new Application();
		application.setEmail(SecurityUtils.getCurrentLogin());
		application.setType(type);
		application.setNature(nature);
		application.setModificationDate(DateTime.now());
		
		
		// Get identity and address from user infos
		User currentUser = userService.getUser();
		application.setUserId(currentUser.getId()); // This is ugly for now (we don't need a user id reference in the application)
		application.setIdentity(currentUser.getIdentity());
		application.setAddress(currentUser.getAddress());
		
		application.setProject(new Project());
		
		if (nature == sejour_etudiant) {
			updateWithCampusInfos(application);
		} else if (nature == naturalisation) {
			// TODO pas la meilleure manière de gérer l'affichage de la francisation...
			application.getIdentity().setFrancisation(false);
		}
		
		List<Application> validatedApps = applicationRepository.findByStatutAndEmailOrderByCreationDateAsc(validated, application.getEmail());
		if(validatedApps.size() > 0) {
			// TODO Potentiellement il y aura d'autre chose à ajouter (admissibilityDate), voir aussi comment faire ça un peu mieux
			application.setBiometricsDate(validatedApps.get(validatedApps.size()-1).getBiometricsDate());
		}
		application = applicationRepository.save(application);

		return application.getId();
	}

	public void updateWithCampusInfos(Application application) {
		// Call campus (badass style)
		if ("kim.soon.jeen@gmail.com".equals(application.getEmail())) {
			// IDENTITY
			Identity identity = application.getIdentity();
			identity.setLastName("Kim");
			identity.setFirstName("Soon-jeen");
			identity.setSex(SexType.F);
			identity.setBirthDate(DateTime.parse("1992-12-10T00:00"));
			identity.setBirthCity("Séoul");
			identity.setBirthCountry("KR");
			identity.setNationality("KR");
			identity.setPassportNumber("5577997");
			// ADDRESS
			Address address = application.getAddress();
			address.setPhone("+82 51 999999");
			address.setEmail(application.getEmail());
			// PROJECT
			Project project = application.getProject();
			project.setUniversity("Télécom Paris Tech");
			project.setTraining("Master of Science in Multimedia Information Technologies");
			if(application.getType() == premiere) {
				project.setTrainingStart(DateTime.parse("2016-01-05T00:00"));
				project.setTrainingLength(12);
			}
		}
	}
}
