package fr.minint.sief.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.minint.sief.domain.enumeration.NatureDemande;
import fr.minint.sief.domain.enumeration.StatutDemande;
import fr.minint.sief.domain.enumeration.TypeDemande;

/**
 * A Demande.
 */
@Document(collection = "DEMANDE")
public class Demande implements Serializable {

    @Id
    private String id;

    @Email
    @NotNull
    @Size(min = 5, max = 100)
    private String email;

    @NotNull
    @Field("nature")
    private NatureDemande nature;

    @NotNull
    @Field("type")
    private TypeDemande type = TypeDemande.premiere;

    @NotNull
    @Field("statut")
    private StatutDemande statut = StatutDemande.draft;
    
    @Field("identity")
    private Identity identity;
    
    @Field("address")
    private Address address;
    
    @Field("project")
    private Project project;
    
    @Field("rdv")
    private DateTime rdv;

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

    public NatureDemande getNature() {
        return nature;
    }

    public void setNature(NatureDemande nature) {
        this.nature = nature;
    }

    public TypeDemande getType() {
        return type;
    }

    public void setType(TypeDemande type) {
        this.type = type;
    }

    public StatutDemande getStatut() {
        return statut;
    }

    public void setStatut(StatutDemande statut) {
        this.statut = statut;
    }

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public DateTime getRdv() {
		return rdv;
	}

	public void setRdv(DateTime rdv) {
		this.rdv = rdv;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Demande demande = (Demande) o;

        if ( ! Objects.equals(id, demande.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", email='" + email + "'" +
                ", nature='" + nature + "'" +
                ", type='" + type + "'" +
                ", statut='" + statut + "'" +
                ", identity='" + identity + "'" +
                ", address='" + address + "'" +
                ", project='" + project + "'" +
                ", rdv='" + rdv + "'" +
                '}';
    }
}
