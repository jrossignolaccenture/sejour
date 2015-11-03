package fr.minint.sief.web.rest.dto;

import java.io.Serializable;

/**
 * A person.
 */
public class PersonDTO implements Serializable {
    
    private IdentityDTO identity = new IdentityDTO();
    
    private AddressDTO address = new AddressDTO();

	public IdentityDTO getIdentity() {
		return identity;
	}

	public void setIdentity(IdentityDTO identity) {
		this.identity = identity;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

    @Override
    public String toString() {
        return "User{" +
                ",identity='" + identity + '\'' +
                ", address='" + address + '\'' +
                "}";
    }
}
