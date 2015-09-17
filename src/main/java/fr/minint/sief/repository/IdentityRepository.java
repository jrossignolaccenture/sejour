package fr.minint.sief.repository;

import fr.minint.sief.domain.Identity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Identity entity.
 */
public interface IdentityRepository extends MongoRepository<Identity,String> {

}
