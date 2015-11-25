package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.joda.time.DateTime;

import fr.minint.sief.domain.enumeration.ResourceType;

/**
 * A DTO for the Project entity.
 */
public class ProjectDTO implements Serializable {

    private DateTime comingDate;

    private String university;

    private String training;

    private DateTime trainingStart;

    private Integer trainingLength;

    private ResourceType resourceType;

    private Integer resourceAmount;

    private List<DocumentDTO> documents = new ArrayList<>();
    
    private Boolean francisation;
    
    private String lastNameFrancise;
    
    private String firstNameFrancise;
    
    private Boolean resourcesSearchAuthorized;
    
    private Boolean taxSituationSearchAuthorized;
    
    private Boolean admissible;

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
