package fr.minint.sief.web.rest;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;

import fr.minint.sief.domain.enumeration.DocumentType;
import fr.minint.sief.service.util.FileService;
import fr.minint.sief.web.rest.dto.DocumentDTO;
import fr.minint.sief.web.rest.mapper.DocumentMapper;

/**
 * REST controller for managing Identity.
 */
@RestController
@RequestMapping("/fileUpload")
public class FileUploadResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);
    
    @Inject
    private FileService fileService;
    
    @Inject
    private DocumentMapper documentMapper;

    /**
     * POST  /document -> save file
     * 
     * @param file
     * @param type
     */
    @RequestMapping(value = "/document", 
    				method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Timed
    public ResponseEntity<DocumentDTO> uploadFile(@RequestParam MultipartFile file, @RequestParam DocumentType type)  {
    	if (file.isEmpty()) {
    		log.debug( "You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    	return fileService.loadFile(file, type)
    			.map(documentMapper::documentToDocumentDTO)
				.map(documentDTO -> new ResponseEntity<>(documentDTO, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /photo -> save uri file
     * 
     * @param uri
     * @param type
     * @param idApplication
     */
    @RequestMapping(value = "/biometrics", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public void uploadPhoto(@RequestParam String uri, @RequestParam String type, @RequestParam String idApplication)  {
    	if (uri.isEmpty()) {
    		log.debug( "You failed to upload " + uri + " because the file was empty.");
    		return;
    	}
    	
    	fileService.loadPhoto(uri, type, idApplication);
    }
}
