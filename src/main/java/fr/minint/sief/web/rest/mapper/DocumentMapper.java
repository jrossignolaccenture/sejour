package fr.minint.sief.web.rest.mapper;

import org.mapstruct.Mapper;

import fr.minint.sief.domain.Document;
import fr.minint.sief.web.rest.dto.DocumentDTO;

/**
 * Mapper for the entity Document and its DTO DocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentMapper {

    DocumentDTO documentToDocumentDTO(Document document);

    Document documentDTOToDocument(DocumentDTO documentDTO);
}
