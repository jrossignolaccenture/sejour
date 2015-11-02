package fr.minint.sief.service.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fr.minint.sief.domain.Application;
import fr.minint.sief.domain.Document;
import fr.minint.sief.domain.User;
import fr.minint.sief.domain.enumeration.DocumentType;
import fr.minint.sief.repository.ApplicationRepository;
import fr.minint.sief.repository.UserRepository;
import fr.minint.sief.security.SecurityUtils;

/**
 * Service class for managing files.
 */
@Service
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private ApplicationRepository applicationRepository;

    /**
     * Load a file on disk
     * 
     * @param sourceFile The file to load
     * @param destFilePrefix The prefix of the file name to create
     * @return an uuid
     */
    public Optional<Document> loadFile(MultipartFile sourceFile, DocumentType destFileType) {
    	
    	Document doc = null;
    	
        User currentUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin()).get();
        String uuid = Long.toString(UUID.randomUUID().getLeastSignificantBits(), Character.MAX_RADIX);
        String destFileName = destFileType + "_" + currentUser.getEmail() + uuid + "." + FilenameUtils.getExtension(sourceFile.getOriginalFilename());
        
        try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/webapp/assets/fileUpload", destFileName)))) {
            stream.write(sourceFile.getBytes());
            doc = new Document();
            doc.setId(uuid);
            doc.setType(destFileType);
            doc.setName(sourceFile.getOriginalFilename());
            doc.setFileName("assets/fileUpload/" + destFileName);
            log.debug( "You successfully uploaded " + destFileName + "!");
        } catch (Exception e) {
        	log.debug( "You failed to upload " + destFileName + " => " + e.getMessage());
        }
        
        return Optional.ofNullable(doc);
	}

    public void loadPhoto(String sourceUri, String destFilePrefix, String idApplication) {
    	
        Application application = applicationRepository.findOne(idApplication);
        String destFileName = destFilePrefix + "_" + application.getEmail() + ".jpeg";
        
        try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/webapp/assets/fileUpload", destFileName)))) {
            stream.write(Base64.getDecoder().decode(sourceUri));
            log.debug( "You successfully uploaded " + destFileName + "!");
        } catch (Exception e) {
        	log.debug( "You failed to upload " + destFileName + " => " + e.getMessage());
        }
	}
}
