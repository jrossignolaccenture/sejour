package fr.minint.sief.service;

import java.util.Calendar;
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
import fr.minint.sief.repository.DemandeRepository;

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

    public void initWithCampus() {
    	// Récupération info utilisateur connecté
        User currentUser = userService.getUser();
        
        // call campus (badass style)
    	Demande demande = getDemandeWithCampusInfos(currentUser.getEmail());
    	demande.setNature(NatureDemande.sejour_etudiant);
        
        // suppression précédente demande en cours
    	Demande existingDemande = getCurrentDemande(StatutDemande.draft);
    	if(existingDemande != null){
    		demandeRepository.delete(existingDemande);
    	}

        // init nouvelle demande
        demandeRepository.save(demande);
        
    }


	private Demande getDemandeWithCampusInfos(String email) {
		Demande demande = new Demande();
        demande.setEmail(email);
	
		if("kim.soon.jeen@gmail.com".equals(email)){
			// IDENTITY
			Identity identity = new Identity();
			identity.setLastName("Kim");
			identity.setFirstName("Soon-jeen");
			identity.setSex(SexType.F);
			Calendar calendar = Calendar.getInstance();
			calendar.set(1992, 11, 10);
			identity.setBirthDate(new DateTime(calendar.getTimeInMillis()));
			identity.setBirthCity("Séoul");
			identity.setBirthCountry("KR");
			identity.setNationality("KR");
			identity.setPassportNumber("5577997");
			demande.setIdentity(identity );
			// ADDRESS
			Address address = new Address();
			address.setNumber("122-1");
			address.setStreet("Bujeon-ro");
			address.setComplement("Busanjin-gu");
			address.setPostalCode("614-861");
			address.setCity("Busan");
			address.setCountry("KR");
			address.setPhone("+82 51 999999");
			address.setEmail(email);
			demande.setAddress(address);
			// PROJECT
			Project project = new Project();
//			calendar.set(2016, 1, 5);
//			project.setComingDate(new DateTime(calendar.getTimeInMillis()));
			project.setUniversity("Télécom Paris Tech");
			project.setTraining("Master of Science in Multimedia Information Technologies");
			calendar.set(2016, 0, 5);
			project.setTrainingStart(new DateTime(calendar.getTimeInMillis()));
			project.setTrainingLength(12);
			demande.setProject(project);
		}
		return demande;
	}
}
