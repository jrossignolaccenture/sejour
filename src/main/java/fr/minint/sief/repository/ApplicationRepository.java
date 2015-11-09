package fr.minint.sief.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.minint.sief.domain.Application;
import fr.minint.sief.domain.enumeration.ApplicationStatus;

/**
 * Spring Data MongoDB repository for the Application entity.
 */
public interface ApplicationRepository extends MongoRepository<Application,String> {
	
	List<Application> findByStatutInOrderByCreationDateAsc(List<ApplicationStatus> status);
	
	List<Application> findByStatutInAndEmailOrderByCreationDateAsc(List<ApplicationStatus> status, String email);
	
	List<Application> findByEmailOrderByCreationDateDesc(String email);
	
	Long countByStatutIn(List<ApplicationStatus> status);
	
	Long countByEmailAndStatutNot(String email, ApplicationStatus status);
}
