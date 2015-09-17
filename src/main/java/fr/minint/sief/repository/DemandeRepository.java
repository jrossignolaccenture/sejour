package fr.minint.sief.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.minint.sief.domain.Demande;
import fr.minint.sief.domain.enumeration.StatutDemande;

/**
 * Spring Data MongoDB repository for the Demande entity.
 */
public interface DemandeRepository extends MongoRepository<Demande,String> {

	Demande findOneByEmailAndStatut(String email, StatutDemande statut);
}
