package fr.minint.sief.service;

import static fr.minint.sief.domain.enumeration.ApplicationNature.sejour_etudiant;
import static fr.minint.sief.domain.enumeration.ApplicationStatus.validated;
import static fr.minint.sief.domain.enumeration.ApplicationType.premiere;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Optional;

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
	 * Create an application
	 * 
	 * @param type
	 *            The type of the application to create
	 * @param nature
	 *            The nature of the application to create
	 * @return the id of the new created application
	 */
	public String createApplication(ApplicationType type, ApplicationNature nature) {

		// Create new application
		Application application = new Application();
		application.setEmail(SecurityUtils.getCurrentLogin());
		application.setType(type);
		application.setNature(nature);
		application.setModificationDate(DateTime.now());
		
		// Copy data from the last application if exist
		Optional<Application> lastApplication = applicationRepository.findFirstByStatutInAndEmailOrderByDecisionDateDesc(asList(validated), application.getEmail());
		if(lastApplication.isPresent()) {
			application.setIdentity(lastApplication.get().getIdentity());
			application.setAddress(lastApplication.get().getAddress());
			application.setBiometricsDate(lastApplication.get().getBiometricsDate());
		}
		
		// Get identity and address from user infos if different of last application
		User currentUser = userService.getUser();
		application.setUserId(currentUser.getId()); // Utilisé temporairement pour simuler un numéro étranger de l'usager (et non pas pour faire un lien entre 2 documents)
		if(!lastApplication.isPresent() || ! lastApplication.get().getIdentity().equals(currentUser.getIdentity())) {
			application.setIdentity(currentUser.getIdentity());
		}
		if(!lastApplication.isPresent() || ! lastApplication.get().getAddress().equals(currentUser.getAddress())) {
			application.setAddress(currentUser.getAddress());
			application.getAddress().setEmail(currentUser.getEmail()); // on force le mail pour être le même que le login
		}
		
		application.setProject(new Project());
		
		if (nature == sejour_etudiant) {
			updateWithCampusInfos(application);
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
			identity.setResidencyCountry("KR");
			// ADDRESS
			Address address = application.getAddress();
			if(address.getPhone() == null) address.setPhone("+82 51 999999");
			if(address.getEmail() == null) address.setEmail(application.getEmail());
			address.setCountry("FR");
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
