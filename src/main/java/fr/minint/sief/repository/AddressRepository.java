package fr.minint.sief.repository;

import fr.minint.sief.domain.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Address entity.
 */
public interface AddressRepository extends MongoRepository<Address,String> {

}
