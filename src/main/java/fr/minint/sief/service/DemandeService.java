package fr.minint.sief.service;

import static fr.minint.sief.domain.enumeration.NatureDemande.sejour_etudiant;
import static fr.minint.sief.domain.enumeration.TypeDemande.premiere;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.minint.sief.domain.Address;
import fr.minint.sief.domain.Demande;
import fr.minint.sief.domain.Identity;
import fr.minint.sief.domain.Project;
import fr.minint.sief.domain.User;
import fr.minint.sief.domain.enumeration.NatureDemande;
import fr.minint.sief.domain.enumeration.SexType;
import fr.minint.sief.domain.enumeration.StatutDemande;
import fr.minint.sief.domain.enumeration.TypeDemande;
import fr.minint.sief.repository.DemandeRepository;
import fr.minint.sief.security.SecurityUtils;

/**
 * Service class for managing demande.
 */
@Service
public class DemandeService {

	private final Logger log = LoggerFactory.getLogger(DemandeService.class);

	@Inject
	private UserService userService;

	@Inject
	private DemandeRepository demandeRepository;

	public List<Demande> getUserDemandes() {
		// Récupération info utilisateur connecté
		User currentUser = userService.getUser();
		// Récupération des demandes
		return demandeRepository.findByEmailOrderByCreationDateDesc(currentUser.getEmail());
	}

	public Demande getCurrentDemande(StatutDemande statut) {
		// Récupération info utilisateur connecté
		User currentUser = userService.getUser();
		// Récupération demande en cours
		return demandeRepository.findOneByEmailAndStatut(currentUser.getEmail(), statut);
	}

	public void initWithUserInfo() {
		// Récupération info utilisateur connecté
		User currentUser = userService.getUser();

		// init demande with user info (pour les clones on verra plus tard
		// hein...)
		Demande demande = new Demande();
		demande.setEmail(currentUser.getEmail());
		demande.setType(TypeDemande.renouvellement);
		demande.setNature(NatureDemande.sejour_etudiant);
		demande.setModificationDate(DateTime.now());
		demande.setUserId(currentUser.getId());
		demande.setIdentity(currentUser.getIdentity());
		demande.setAddress(currentUser.getFrenchAddress());
		Project project = new Project();
		project.setComingDate(currentUser.getComingDate());
		project.setUniversity("Télécom Paris Tech");
		project.setTraining("Master of Science in Multimedia Information Technologies");
		demande.setProject(project);

		// suppression précédente demande en cours
		Demande existingDemande = getCurrentDemande(StatutDemande.draft);
		if (existingDemande != null) {
			demandeRepository.delete(existingDemande);
		}

		// init nouvelle demande
		demandeRepository.save(demande);
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
	public String createApplication(TypeDemande type, NatureDemande nature) {
		// Delete old draft application
		Demande oldDraftApplication = getCurrentDemande(StatutDemande.draft);
		if (oldDraftApplication != null) {
			demandeRepository.delete(oldDraftApplication);
		}

		// Create new application
		Demande application = new Demande();
		application.setEmail(SecurityUtils.getCurrentLogin());
		application.setType(type);
		application.setNature(nature);
		application.setModificationDate(DateTime.now());
		// This is ugly for now (we don't need a user id reference in the application)
		application.setUserId(userService.getUser().getId());

		if (nature == sejour_etudiant) {
			updateWithCampusInfos(application);
		}

		application = demandeRepository.save(application);

		return application.getId();
	}

	public void updateWithCampusInfos(Demande demande) {
		// call campus (badass style)
		if ("kim.soon.jeen@gmail.com".equals(demande.getEmail())) {
			if (demande.getType() == premiere) {
				// IDENTITY
				Identity identity = new Identity();
				identity.setLastName("Kim");
				identity.setFirstName("Soon-jeen");
				identity.setSex(SexType.F);
				identity.setBirthDate(DateTime.parse("1992-12-10T00:00"));
				identity.setBirthCity("Séoul");
				identity.setBirthCountry("KR");
				identity.setNationality("KR");
				identity.setPassportNumber("5577997");
				demande.setIdentity(identity);
				// ADDRESS
				Address address = new Address();
				address.setNumber("122-1");
				address.setStreet("Bujeon-ro");
				address.setComplement("Busanjin-gu");
				address.setPostalCode("614-861");
				address.setCity("Busan");
				address.setCountry("KR");
				address.setPhone("+82 51 999999");
				address.setEmail(demande.getEmail());
				demande.setAddress(address);
			}
			// PROJECT
			Project project = new Project();
			project.setUniversity("Télécom Paris Tech");
			project.setTraining("Master of Science in Multimedia Information Technologies");
			project.setTrainingStart(DateTime.parse("2016-01-05T00:00"));
			project.setTrainingLength(12);
			demande.setProject(project);
		}
	}
}
