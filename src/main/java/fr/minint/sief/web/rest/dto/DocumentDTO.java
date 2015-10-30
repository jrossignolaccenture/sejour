package fr.minint.sief.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import fr.minint.sief.domain.enumeration.DocumentType;

/**
 * A DTO for the Document entity.
 */
public class DocumentDTO implements Serializable {
    @NotNull
    private DocumentType type;
    
    @NotNull
    private String id;
    
    @NotNull
    private String name;

    public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentDTO document = (DocumentDTO) o;

        if ( ! Objects.equals(type, document.type)
        			|| ! Objects.equals(id, document.id)
        			|| ! Objects.equals(name, document.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
    	int result = 1;
    	result = prime * result + Objects.hashCode(type);
    	result = prime * result + Objects.hashCode(id);
    	result = prime * result + Objects.hashCode(name);
    	return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "type='" + type + "'" +
                ", id='" + id + "'" +
                ", name='" + name + "'" +
                '}';
    }
}
