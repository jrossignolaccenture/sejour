package fr.minint.sief.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.minint.sief.domain.enumeration.UserType;
import fr.minint.sief.domain.util.CustomDateTimeDeserializer;
import fr.minint.sief.domain.util.CustomDateTimeSerializer;

/**
 * A user.
 */
@Document(collection = "JHI_USER")
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @Email
    @NotNull
    @Size(min = 5, max = 100)
    private String email;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60) 
    private String password;
	
	/** ENUM = COMPANY, PERSON, UNIVERSITY */
	@NotNull
	private UserType type;
	
	@Field("profil")
	private String profil;
    
    @Field("identity")
    private Identity identity = new Identity();

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("coming_date")
    private DateTime comingDate;
    
    @Field("address")
    private Address address = new Address();

    private boolean activated = false;

    @Size(min = 2, max = 5)
    @Field("lang_key")
    private String langKey;

    @Size(max = 20)
    @Field("activation_key")
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Field("reset_key")
    private String resetKey;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("reset_date")
    private DateTime resetDate = null;

    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	public DateTime getComingDate() {
		return comingDate;
	}

	public void setComingDate(DateTime comingDate) {
		this.comingDate = comingDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public DateTime getResetDate() {
       return resetDate;
    }

    public void setResetDate(DateTime resetDate) {
       this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!email.equals(user.email)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", profil='" + profil + '\'' +
                ", identity='" + identity + '\'' +
                ", comingDate='" + comingDate + '\'' +
                ", address='" + address + '\'' +
                ", activated='" + activated + '\'' +
                ", langKey='" + langKey + '\'' +
                ", activationKey='" + activationKey + '\'' +
                "}";
    }
}
