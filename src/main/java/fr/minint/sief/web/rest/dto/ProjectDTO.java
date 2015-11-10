package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import fr.minint.sief.domain.enumeration.ResourceType;

/**
 * A DTO for the Project entity.
 */
public class ProjectDTO implements Serializable {

    @NotNull
    private DateTime comingDate;

    @NotNull
    private String university;

    @NotNull
    private String training;

    @NotNull
    private DateTime trainingStart;

    @NotNull
    private Integer trainingLength;

    @NotNull
    private ResourceType resourceType;

    @NotNull
    private Integer resourceAmount;

    @NotNull
    private List<DocumentDTO> documents = new ArrayList<>();
    
    private Boolean resourcesSearchAuthorized;
    
    private Boolean taxSituationSearchAuthorized;
    
    private boolean admissible;

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

    public List<DocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDTO> documents) {
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

        ProjectDTO projectDTO = (ProjectDTO) o;

        if ( ! Objects.equals(comingDate, projectDTO.comingDate)) return false;
        if ( ! Objects.equals(training, projectDTO.training)) return false;
        if ( ! Objects.equals(trainingStart, projectDTO.trainingStart)) return false;

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
        return "ProjectDTO{" +
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
