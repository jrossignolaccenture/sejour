package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import fr.minint.sief.domain.enumeration.ActivityType;
import fr.minint.sief.domain.enumeration.MaritalStatus;
import fr.minint.sief.domain.enumeration.PersonType;
import fr.minint.sief.domain.enumeration.SexType;

/**
 * A DTO for the Identity entity.
 */
public class IdentityDTO implements Serializable {
	
	private Boolean francisation;

    @NotNull
    private String lastName;
    
    private String lastNameFrancise;

    private String usedLastName;

    private String firstName;

    private String firstNameFrancise;

    @NotNull
    private SexType sex;

    @NotNull
    private DateTime birthDate;

    @NotNull
    private String birthCity;

    @NotNull
    private String birthCountry;

    @NotNull
    private String nationality;

    @NotNull
    private String passportNumber;

    @NotNull        
    private MaritalStatus maritalStatus;

    private int childsNumber;

    private int brothersNumber;

    @NotNull        
    private ActivityType activity;

    @NotNull
    private List<DocumentDTO> documents = new ArrayList<>();
    
    private Map<PersonType, List<PersonDTO>> family = new HashMap<>();

    public Boolean isFrancisation() {
		return francisation;
	}

	public void setFrancisation(Boolean francisation) {
		this.francisation = francisation;
	}

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastNameFrancise() {
		return lastNameFrancise;
	}

	public void setLastNameFrancise(String lastNameFrancise) {
		this.lastNameFrancise = lastNameFrancise;
	}

    public String getUsedLastName() {
        return usedLastName;
    }

    public void setUsedLastName(String usedLastName) {
        this.usedLastName = usedLastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstNameFrancise() {
		return firstNameFrancise;
	}

	public void setFirstNameFrancise(String firstNameFrancise) {
		this.firstNameFrancise = firstNameFrancise;
	}

    public SexType getSex() {
        return sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public int getChildsNumber() {
		return childsNumber;
	}

	public void setChildsNumber(int childsNumber) {
		this.childsNumber = childsNumber;
	}

	public int getBrothersNumber() {
		return brothersNumber;
	}

	public void setBrothersNumber(int brothersNumber) {
		this.brothersNumber = brothersNumber;
	}

	public ActivityType getActivity() {
		return activity;
	}

	public void setActivity(ActivityType activity) {
		this.activity = activity;
	}

    public List<DocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDTO> documents) {
		this.documents = documents;
	}

	public Map<PersonType, List<PersonDTO>> getFamily() {
		return family;
	}

	public void setFamily(Map<PersonType, List<PersonDTO>> family) {
		this.family = family;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IdentityDTO identityDTO = (IdentityDTO) o;

        if ( ! Objects.equals(passportNumber, identityDTO.passportNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(passportNumber);
    }

    @Override
    public String toString() {
        return "IdentityDTO{" +
                "francisation='" + francisation + "'" +
                ", lastName='" + lastName + "'" +
                ", lastNameFrancise='" + lastNameFrancise + "'" +
                ", usedLastName='" + usedLastName + "'" +
                ", firstName='" + firstName + "'" +
                ", firstNameFrancise='" + firstNameFrancise + "'" +
                ", sex='" + sex + "'" +
                ", birthDate='" + birthDate + "'" +
                ", birthCity='" + birthCity + "'" +
                ", birthCountry='" + birthCountry + "'" +
                ", nationality='" + nationality + "'" +
                ", passportNumber='" + passportNumber + "'" +
                ", maritalStatus='" + maritalStatus + "'" +
                ", childsNumber='" + childsNumber + "'" +
                ", brothersNumber='" + brothersNumber + "'" +
                ", activity='" + activity + "'" +
                ", documents='" + documents + "'" +
                ", family='" + family + '\'' +
                '}';
    }
}
