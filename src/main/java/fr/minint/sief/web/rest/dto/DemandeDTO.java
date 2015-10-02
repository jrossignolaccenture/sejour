package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import fr.minint.sief.domain.enumeration.NatureDemande;
import fr.minint.sief.domain.enumeration.StatutDemande;
import fr.minint.sief.domain.enumeration.TypeDemande;

/**
 * A DTO for the Demande entity.
 */
public class DemandeDTO implements Serializable {

    private String id;

	@Email
	@NotNull
	@Size(min = 5, max = 100)
	private String email;
	
	private String userId;

	@NotNull
    private NatureDemande nature;

    @NotNull
    private TypeDemande type = TypeDemande.premiere;

    @NotNull
    private StatutDemande statut = StatutDemande.draft;
    
    @NotNull
    private DateTime creationDate = DateTime.now();
    
    private DateTime modificationDate = DateTime.now();
    
    private IdentityDTO identity;
    
    private AddressDTO address;
    
    private ProjectDTO project;
    
    private DateTime paymentDate;
    
    private DateTime recevabilityDate;
    
    private DateTime rdvDate;
    
    private DateTime documentsDate;
    
    private DateTime biometricsDate;
    
    private DateTime decisionDate;

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

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

    public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

    public DateTime getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(DateTime modificationDate) {
		this.modificationDate = modificationDate;
	}

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

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public DateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(DateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public DateTime getRecevabilityDate() {
		return recevabilityDate;
	}

	public void setRecevabilityDate(DateTime recevabilityDate) {
		this.recevabilityDate = recevabilityDate;
	}

	public DateTime getRdvDate() {
		return rdvDate;
	}

	public void setRdvDate(DateTime rdvDate) {
		this.rdvDate = rdvDate;
	}

	public DateTime getDocumentsDate() {
		return documentsDate;
	}

	public void setDocumentsDate(DateTime documentsDate) {
		this.documentsDate = documentsDate;
	}

	public DateTime getBiometricsDate() {
		return biometricsDate;
	}

	public void setBiometricsDate(DateTime biometricsDate) {
		this.biometricsDate = biometricsDate;
	}

	public DateTime getDecisionDate() {
		return decisionDate;
	}

	public void setDecisionDate(DateTime decisionDate) {
		this.decisionDate = decisionDate;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DemandeDTO demandeDTO = (DemandeDTO) o;

        if ( ! Objects.equals(id, demandeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DemandeDTO{" +
                "id=" + id +
                ", email='" + email + "'" +
                ", userId='" + userId + "'" +
                ", nature='" + nature + "'" +
                ", type='" + type + "'" +
                ", statut='" + statut + "'" +
                ", creationDate='" + creationDate + "'" +
                ", modificationDate='" + modificationDate + "'" +
                ", identity='" + identity + "'" +
                ", address='" + address + "'" +
                ", project='" + project + "'" +
                ", paymentDate='" + paymentDate + "'" +
                ", recevabilityDate='" + recevabilityDate + "'" +
                ", rdv='" + rdvDate + "'" +
                ", documentsDate='" + documentsDate + "'" +
                ", biometricsDate='" + biometricsDate + "'" +
                ", decisionDate='" + decisionDate + "'" +
                '}';
    }
}
