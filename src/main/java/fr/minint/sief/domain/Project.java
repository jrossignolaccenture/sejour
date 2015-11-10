package fr.minint.sief.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

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

    @NotNull        
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("coming_date")
    private DateTime comingDate;

    @NotNull        
    @Field("university")
    private String university;

    @NotNull        
    @Field("training")
    private String training;

    @NotNull        
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("training_start")
    private DateTime trainingStart;

    @NotNull        
    @Field("training_length")
    private Integer trainingLength;

    @NotNull        
    @Field("resource_type")
    private ResourceType resourceType;

    @NotNull        
    @Field("resource_amount")
    private Integer resourceAmount;
    
    @Field("documents")
    private List<Document> documents = new ArrayList<>();
    
    @Field("resources_search_authorized")
    private Boolean resourcesSearchAuthorized;
    
    @Field("tax_situation_search_authorized")
    private Boolean taxSituationSearchAuthorized;
    
    @Field("admissible")
    private boolean admissible;

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

	public boolean isAdmissible() {
		return admissible;
	}

	public void setAdmissible(boolean admissible) {
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

        return true;
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
    	int result = 1;
    	result = prime * result + Objects.hashCode(comingDate);
    	result = prime * result + Objects.hashCode(training);
    	result = prime * result + Objects.hashCode(trainingStart);
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
                ", resourcesSearchAuthorized='" + resourcesSearchAuthorized + "'" +
                ", taxSituationSearchAuthorized='" + taxSituationSearchAuthorized + "'" +
                ", admissible='" + admissible + '\'' +
                ", validateOn='" + validateOn + '\'' +
                '}';
    }
}
