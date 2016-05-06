package fr.minint.sief.config;


import static java.util.Collections.singletonList;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@Configuration
@Profile("!" + Constants.SPRING_PROFILE_CLOUD)
@EnableMongoRepositories("fr.minint.sief.repository")
@Import(value = MongoAutoConfiguration.class)
@EnableMongoAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class DatabaseConfiguration extends AbstractMongoConfiguration  {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

//    @Inject
//    private Mongo mongo;
//
//    @Inject
//    private MongoProperties mongoProperties;
    
    @Value("${spring.data.mongodb.host}")
    private String host;
    
    @Value("${spring.data.mongodb.port}")
    private Integer port;
    
    @Value("${spring.data.mongodb.username}")
    private String username;
    
    @Value("${spring.data.mongodb.database}")
    private String database;
    
    @Value("${spring.data.mongodb.password}")
    private String password;

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(singletonList(new ServerAddress(host, port)),
                singletonList(MongoCredential.createCredential(username, database, password.toCharArray())));
    }

//    @Bean
//    @Profile("!" + Constants.SPRING_PROFILE_FAST)
//    public Mongeez mongeez() throws Exception {
//        log.debug("Configuring Mongeez");
//        Mongeez mongeez = new Mongeez();
//        mongeez.setFile(new ClassPathResource("/config/mongeez/master.xml"));
//        mongeez.setMongo(mongo());
//        mongeez.setDbName(database);
//        mongeez.process();
//        return mongeez;
//    }

    //TODO Temporaire car pressé...
    @Bean
    @Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
    public String initDB() throws Exception {
        log.debug("shalala");
        MongoDatabase db = ((MongoClient)mongo()).getDatabase("sejour");
        db.drop();
        db.createCollection("JHI_AUTHORITY");
		db.getCollection("JHI_AUTHORITY").insertOne(Document.parse("{_id : 'ROLE_ADMIN'}"));
		db.getCollection("JHI_AUTHORITY").insertOne(Document.parse("{_id : 'ROLE_USAGER'}"));
		db.getCollection("JHI_AUTHORITY").insertOne(Document.parse("{_id : 'ROLE_AGENT'}"));
		db.getCollection("JHI_AUTHORITY").insertOne(Document.parse("{_id : 'ROLE_USER'}"));
		
        db.createCollection("JHI_USER");
		db.getCollection("JHI_USER").createIndex(Document.parse("{'email' : 1}"));
		db.getCollection("JHI_USER").insertOne(Document.parse("{_id : 'user-blanc', _class : 'fr.minint.sief.domain.User', password : '$2a$10$h.KqlemFHFF0b.Xure4weumMVs/c0hFCQtj/05y8zGO7bZDUKHiAe',"
				+ "email : 'michele.blanc@sief.com', type : 'agent',"
				+ "identity : {last_name: 'Blanc', first_name: 'Michèle'},"
				+ "address : {},"
				+ "authorities : [{_id : 'ROLE_AGENT'}], activated : true, lang_key : 'fr', created_by : 'michele.blanc@sief.com', created_date : ISODate('2015-09-23T09:22:39.031Z'), last_modified_by : 'michele.blanc@sief.com', last_modified_date : ISODate('2015-11-12T09:42:10.211Z')}"));
		db.getCollection("JHI_USER").insertOne(Document.parse("{_id : 'user-bak', _class : 'fr.minint.sief.domain.User', password : '$2a$10$h.KqlemFHFF0b.Xure4weumMVs/c0hFCQtj/05y8zGO7bZDUKHiAe',"
				+ "email : 'catherine.bak@sief.com', type : 'agent',"
				+ "identity : {last_name: 'Bak', first_name: 'Catherine'},"
				+ "address : {},"
				+ "authorities : [{_id : 'ROLE_AGENT'}], activated : true, lang_key : 'fr', created_by : 'catherine.bak@sief.com', created_date : ISODate('2015-09-23T09:22:39.031Z'), last_modified_by : 'catherine.bak@sief.com', last_modified_date : ISODate('2015-11-12T09:42:10.211Z')}"));
		db.getCollection("JHI_USER").insertOne(Document.parse("{_id : 'user-martin', _class : 'fr.minint.sief.domain.User', password : '$2a$10$h.KqlemFHFF0b.Xure4weumMVs/c0hFCQtj/05y8zGO7bZDUKHiAe',"
				+ "email : 'didier.martin@sief.com', type : 'agent',"
				+ "identity : {last_name: 'Martin', first_name: 'Didier'},"
				+ "address : {},"
				+ "authorities : [{_id : 'ROLE_AGENT'}], activated : true, lang_key : 'fr', created_by : 'didier.martin@sief.com', created_date : ISODate('2015-09-23T09:22:39.031Z'), last_modified_by : 'didier.martin@sief.com', last_modified_date : ISODate('2015-11-12T09:42:10.211Z')}"));
		db.getCollection("JHI_USER").insertOne(Document.parse("{_id : 'user-petit', _class : 'fr.minint.sief.domain.User', password : '$2a$10$h.KqlemFHFF0b.Xure4weumMVs/c0hFCQtj/05y8zGO7bZDUKHiAe',"
				+ "email : 'louise.petit@sief.com',"
				+ "type : 'agent',"
				+ "identity : {last_name: 'Petit', first_name: 'Louise'},"
				+ "address : {},"
				+ "authorities : [{_id : 'ROLE_AGENT'}], activated : true, lang_key : 'fr', created_by : 'louise.petit@sief.com', created_date : ISODate('2015-09-23T09:22:39.031Z'), last_modified_by : 'louise.petit@sief.com', last_modified_date : ISODate('2015-11-12T09:42:10.211Z')}"));
		db.getCollection("JHI_USER").insertOne(Document.parse("{_id : 'user-dupuis', _class : 'fr.minint.sief.domain.User', password : '$2a$10$h.KqlemFHFF0b.Xure4weumMVs/c0hFCQtj/05y8zGO7bZDUKHiAe',"
				+ "email : 'kitty.dupuis@sief.com',"
				+ "type : 'agent',"
				+ "identity : {last_name: 'Dupuis', first_name: 'Kitty'},"
				+ "address : {},"
				+ "authorities : [{_id : 'ROLE_AGENT'}], activated : true, lang_key : 'fr', created_by : 'kitty.dupuis@sief.com', created_date : ISODate('2015-09-23T09:22:39.031Z'), last_modified_by : 'kitty.dupuis@sief.com', last_modified_date : ISODate('2015-11-12T09:42:10.211Z')}"));
//		db.getCollection("JHI_USER").insertOne(Document.parse("{_id : ObjectId('56026f5fd4c6d4a17b866d88'), _class : 'fr.minint.sief.domain.User', password : '$2a$10$zIrzNzyxPJFiMEQFUUGDcepj/qDuKUYGf0oMjz/W/UILGHxnu5sw6',"
//				+ "email : 'kim.soon.jeen@gmail.com', type : 'individual',"
//				+ "identity : {last_name: 'Kim', first_name: 'Soon-Jeen', documents: [], family: {}},"
//				+ "'address' : {}, "
//				+ "project: {},"
//				+ "authorities : [{_id : 'ROLE_USAGER'}], activated : true, lang_key : 'fr', created_by : 'kim.soon.jeen@gmail.com', created_date : ISODate('2015-09-23T09:22:39.031Z'), last_modified_by : 'kim.soon.jeen@gmail.com', last_modified_date : ISODate('2015-11-12T09:42:10.211Z')}"));
		db.getCollection("JHI_USER").insertOne(Document.parse("{_id : ObjectId('56026f5fd4c6d4a17b866d89'), _class : 'fr.minint.sief.domain.User', password : '$2a$10$zIrzNzyxPJFiMEQFUUGDcepj/qDuKUYGf0oMjz/W/UILGHxnu5sw6',"
				+ "email : 'kim.soon.jeen@gmail.com', type : 'individual',"
				+ "'identity' : { 'foreigner_number': '0123456789', 'last_name' : 'Kim', 'first_name' : 'Soon-Jeen', 'sex' : 'F', 'birth_date' : ISODate('1992-12-10T23:00:00Z'), 'birth_city' : 'Séoul', 'birth_country' : 'KR', 'nationality' : 'KR', 'passport_number' : '5577997', 'residency_country': 'FR', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'student', 'documents' : [ { 'type' : 'passport', 'id' : '-1p55gnq7olyyy', 'name' : 'Soon-jeen KIM_Passeport.png', 'file_name' : 'assets/fileUpload/passport_kim.soon.jeen@gmail.com-1p55gnq7olyyy.png', 'validation' : ISODate('20015-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-11vbnn40z1j44', 'name' : 'Soon-jeen KIM_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_kim.soon.jeen@gmail.com-11vbnn40z1j44.png', 'validation' : ISODate('2015-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2015-02-10T09:11:19.669Z'), 'family' : {  } }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'kim.soon.jeen@gmail.com', 'contact_type' : [ 'email' ], 'documents' : [], 'admissible' : true, 'validate_on' : ISODate('2015-03-10T10:00:19.669Z') }, "
				+ "project: {},"
				+ "authorities : [{_id : 'ROLE_USAGER'}], activated : true, lang_key : 'fr', created_by : 'kim.soon.jeen@gmail.com', created_date : ISODate('2015-09-23T09:22:39.031Z'), last_modified_by : 'kim.soon.jeen@gmail.com', last_modified_date : ISODate('2015-11-12T09:42:10.211Z')}"));
		db.getCollection("JHI_USER").insertOne(Document.parse("{_id : ObjectId('56533ccb151b140ad77274b2'), _class : 'fr.minint.sief.domain.User', password : '$2a$10$zIrzNzyxPJFiMEQFUUGDcepj/qDuKUYGf0oMjz/W/UILGHxnu5sw6',"
				+ "email : 'zayat.noura@gmail.com', type : 'individual',"
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'salaried', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'documents' : [], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "project: {},"
				+ "authorities : [{_id : 'ROLE_USAGER'}], activated : true, lang_key : 'fr', created_by : 'zayat.noura@gmail.com', created_date : ISODate('2055-01-02T09:22:39.031Z'), last_modified_by : 'zayat.noura@gmail.com', last_modified_date : ISODate('2015-11-12T09:42:10.211Z')}"));
		
		db.createCollection("APPLICATION");
		db.getCollection("APPLICATION").createIndex(Document.parse("{'email' : 1}"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034488'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_etudiant', 'type' : 'premiere', 'statut' : 'validated', 'creation_date' : ISODate('2005-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2005-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'residency_country' : 'LB', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'student', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '+961 51 999999', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'coming_date' : ISODate('2005-05-15T23:00:00Z'), 'university' : 'Télécom Paris Tech', 'training' : 'Master of Science in Multimedia Information Technologies', 'training_start' : ISODate('2005-05-31T23:00:00Z'), 'training_length' : 12, 'resource_type' : 'bourse', 'resource_amount' : 10000, 'documents' : [], 'admissible' : true }, "
				+ "'payment_date' : ISODate('2005-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2005-02-03T09:30:19.669Z'), 'rdv_date' : ISODate('2005-02-08T14:00:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2005-02-10T09:11:19.669Z') }"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034489'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_etudiant', 'type' : 'renouvellement', 'statut' : 'validated', 'creation_date' : ISODate('2006-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2006-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'student', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2006-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'university' : 'Télécom Paris Tech', 'training' : 'Master of Science in Multimedia Information Technologies', 'training_start' : ISODate('2006-05-31T23:00:00Z'), 'training_length' : 12, 'resource_type' : 'bourse', 'resource_amount' : 10000, 'documents' : [ ], 'admissible' : true }, "
				+ "'payment_date' : ISODate('2006-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2006-02-03T09:30:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2006-02-05T09:11:19.669Z') }"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034490'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_etudiant', 'type' : 'renouvellement', 'statut' : 'validated', 'creation_date' : ISODate('2007-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2007-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'student', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'university' : 'Télécom Paris Tech', 'training' : 'Master of Science in Multimedia Information Technologies', 'training_start' : ISODate('2007-05-31T23:00:00Z'), 'training_length' : 12, 'resource_type' : 'bourse', 'resource_amount' : 10000, 'documents' : [ ], 'admissible' : true }, "
				+ "'payment_date' : ISODate('2007-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2007-02-03T09:30:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2007-02-05T09:11:19.669Z') }"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034491'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_etudiant', 'type' : 'renouvellement', 'statut' : 'validated', 'creation_date' : ISODate('2008-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2008-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'student', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'university' : 'Télécom Paris Tech', 'training' : 'Master of Science in Multimedia Information Technologies', 'training_start' : ISODate('2008-05-31T23:00:00Z'), 'training_length' : 12, 'resource_type' : 'bourse', 'resource_amount' : 10000, 'documents' : [ ], 'admissible' : true }, "
				+ "'payment_date' : ISODate('2008-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2008-02-03T09:30:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2008-02-05T09:11:19.669Z') }"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034492'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_salarie', 'type' : 'renouvellement', 'statut' : 'validated', 'creation_date' : ISODate('2009-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2009-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'salaried', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'training_start' : ISODate('2009-05-31T23:00:00Z'), 'admissible' : true }, "
				+ "'payment_date' : ISODate('2009-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2009-02-03T09:30:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2009-02-05T09:11:19.669Z') }"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034493'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_salarie', 'type' : 'renouvellement', 'statut' : 'validated', 'creation_date' : ISODate('2010-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2010-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'salaried', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'training_start' : ISODate('2010-05-31T23:00:00Z'), 'admissible' : true }, "
				+ "'payment_date' : ISODate('2010-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2010-02-03T09:30:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2010-02-05T09:11:19.669Z') }"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034494'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_salarie', 'type' : 'renouvellement', 'statut' : 'validated', 'creation_date' : ISODate('2011-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2011-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'salaried', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'training_start' : ISODate('2011-05-31T23:00:00Z'), 'admissible' : true }, "
				+ "'payment_date' : ISODate('2011-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2011-02-03T09:30:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2011-02-05T09:11:19.669Z') }"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034495'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_salarie', 'type' : 'renouvellement', 'statut' : 'validated', 'creation_date' : ISODate('2012-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2012-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'salaried', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'training_start' : ISODate('2012-05-31T23:00:00Z'), 'admissible' : true }, "
				+ "'payment_date' : ISODate('2012-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2012-02-03T09:30:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2012-02-05T09:11:19.669Z') }"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034496'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_salarie', 'type' : 'renouvellement', 'statut' : 'validated', 'creation_date' : ISODate('2013-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2013-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'salaried', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'training_start' : ISODate('2013-05-31T23:00:00Z'), 'admissible' : true }, "
				+ "'payment_date' : ISODate('2013-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2013-02-03T09:30:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2013-02-05T09:11:19.669Z') }"));
		db.getCollection("APPLICATION").insertOne(Document.parse("{ '_id' : ObjectId('56534019151b140b21034497'), '_class' : 'fr.minint.sief.domain.Application', "
				+ "'email' : 'zayat.noura@gmail.com', 'userId' : '56533ccb151b140ad77274b2', "
				+ "'nature' : 'sejour_salarie', 'type' : 'renouvellement', 'statut' : 'validated', 'creation_date' : ISODate('2014-02-02T11:44:33.314Z'), 'modification_date' : ISODate('2014-02-02T11:49:38.197Z'), "
				+ "'identity' : { 'last_name' : 'Zayat', 'first_name' : 'Noura', 'sex' : 'F', 'birth_date' : ISODate('1984-12-09T23:00:00Z'), 'birth_city' : 'Beyrouth', 'birth_country' : 'LB', 'nationality' : 'LB', 'passport_number' : '0554477', 'marital_status' : 'single', 'childs_number' : 0, 'brothers_number' : 0, 'activity' : 'salaried', 'documents' : [ { 'type' : 'passport', 'id' : '-1lux0x4ynub15', 'name' : 'Noura ZAYAT_Passeport.png', 'file_name' : 'assets/fileUpload/passport_zayat.noura@gmail.com-1lux0x4ynub15.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') }, { 'type' : 'birthAct', 'id' : '-105u8gaot3u61', 'name' : 'Noura ZAYAT_Acte de naissance.png', 'file_name' : 'assets/fileUpload/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png', 'validation' : ISODate('2005-02-10T09:11:19.669Z') } ], 'family' : {  }, 'admissible' : true, 'validate_on' : ISODate('2005-02-10T09:11:19.669Z'), 'family_admissible' : true, 'family_validate_on' : ISODate('2005-02-10T09:11:19.669Z') }, "
				+ "'address' : { 'number' : '4', 'street' : 'rue campagne première', 'complement' : '', 'postal_code' : '75014', 'city' : 'Paris', 'country' : 'FR', 'phone' : '0601020304', 'email' : 'zayat.noura@gmail.com', 'contact_type' : [ 'email' ], 'admissible' : true, 'validate_on' : ISODate('2005-12-10T10:00:19.669Z') }, "
				+ "'project' : { 'training_start' : ISODate('2014-05-31T23:00:00Z'), 'admissible' : true }, "
				+ "'payment_date' : ISODate('2014-02-02T11:50:38.197Z'), 'admissibility_date' : ISODate('2014-02-03T09:30:19.669Z'), 'biometrics_date' : ISODate('2005-02-08T14:00:19.669Z'), 'decision_date' : ISODate('2014-02-05T09:11:19.669Z') }"));
        
		File targetDir = new File("src/main/webapp/assets/fileUpload");
		FileUtils.forceMkdir(targetDir);
		FileUtils.cleanDirectory(targetDir);
		FileUtils.copyFileToDirectory(new File(this.getClass().getResource("/data/passport_zayat.noura@gmail.com-1lux0x4ynub15.png").getFile()), targetDir);
		FileUtils.copyFileToDirectory(new File(this.getClass().getResource("/data/birthAct_zayat.noura@gmail.com-105u8gaot3u61.png").getFile()), targetDir);
		FileUtils.copyFileToDirectory(new File(this.getClass().getResource("/data/passport_kim.soon.jeen@gmail.com-1p55gnq7olyyy.png").getFile()), targetDir);
		FileUtils.copyFileToDirectory(new File(this.getClass().getResource("/data/birthAct_kim.soon.jeen@gmail.com-11vbnn40z1j44.png").getFile()), targetDir);
        
        return "initDB";
    }
}
