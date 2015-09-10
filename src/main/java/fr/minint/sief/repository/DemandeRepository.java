package fr.minint.sief.repository;

import fr.minint.sief.domain.Demande;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Demande entity.
 */
public interface DemandeRepository extends MongoRepository<Demande,String> {

}
