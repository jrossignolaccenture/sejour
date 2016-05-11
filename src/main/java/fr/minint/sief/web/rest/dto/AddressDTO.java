package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import fr.minint.sief.domain.enumeration.ContactType;

/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private String owner;

    @NotNull
    private String number;

    @NotNull
    private String street;

    private String complement;

    @NotNull
    private String postalCode;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private String phone;

    @NotNull
    private String email;

    @NotNull
    private List<ContactType> contactType = new ArrayList<>();

    @NotNull
    private List<DocumentDTO> documents = new ArrayList<>();
    
    private boolean changed = false;
    
    private Boolean admissible;
    
    private boolean valid;

    private DateTime validateOn;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ContactType> getContactType() {
        return contactType;
    }

    public void setContactType(List<ContactType> contactType) {
        this.contactType = contactType;
    }

    public List<DocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDTO> documents) {
		this.documents = documents;
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public DateTime getValidateOn() {
		return validateOn;
	}

	public void setValidateOn(DateTime validateOn) {
		this.validateOn = validateOn;
	}

    @Override
    public String toString() {
        return "AddressDTO{" +
                "owner='" + owner + "'" +
                ", number='" + number + "'" +
                ", street='" + street + "'" +
                ", complement='" + complement + "'" +
                ", postalCode='" + postalCode + "'" +
                ", city='" + city + "'" +
                ", country='" + country + "'" +
                ", phone='" + phone + "'" +
                ", email='" + email + "'" +
                ", contactType='" + contactType + "'" +
                ", documents='" + documents + "'" +
                ", changed='" + changed + '\'' +
                ", admissible='" + admissible + '\'' +
                ", valid='" + valid + '\'' +
                ", validateOn='" + validateOn + '\'' +
                '}';
    }
}
