package fr.minint.sief.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.minint.sief.domain.enumeration.ActivityType;
import fr.minint.sief.domain.enumeration.MaritalStatus;
import fr.minint.sief.domain.enumeration.PersonType;
import fr.minint.sief.domain.enumeration.SexType;
import fr.minint.sief.domain.util.CustomDateTimeDeserializer;
import fr.minint.sief.domain.util.CustomDateTimeSerializer;

/**
 * A Identity.
 */
public class Identity implements Serializable {
	
	@Field("francisation")
	private Boolean francisation;

    @NotNull        
    @Field("last_name")
    private String lastName;

    @Field("last_name_francise")
    private String lastNameFrancise;
    
    @Field("used_last_name")
    private String usedLastName;
    
    @Field("first_name")
    private String firstName;
    
    @Field("first_name_francise")
    private String firstNameFrancise;

    @NotNull        
    @Field("sex")
    private SexType sex;

    @NotNull        
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("birth_date")
    private DateTime birthDate;

    @NotNull        
    @Field("birth_city")
    private String birthCity;

    @NotNull        
    @Field("birth_country")
    private String birthCountry;

    @NotNull        
    @Field("nationality")
    private String nationality;

    @NotNull        
    @Field("passport_number")
    private String passportNumber;

    @NotNull        
    @Field("marital_status")
    private MaritalStatus maritalStatus;

    @Field("childs_number")
    private int childsNumber;

    @Field("brothers_number")
    private int brothersNumber;

    @NotNull        
    @Field("activity")
    private ActivityType activity;
    
    @Field("documents")
    private List<Document> documents = new ArrayList<>();
    
    @Field("family")
    private Map<PersonType, List<Person>> family = new HashMap<>();

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

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public Map<PersonType, List<Person>> getFamily() {
		return family;
	}

	public void setFamily(Map<PersonType, List<Person>> family) {
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

        Identity identity = (Identity) o;

        if ( ! Objects.equals(passportNumber, identity.passportNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
    	return Objects.hashCode(passportNumber);
    }

    @Override
    public String toString() {
        return "Identity{" +
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
    
    public boolean hasDocumentToValidate() {
    	return documents.stream().anyMatch(doc -> doc.getValidation() == null) || hasFamilyDocumentToValidate();
    }
    
    private boolean hasFamilyDocumentToValidate() {
    	return family.keySet().stream()
    		.anyMatch(personType -> {
    			return family.get(personType).stream().anyMatch(person -> {
    				return person.getIdentity().getDocuments().stream().anyMatch(doc -> doc.getValidation() == null);
    			});
    		});
    }
    
    public void setDocumentsDate(final DateTime date) {
    	documents.stream()
    		.filter(doc -> doc.getValidation() == null)
    		.forEach(doc -> doc.setValidation(date));
    	
    	family.keySet().stream()
        	.forEach(personType -> family.get(personType).stream()
        				.forEach(person -> person.getIdentity().getDocuments().stream()
						            		.filter(doc -> doc.getValidation() == null)
						            		.forEach(doc -> doc.setValidation(date))
						)
        	);
    }
}
