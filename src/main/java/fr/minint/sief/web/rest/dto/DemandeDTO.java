package fr.minint.sief.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import fr.minint.sief.domain.enumeration.NatureDemande;
import fr.minint.sief.domain.enumeration.TypeDemande;
import fr.minint.sief.domain.enumeration.StatutDemande;

/**
 * A DTO for the Demande entity.
 */
public class DemandeDTO implements Serializable {

    private String id;

    @NotNull
    private NatureDemande nature;

    @NotNull
    private TypeDemande type;

    @NotNull
    private StatutDemande statut;

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
                ", nature='" + nature + "'" +
                ", type='" + type + "'" +
                ", statut='" + statut + "'" +
                ", demandeComplementaire='" + demandeComplementaire + "'" +
                '}';
    }
}
