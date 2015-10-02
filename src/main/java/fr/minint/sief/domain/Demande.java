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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.minint.sief.domain.enumeration.NatureDemande;
import fr.minint.sief.domain.enumeration.StatutDemande;
import fr.minint.sief.domain.enumeration.TypeDemande;
import fr.minint.sief.domain.util.CustomDateTimeDeserializer;
import fr.minint.sief.domain.util.CustomDateTimeSerializer;

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
    
    private String userId;

    @NotNull
    @Field("nature")
    private NatureDemande nature;

    @NotNull
    @Field("type")
    private TypeDemande type = TypeDemande.premiere;

    @NotNull
    @Field("statut")
    private StatutDemande statut = StatutDemande.draft;
    
    @NotNull        
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("creation_date")
    private DateTime creationDate = DateTime.now();

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("modification_date")
    private DateTime modificationDate = DateTime.now();
    
    @Field("identity")
    private Identity identity;
    
    @Field("address")
    private Address address;
    
    @Field("project")
    private Project project;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("payment_date")
    private DateTime paymentDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("recevability_date")
    private DateTime recevabilityDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("rdv_date")
    private DateTime rdvDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("documents_date")
    private DateTime documentsDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("biometrics_date")
    private DateTime biometricsDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("decision_date")
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
                ", userId='" + userId + "'" +
                ", nature='" + nature + "'" +
                ", type='" + type + "'" +
                ", statut='" + statut + "'" +
                ", creationDate='" + creationDate + "'" +
                ", modificationDate='" + modificationDate + "'" +
                ", identity='" + identity + "'" +
                ", address='" + address + "'" +
                ", project='" + project + "'" +
                ", paymentDate='" + getPaymentDate() + "'" +
                ", recevabilityDate='" + recevabilityDate + "'" +
                ", rdv='" + rdvDate + "'" +
                ", documentsDate='" + documentsDate + "'" +
                ", biometricsDate='" + biometricsDate + "'" +
                ", decisionDate='" + getDecisionDate() + "'" +
                '}';
    }
}
