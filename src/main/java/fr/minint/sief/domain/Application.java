package fr.minint.sief.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.minint.sief.domain.enumeration.ApplicationNature;
import fr.minint.sief.domain.enumeration.ApplicationStatus;
import fr.minint.sief.domain.enumeration.ApplicationType;
import fr.minint.sief.domain.util.CustomDateTimeDeserializer;
import fr.minint.sief.domain.util.CustomDateTimeSerializer;

/**
 * An application.
 */
@Document(collection = "APPLICATION")
public class Application implements Serializable {

    @Id
    private String id;

    @Email
    @NotNull
    @Size(min = 5, max = 100)
    private String email;
    
    private String userId;

    @NotNull
    @Field("nature")
    private ApplicationNature nature;

    @NotNull
    @Field("type")
    private ApplicationType type;

    @NotNull
    @Field("statut")
    private ApplicationStatus statut = ApplicationStatus.draft;
    
    @NotNull        
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("creation_date")
    private DateTime creationDate = DateTime.now();

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("modification_date")
    private DateTime modificationDate = DateTime.now();
    
    @Field("identity")
    private Identity identity = new Identity();
    
    @Field("address")
    private Address address = new Address();
    
    @Field("project")
    private Project project = new Project();

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("payment_date")
    private DateTime paymentDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("admissibility_date")
    private DateTime admissibilityDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("rdv_date")
    private DateTime rdvDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("interview_date")
    private DateTime interviewDate;
    
    @Field("interview_report")
    private String interviewReport;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("biometrics_date")
    private DateTime biometricsDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("issuing_date")
    private DateTime issuingDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("decision_date")
    private DateTime decisionDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("reconstruction_date")
    private DateTime reconstructionDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ApplicationNature getNature() {
        return nature;
    }

    public void setNature(ApplicationNature nature) {
        this.nature = nature;
    }

    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public ApplicationStatus getStatut() {
        return statut;
    }

    public void setStatut(ApplicationStatus statut) {
        this.statut = statut;
    }

    public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public DateTime getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(DateTime modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public DateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(DateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public DateTime getAdmissibilityDate() {
		return admissibilityDate;
	}

	public void setAdmissibilityDate(DateTime admissibilityDate) {
		this.admissibilityDate = admissibilityDate;
	}

	public DateTime getRdvDate() {
		return rdvDate;
	}

	public void setRdvDate(DateTime rdvDate) {
		this.rdvDate = rdvDate;
	}

	public DateTime getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(DateTime interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewReport() {
		return interviewReport;
	}

	public void setInterviewReport(String interviewReport) {
		this.interviewReport = interviewReport;
	}

	public DateTime getBiometricsDate() {
		return biometricsDate;
	}

	public void setBiometricsDate(DateTime biometricsDate) {
		this.biometricsDate = biometricsDate;
	}

	public DateTime getDecisionDate() {
		return decisionDate;
	}

	public void setDecisionDate(DateTime decisionDate) {
		this.decisionDate = decisionDate;
	}

	public DateTime getIssuingDate() {
		return issuingDate;
	}

	public void setIssuingDate(DateTime issuingDate) {
		this.issuingDate = issuingDate;
	}

	public DateTime getReconstructionDate() {
		return reconstructionDate;
	}

	public void setReconstructionDate(DateTime reconstructionDate) {
		this.reconstructionDate = reconstructionDate;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Application application = (Application) o;

        if ( ! Objects.equals(id, application.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", email='" + email + "'" +
                ", userId='" + userId + "'" +
                ", nature='" + nature + "'" +
                ", type='" + type + "'" +
                ", statut='" + statut + "'" +
                ", creationDate='" + creationDate + "'" +
                ", modificationDate='" + modificationDate + "'" +
                ", identity='" + identity + "'" +
                ", address='" + address + "'" +
                ", project='" + project + "'" +
                ", paymentDate='" + paymentDate + "'" +
                ", admissibilityDate='" + admissibilityDate + "'" +
                ", rdv='" + rdvDate + "'" +
                ", interviewDate='" + interviewDate + "'" +
                ", interviewReport='" + interviewReport + "'" +
                ", biometricsDate='" + biometricsDate + "'" +
                ", decisionDate='" + decisionDate + "'" +
                ", issuingDate='" + issuingDate + "'" +
                ", reconstructionDate='" + reconstructionDate + "'" +
                '}';
    }
    
    public void setAdmissibility(DateTime date) {
    	admissibilityDate = date;
    	identity.setAdmissible(true);
    	identity.setFamilyAdmissible(true);
    	address.setAdmissible(true);
    	if(address.getValidateOn() == null) {
    		address.setValidateOn(date);
    	}
    	project.setAdmissible(true);
    }
}
