package fr.minint.sief.web.rest.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import fr.minint.sief.domain.enumeration.UserType;

public class UserDTO {

	public static final int PASSWORD_MIN_LENGTH = 5;
	public static final int PASSWORD_MAX_LENGTH = 100;
	
    private String id;

	@Email
	@NotNull
	@Size(min = 5, max = 100)
	private String email;

	@NotNull
	@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
	private String password;
	
	/** ENUM = COMPANY, PERSON */
	@NotNull
	private UserType type;
	
    private IdentityDTO identity = new IdentityDTO();
	
    private DateTime comingDate;
    
    private AddressDTO address = new AddressDTO();

	@Size(min = 2, max = 5)
	private String langKey;

	private List<String> roles;

	public UserDTO() {
	}

	public UserDTO(String id, String email, String password, UserType type, IdentityDTO identity, DateTime comingDate, 
			AddressDTO address, String langKey, List<String> roles) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.type = type;
		this.identity = identity;
		this.comingDate = comingDate;
		this.address = address;
		this.langKey = langKey;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public UserType getType() {
		return type;
	}

	public String getPassword() {
		return password;
	}

    public IdentityDTO getIdentity() {
		return identity;
	}

    public DateTime getComingDate() {
		return comingDate;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public String getLangKey() {
		return langKey;
	}

	public List<String> getRoles() {
		return roles;
	}

	@Override
	public String toString() {
		return "UserDTO{" + 
				"id='" + id + '\'' + 
				", email='" + email + '\'' + 
				", password='" + password + '\'' + 
				", type='" + type + '\'' + 
                ", identity='" + identity + '\'' +
                ", comingDate='" + comingDate + '\'' +
                ", address='" + address + '\'' +
				", langKey='" + langKey + '\'' + 
				", roles=" + roles + '}';
	}
}
