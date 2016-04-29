package fr.minint.sief.web.rest.dto;

import java.io.Serializable;

/**
 * A DTO for the Application count.
 */
public class ApplicationCountDTO implements Serializable {
	
	private static final long serialVersionUID = -8061995948732515536L;
	
	private Long nbPaid = 0L;
	private Long nbScheduled = 0L;
    private Long nbIdentityVerified = 0L;
    private Long nbCivilStateToReconstruct = 0L;
    private Long nbPermitToIssue = 0L;

	public Long getNbPaid() {
		return nbPaid;
	}

	public void setNbPaid(Long nbPaid) {
		this.nbPaid = nbPaid;
	}

	public Long getNbScheduled() {
		return nbScheduled;
	}

	public void setNbScheduled(Long nbScheduled) {
		this.nbScheduled = nbScheduled;
	}

	public Long getNbIdentityVerified() {
		return nbIdentityVerified;
	}

	public void setNbIdentityVerified(Long nbIdentityVerified) {
		this.nbIdentityVerified = nbIdentityVerified;
	}

	public Long getNbCivilStateToReconstruct() {
		return nbCivilStateToReconstruct;
	}

	public void setNbCivilStateToReconstruct(Long nbCivilStateToReconstruct) {
		this.nbCivilStateToReconstruct = nbCivilStateToReconstruct;
	}

	public Long getNbPermitToIssue() {
		return nbPermitToIssue;
	}

	public void setNbPermitToIssue(Long nbPermitToIssue) {
		this.nbPermitToIssue = nbPermitToIssue;
	}

	@Override
    public String toString() {
        return "ApplicationCountDTO{" +
                "nbPaid='" + nbPaid + "'" +
                ", nbScheduled='" + nbScheduled + "'" +
                ", nbIdentityVerified='" + nbIdentityVerified + "'" +
                ", nbCivilStateToReconstruct='" + nbCivilStateToReconstruct + "'" +
                ", nbPermitToIssue='" + nbPermitToIssue + "'" +
                '}';
    }
}
