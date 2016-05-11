package fr.minint.sief.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.minint.sief.domain.Application;
import fr.minint.sief.domain.enumeration.ApplicationNature;
import fr.minint.sief.domain.enumeration.ApplicationStatus;

/**
 * Spring Data MongoDB repository for the Application entity.
 */
public interface ApplicationRepository extends MongoRepository<Application,String> {
	
	List<Application> findByStatutInOrderByCreationDateAsc(List<ApplicationStatus> status);
	
	List<Application> findByStatutInAndEmailOrderByCreationDateAsc(List<ApplicationStatus> status, String email);
	
	List<Application> findByStatutAndNatureAndReconstructionDateIsNull(ApplicationStatus status, ApplicationNature nature);
	
	List<Application> findByStatutAndNatureAndDecisionDateIsNotNull(ApplicationStatus status, ApplicationNature nature);
	
	List<Application> findByStatutAndNatureAndReceiptDateIsNullOrderByDecisionDateDesc(ApplicationStatus status, ApplicationNature nature);
	
	List<Application> findByStatutAndNatureAndReceiptDateIsNotNullAndIssuingDateIsNull(ApplicationStatus status, ApplicationNature nature);
	
	Optional<Application> findFirstByStatutInAndEmailOrderByDecisionDateDesc(List<ApplicationStatus> status, String email);
	
	List<Application> findByEmailOrderByCreationDateDesc(String email);
	
	Long countByStatutIn(List<ApplicationStatus> status);
	
	Long countByStatutInAndNatureAndReconstructionDateIsNull(ApplicationStatus status, ApplicationNature nature);
	
	Long countByStatutInAndNatureAndReceiptDateIsNotNullAndIssuingDateIsNull(ApplicationStatus status, ApplicationNature nature);
	
	Long countByEmailAndStatutNot(String email, ApplicationStatus status);
}
