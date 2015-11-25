package fr.minint.sief.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.minint.sief.domain.enumeration.ResourceType;
import fr.minint.sief.domain.util.CustomDateTimeDeserializer;
import fr.minint.sief.domain.util.CustomDateTimeSerializer;

/**
 * A Project.
 */
public class Project implements Serializable {

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("coming_date")
    private DateTime comingDate;

    @Field("university")
    private String university;

    @Field("training")
    private String training;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("training_start")
    private DateTime trainingStart;

    @Field("training_length")
    private Integer trainingLength;

    @Field("resource_type")
    private ResourceType resourceType;

    @Field("resource_amount")
    private Integer resourceAmount;
    
    @Field("documents")
    private List<Document> documents = new ArrayList<>();
	
	@Field("francisation")
	private Boolean francisation;

    @Field("last_name_francise")
    private String lastNameFrancise;
    
    @Field("first_name_francise")
    private String firstNameFrancise;
    
    @Field("resources_search_authorized")
    private Boolean resourcesSearchAuthorized;
    
    @Field("tax_situation_search_authorized")
    private Boolean taxSituationSearchAuthorized;
    
    @Field("admissible")
    private Boolean admissible;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("validate_on")
    private DateTime validateOn;

    public DateTime getComingDate() {
        return comingDate;
    }

    public void setComingDate(DateTime comingDate) {
        this.comingDate = comingDate;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public DateTime getTrainingStart() {
        return trainingStart;
    }

    public void setTrainingStart(DateTime trainingStart) {
        this.trainingStart = trainingStart;
    }

    public Integer getTrainingLength() {
        return trainingLength;
    }

    public void setTrainingLength(Integer trainingLength) {
        this.trainingLength = trainingLength;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getResourceAmount() {
        return resourceAmount;
    }

    public void setResourceAmount(Integer resourceAmount) {
        this.resourceAmount = resourceAmount;
    }

    public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

    public Boolean isFrancisation() {
		return francisation;
	}

	public void setFrancisation(Boolean francisation) {
		this.francisation = francisation;
	}

    public String getLastNameFrancise() {
		return lastNameFrancise;
	}

	public void setLastNameFrancise(String lastNameFrancise) {
		this.lastNameFrancise = lastNameFrancise;
	}

    public String getFirstNameFrancise() {
		return firstNameFrancise;
	}

	public void setFirstNameFrancise(String firstNameFrancise) {
		this.firstNameFrancise = firstNameFrancise;
	}

    public Boolean isResourcesSearchAuthorized() {
		return resourcesSearchAuthorized;
	}

	public void setResourcesSearchAuthorized(Boolean resourcesSearchAuthorized) {
		this.resourcesSearchAuthorized = resourcesSearchAuthorized;
	}

	public Boolean isTaxSituationSearchAuthorized() {
		return taxSituationSearchAuthorized;
	}

	public void setTaxSituationSearchAuthorized(Boolean taxSituationSearchAuthorized) {
		this.taxSituationSearchAuthorized = taxSituationSearchAuthorized;
	}

	public Boolean isAdmissible() {
		return admissible;
	}

	public void setAdmissible(Boolean admissible) {
		this.admissible = admissible;
	}

	public DateTime getValidateOn() {
		return validateOn;
	}

	public void setValidateOn(DateTime validateOn) {
		this.validateOn = validateOn;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Project project = (Project) o;

        if ( ! Objects.equals(comingDate, project.comingDate)) return false;
        if ( ! Objects.equals(training, project.training)) return false;
        if ( ! Objects.equals(trainingStart, project.trainingStart)) return false;
        if ( ! Objects.equals(lastNameFrancise, project.lastNameFrancise)) return false;
        if ( ! Objects.equals(firstNameFrancise, project.firstNameFrancise)) return false;

        return true;
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
    	int result = 1;
    	result = prime * result + Objects.hashCode(comingDate);
    	result = prime * result + Objects.hashCode(training);
    	result = prime * result + Objects.hashCode(trainingStart);
    	result = prime * result + Objects.hashCode(lastNameFrancise);
    	result = prime * result + Objects.hashCode(firstNameFrancise);
    	return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "comingDate='" + comingDate + "'" +
                ", university='" + university + "'" +
                ", training='" + training + "'" +
                ", trainingStart='" + trainingStart + "'" +
                ", trainingLength='" + trainingLength + "'" +
                ", resourceType='" + resourceType + "'" +
                ", resourceAmount='" + resourceAmount + "'" +
                ", documents='" + documents + "'" +
                ", francisation='" + francisation + "'" +
                ", lastNameFrancise='" + lastNameFrancise + "'" +
                ", firstNameFrancise='" + firstNameFrancise + "'" +
                ", resourcesSearchAuthorized='" + resourcesSearchAuthorized + "'" +
                ", taxSituationSearchAuthorized='" + taxSituationSearchAuthorized + "'" +
                ", admissible='" + admissible + '\'' +
                ", validateOn='" + validateOn + '\'' +
                '}';
    }
}
