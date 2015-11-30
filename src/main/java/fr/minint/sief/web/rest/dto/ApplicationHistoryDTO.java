package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;

import org.joda.time.DateTime;

import fr.minint.sief.domain.enumeration.ApplicationNature;
import fr.minint.sief.domain.enumeration.ApplicationType;

/**
 * A DTO for the Application entity.
 */
public class ApplicationHistoryDTO implements Serializable {

    private String id;

    private ApplicationNature nature;

    private ApplicationType type;
    
    private DateTime startDate;
    
    private DateTime endDate;
    
    public ApplicationHistoryDTO(String id, ApplicationNature nature, ApplicationType type, DateTime startDate,
			DateTime endDate) {
		super();
		this.id = id;
		this.nature = nature;
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getId() {
        return id;
    }

    public ApplicationNature getNature() {
        return nature;
    }

    public ApplicationType getType() {
        return type;
    }

    public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationHistoryDTO applicationDTO = (ApplicationHistoryDTO) o;

        if ( ! Objects.equals(id, applicationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApplicationDTO{" +
                "id=" + id +
                ", nature='" + nature + "'" +
                ", type='" + type + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                '}';
    }
}
