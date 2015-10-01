package fr.minint.sief.web.rest;

import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;

import fr.minint.sief.service.util.FileService;

/**
 * REST controller for managing Identity.
 */
@RestController
@RequestMapping("/fileUpload")
public class FileUploadResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);
    
    @Inject
    private FileService fileService;

    /**
     * POST  /document -> save file
     * @throws IOException 
     * @throws IllegalStateException 
     */
    @RequestMapping(value = "/document", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public void uploadFile(@RequestParam MultipartFile file, @RequestParam String type)  {
    	if (file.isEmpty()) {
    		log.debug( "You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
    		return;
    	}
    	
    	fileService.loadFile(file, type);
    }

    /**
     * POST  /photo -> save uri file
     * @throws IOException 
     * @throws IllegalStateException 
     */
    @RequestMapping(value = "/biometrics", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public void uploadPhoto(@RequestParam String uri, @RequestParam String type, @RequestParam String idDemande)  {
    	if (uri.isEmpty()) {
    		log.debug( "You failed to upload " + uri + " because the file was empty.");
    		return;
    	}
    	
    	fileService.loadPhoto(uri, type, idDemande);
    }
}
