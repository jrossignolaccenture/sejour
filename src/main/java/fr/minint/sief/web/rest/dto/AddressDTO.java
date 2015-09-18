package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

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
    private ContactType contactType;

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

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;

        if ( ! Objects.equals(phone, addressDTO.phone)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(phone);
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
                '}';
    }
}
