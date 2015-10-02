package fr.minint.sief.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.minint.sief.domain.Demande;
import fr.minint.sief.domain.enumeration.StatutDemande;

/**
 * Spring Data MongoDB repository for the Demande entity.
 */
public interface DemandeRepository extends MongoRepository<Demande,String> {

	Demande findOneByEmailAndStatut(String email, StatutDemande statut);
	
	List<Demande> findByStatutOrderByCreationDateAsc(StatutDemande statut);
	
	List<Demande> findByEmailOrderByCreationDateDesc(String email);
	
	Long countByStatut(StatutDemande statut);
	
	Long countByEmailAndStatutNot(String email, StatutDemande statut);
}
