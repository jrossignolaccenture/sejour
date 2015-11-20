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

import fr.minint.sief.domain.enumeration.ContactType;
import fr.minint.sief.domain.util.CustomDateTimeDeserializer;
import fr.minint.sief.domain.util.CustomDateTimeSerializer;

/**
 * A Address.
 */
public class Address implements Serializable {

    @Field("owner")
    private String owner;

    @NotNull        
    @Field("number")
    private String number;

    @NotNull        
    @Field("street")
    private String street;
    
    @Field("complement")
    private String complement;

    @NotNull        
    @Field("postal_code")
    private String postalCode;

    @NotNull        
    @Field("city")
    private String city;

    @NotNull        
    @Field("country")
    private String country;

    @NotNull        
    @Field("phone")
    private String phone;

    @NotNull        
    @Field("email")
    private String email;

    @NotNull        
    @Field("contact_type")
    private List<ContactType> contactType = new ArrayList<>();
    
    @Field("admissible")
    private Boolean admissible;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("validate_on")
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

        Address address = (Address) o;

        if ( !Objects.equals(owner, address.owner)
        		|| !Objects.equals(number, address.number)
        		|| !Objects.equals(street, address.street)
        		|| !Objects.equals(complement, address.complement)
        		|| !Objects.equals(postalCode, address.postalCode)
        		|| !Objects.equals(city, address.city)
        		|| !Objects.equals(country, address.country)
        		|| !Objects.equals(phone, address.phone)
        		|| !Objects.equals(email, address.email)
        		|| !Objects.equals(contactType, address.contactType)) return false;

        return true;
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
    	int result = 17;
    	result = prime * result + Objects.hashCode(owner);
    	result = prime * result + Objects.hashCode(number);
    	result = prime * result + Objects.hashCode(street);
    	result = prime * result + Objects.hashCode(complement);
    	result = prime * result + Objects.hashCode(postalCode);
    	result = prime * result + Objects.hashCode(city);
    	result = prime * result + Objects.hashCode(country);
    	result = prime * result + Objects.hashCode(phone);
    	result = prime * result + Objects.hashCode(email);
    	result = prime * result + Objects.hashCode(contactType);
    	return result;
    }

    @Override
    public String toString() {
        return "Address{" +
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
                ", admissible='" + admissible + '\'' +
                ", validateOn='" + validateOn + '\'' +
                '}';
    }
}
