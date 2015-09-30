package fr.minint.sief.web.rest.dto;

import java.io.Serializable;

/**
 * A DTO for the Demande count.
 */
public class DemandeCountDTO implements Serializable {
	
	private static final long serialVersionUID = -8061995948732515536L;
	
	private Long nbConsult = 0L;
	private Long nbRecevability = 0L;
	private Long nbIdentification = 0L;
    private Long nbDecision = 0L;
    
    public Long getNbConsult() {
		return nbConsult;
	}

	public void setNbConsult(Long nbConsult) {
		this.nbConsult = nbConsult;
	}

	public Long getNbRecevability() {
		return nbRecevability;
	}

	public void setNbRecevability(Long nbRecevability) {
		this.nbRecevability = nbRecevability;
	}

	public Long getNbIdentification() {
		return nbIdentification;
	}

	public void setNbIdentification(Long nbIdentification) {
		this.nbIdentification = nbIdentification;
	}

	public Long getNbDecision() {
		return nbDecision;
	}

	public void setNbDecision(Long nbDecision) {
		this.nbDecision = nbDecision;
	}

	@Override
    public String toString() {
        return "DemandeCountDTO{" +
                "nbConsult=" + nbConsult +
                ", nbRecevability='" + nbRecevability + "'" +
                ", nbRecevability='" + nbRecevability + "'" +
                ", nbDecision='" + nbDecision + "'" +
                '}';
    }
}
