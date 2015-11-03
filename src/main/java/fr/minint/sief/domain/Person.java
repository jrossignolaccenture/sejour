package fr.minint.sief.domain;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A person.
 */
public class Person implements Serializable {
    
    @Field("identity")
    private Identity identity = new Identity();
    
    @Field("address")
    private Address address = new Address();

    public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person user = (Person) o;

        if (!identity.equals(user.identity)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return identity.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "identity='" + identity + '\'' +
                ", address='" + address + '\'' +
                "}";
    }
}
