package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private String foreignerNumber;

    @NotNull
    private String lastName;

    private String usedLastName;

    private String firstName;

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
    private String residencyCountry;

    @NotNull        
    private MaritalStatus maritalStatus;

    private int childsNumber;

    private int brothersNumber;

    @NotNull        
    private ActivityType activity;

    @NotNull
    private List<DocumentDTO> documents = new ArrayList<>();
    
    private Map<PersonType, List<PersonDTO>> family = new HashMap<>();
    
    private boolean changed = false;

    private Boolean admissible;
    
    private boolean valid = false;

    private DateTime validateOn;
    
    private Boolean familyAdmissible;

    private DateTime familyValidateOn;

    public String getForeignerNumber() {
		return foreignerNumber;
	}

	public void setForeignerNumber(String foreignerNumber) {
		this.foreignerNumber = foreignerNumber;
	}

	public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getResidencyCountry() {
		return residencyCountry;
	}

	public void setResidencyCountry(String residencyCountry) {
		this.residencyCountry = residencyCountry;
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

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void setValidateOn(DateTime validateOn) {
		this.validateOn = validateOn;
	}

	public Boolean isFamilyAdmissible() {
		return familyAdmissible;
	}

	public void setFamilyAdmissible(Boolean familyAdmissible) {
		this.familyAdmissible = familyAdmissible;
	}

	public DateTime getFamilyValidateOn() {
		return familyValidateOn;
	}

	public void setFamilyValidateOn(DateTime familyValidateOn) {
		this.familyValidateOn = familyValidateOn;
	}

    @Override
    public String toString() {
        return "IdentityDTO{" +
                "foreignerNumber='" + foreignerNumber + "'" +
                ", lastName='" + lastName + "'" +
                ", usedLastName='" + usedLastName + "'" +
                ", firstName='" + firstName + "'" +
                ", sex='" + sex + "'" +
                ", birthDate='" + birthDate + "'" +
                ", birthCity='" + birthCity + "'" +
                ", birthCountry='" + birthCountry + "'" +
                ", nationality='" + nationality + "'" +
                ", passportNumber='" + passportNumber + "'" +
                ", residencyCountry='" + residencyCountry + "'" +
                ", maritalStatus='" + maritalStatus + "'" +
                ", childsNumber='" + childsNumber + "'" +
                ", brothersNumber='" + brothersNumber + "'" +
                ", activity='" + activity + "'" +
                ", documents='" + documents + "'" +
                ", family='" + family + '\'' +
                ", changed='" + changed + '\'' +
                ", admissible='" + admissible + '\'' +
                ", validateOn='" + validateOn + '\'' +
                ", valid='" + valid + '\'' +
                ", familyValidateOn='" + familyValidateOn + '\'' +
                ", familyAdmissible='" + familyAdmissible + '\'' +
                '}';
    }
}
