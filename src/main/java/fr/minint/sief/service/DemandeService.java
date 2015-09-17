package fr.minint.sief.service;

import java.util.Calendar;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.minint.sief.domain.Demande;
import fr.minint.sief.domain.Identity;
import fr.minint.sief.domain.User;
import fr.minint.sief.domain.enumeration.SexType;
import fr.minint.sief.domain.enumeration.StatutDemande;
import fr.minint.sief.repository.DemandeRepository;
import fr.minint.sief.repository.UserRepository;
import fr.minint.sief.security.SecurityUtils;

/**
 * Service class for managing demande.
 */
@Service
public class DemandeService {

    private final Logger log = LoggerFactory.getLogger(DemandeService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private DemandeRepository demandeRepository;

    public void initWithCampus() {
    	log.debug("Recupération infos utilisateur");
        User currentUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin()).get();
        
        // call campus (badass style)
    	log.debug("Recupération infos campus");
    	Demande demande = getDemandeWithCampusInfos(currentUser.getEmail());
        
        // init demande
    	log.debug("Sauvegarde de la demande");
    	Demande existingDemande = demandeRepository.findOneByEmailAndStatut(demande.getEmail(), StatutDemande.init);
    	if(existingDemande != null){
    		demandeRepository.delete(existingDemande);
    	}
        demandeRepository.save(demande);
        
    }


	private Demande getDemandeWithCampusInfos(String email) {
		Demande demande = new Demande();
        demande.setEmail(email);
	
		if("zhang.li@gmail.com".equals(email)){
			Identity identity = new Identity();
			identity.setFirstName("Zhang");
			identity.setLastName("Li");
			identity.setSex(SexType.F);
			Calendar calendar = Calendar.getInstance();
			calendar.set(1992, 12, 10);
			identity.setBirthDate(new DateTime(calendar.getTimeInMillis()));
			identity.setBirthCity("Xi'an");
			identity.setBirthCountry("CN");
			identity.setNationality("CN");
			identity.setPassportNumber("1234567890");
			demande.setIdentity(identity );
		}
		return demande;
	}
}
