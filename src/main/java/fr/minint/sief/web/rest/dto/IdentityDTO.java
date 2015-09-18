package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import fr.minint.sief.domain.enumeration.SexType;

/**
 * A DTO for the Identity entity.
 */
public class IdentityDTO implements Serializable {

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
    private String passport;

    @NotNull
    private String birthAct;

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

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getBirthAct() {
        return birthAct;
    }

    public void setBirthAct(String birthAct) {
        this.birthAct = birthAct;
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
                "lastName='" + lastName + "'" +
                ", usedLastName='" + usedLastName + "'" +
                ", firstName='" + firstName + "'" +
                ", sex='" + sex + "'" +
                ", birthDate='" + birthDate + "'" +
                ", birthCity='" + birthCity + "'" +
                ", birthCountry='" + birthCountry + "'" +
                ", nationality='" + nationality + "'" +
                ", passportNumber='" + passportNumber + "'" +
                ", passport='" + passport + "'" +
                ", birthAct='" + birthAct + "'" +
                '}';
    }
}
