package fr.minint.sief.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import fr.minint.sief.domain.enumeration.NatureDemande;
import fr.minint.sief.domain.enumeration.TypeDemande;
import fr.minint.sief.domain.enumeration.StatutDemande;

/**
 * A Demande.
 */
@Document(collection = "DEMANDE")
public class Demande implements Serializable {

    @Id
    private String id;
    

    @NotNull        
    @Field("nature")
    private NatureDemande nature;

    @NotNull        
    @Field("type")
    private TypeDemande type;

    @NotNull        
    @Field("statut")
    private StatutDemande statut;
    
    @Field("demande_complementaire")
    private Boolean demandeComplementaire;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getDemandeComplementaire() {
        return demandeComplementaire;
    }

    public void setDemandeComplementaire(Boolean demandeComplementaire) {
        this.demandeComplementaire = demandeComplementaire;
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
                ", nature='" + nature + "'" +
                ", type='" + type + "'" +
                ", statut='" + statut + "'" +
                ", demandeComplementaire='" + demandeComplementaire + "'" +
                '}';
    }
}
