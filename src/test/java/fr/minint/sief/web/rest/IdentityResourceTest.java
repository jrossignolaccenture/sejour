package fr.minint.sief.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.minint.sief.Application;
import fr.minint.sief.domain.Identity;
import fr.minint.sief.domain.enumeration.SexType;
import fr.minint.sief.repository.IdentityRepository;
import fr.minint.sief.web.rest.dto.IdentityDTO;
import fr.minint.sief.web.rest.mapper.IdentityMapper;

/**
 * Test class for the IdentityResource REST controller.
 *
 * @see IdentityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class IdentityResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_LAST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_LAST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_USED_LAST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_USED_LAST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_FIRST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST_NAME = "UPDATED_TEXT";

    private static final SexType DEFAULT_SEX = SexType.M;
    private static final SexType UPDATED_SEX = SexType.F;

    private static final DateTime DEFAULT_BIRTH_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_BIRTH_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_BIRTH_DATE_STR = dateTimeFormatter.print(DEFAULT_BIRTH_DATE);
    private static final String DEFAULT_BIRTH_CITY = "SAMPLE_TEXT";
    private static final String UPDATED_BIRTH_CITY = "UPDATED_TEXT";
    private static final String DEFAULT_BIRTH_COUNTRY = "SAMPLE_TEXT";
    private static final String UPDATED_BIRTH_COUNTRY = "UPDATED_TEXT";
    private static final String DEFAULT_NATIONALITY = "SAMPLE_TEXT";
    private static final String UPDATED_NATIONALITY = "UPDATED_TEXT";
    private static final String DEFAULT_PASSPORT_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_PASSPORT_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_PASSPORT = "SAMPLE_TEXT";
    private static final String UPDATED_PASSPORT = "UPDATED_TEXT";
    private static final String DEFAULT_BIRTH_ACT = "SAMPLE_TEXT";
    private static final String UPDATED_BIRTH_ACT = "UPDATED_TEXT";

    private static final Boolean DEFAULT_TEST = false;

    @Inject
    private IdentityRepository identityRepository;

    @Inject
    private IdentityMapper identityMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restIdentityMockMvc;

    private Identity identity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IdentityResource identityResource = new IdentityResource();
        ReflectionTestUtils.setField(identityResource, "identityRepository", identityRepository);
        ReflectionTestUtils.setField(identityResource, "identityMapper", identityMapper);
        this.restIdentityMockMvc = MockMvcBuilders.standaloneSetup(identityResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        identityRepository.deleteAll();
        identity = new Identity();
        identity.setLastName(DEFAULT_LAST_NAME);
        identity.setUsedLastName(DEFAULT_USED_LAST_NAME);
        identity.setFirstName(DEFAULT_FIRST_NAME);
        identity.setSex(DEFAULT_SEX);
        identity.setBirthDate(DEFAULT_BIRTH_DATE);
        identity.setBirthCity(DEFAULT_BIRTH_CITY);
        identity.setBirthCountry(DEFAULT_BIRTH_COUNTRY);
        identity.setNationality(DEFAULT_NATIONALITY);
        identity.setPassportNumber(DEFAULT_PASSPORT_NUMBER);
        identity.setPassport(DEFAULT_PASSPORT);
        identity.setBirthAct(DEFAULT_BIRTH_ACT);
    }

    @Test
    public void createIdentity() throws Exception {
        int databaseSizeBeforeCreate = identityRepository.findAll().size();

        // Create the Identity
        IdentityDTO identityDTO = identityMapper.identityToIdentityDTO(identity);

        restIdentityMockMvc.perform(post("/api/identitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(identityDTO)))
                .andExpect(status().isCreated());

        // Validate the Identity in the database
        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeCreate + 1);
        Identity testIdentity = identitys.get(identitys.size() - 1);
        assertThat(testIdentity.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIdentity.getUsedLastName()).isEqualTo(DEFAULT_USED_LAST_NAME);
        assertThat(testIdentity.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIdentity.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testIdentity.getBirthDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testIdentity.getBirthCity()).isEqualTo(DEFAULT_BIRTH_CITY);
        assertThat(testIdentity.getBirthCountry()).isEqualTo(DEFAULT_BIRTH_COUNTRY);
        assertThat(testIdentity.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testIdentity.getPassportNumber()).isEqualTo(DEFAULT_PASSPORT_NUMBER);
        assertThat(testIdentity.getPassport()).isEqualTo(DEFAULT_PASSPORT);
        assertThat(testIdentity.getBirthAct()).isEqualTo(DEFAULT_BIRTH_ACT);
    }

    @Test
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityRepository.findAll().size();
        // set the field null
        identity.setLastName(null);

        // Create the Identity, which fails.
        IdentityDTO identityDTO = identityMapper.identityToIdentityDTO(identity);

        restIdentityMockMvc.perform(post("/api/identitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(identityDTO)))
                .andExpect(status().isBadRequest());

        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSexIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityRepository.findAll().size();
        // set the field null
        identity.setSex(null);

        // Create the Identity, which fails.
        IdentityDTO identityDTO = identityMapper.identityToIdentityDTO(identity);

        restIdentityMockMvc.perform(post("/api/identitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(identityDTO)))
                .andExpect(status().isBadRequest());

        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityRepository.findAll().size();
        // set the field null
        identity.setBirthDate(null);

        // Create the Identity, which fails.
        IdentityDTO identityDTO = identityMapper.identityToIdentityDTO(identity);

        restIdentityMockMvc.perform(post("/api/identitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(identityDTO)))
                .andExpect(status().isBadRequest());

        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBirthCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityRepository.findAll().size();
        // set the field null
        identity.setBirthCity(null);

        // Create the Identity, which fails.
        IdentityDTO identityDTO = identityMapper.identityToIdentityDTO(identity);

        restIdentityMockMvc.perform(post("/api/identitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(identityDTO)))
                .andExpect(status().isBadRequest());

        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBirthCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityRepository.findAll().size();
        // set the field null
        identity.setBirthCountry(null);

        // Create the Identity, which fails.
        IdentityDTO identityDTO = identityMapper.identityToIdentityDTO(identity);

        restIdentityMockMvc.perform(post("/api/identitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(identityDTO)))
                .andExpect(status().isBadRequest());

        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityRepository.findAll().size();
        // set the field null
        identity.setNationality(null);

        // Create the Identity, which fails.
        IdentityDTO identityDTO = identityMapper.identityToIdentityDTO(identity);

        restIdentityMockMvc.perform(post("/api/identitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(identityDTO)))
                .andExpect(status().isBadRequest());

        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPassportNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityRepository.findAll().size();
        // set the field null
        identity.setPassportNumber(null);

        // Create the Identity, which fails.
        IdentityDTO identityDTO = identityMapper.identityToIdentityDTO(identity);

        restIdentityMockMvc.perform(post("/api/identitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(identityDTO)))
                .andExpect(status().isBadRequest());

        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllIdentitys() throws Exception {
        // Initialize the database
        identityRepository.save(identity);

        // Get all the identitys
        restIdentityMockMvc.perform(get("/api/identitys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].usedLastName").value(hasItem(DEFAULT_USED_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE_STR)))
                .andExpect(jsonPath("$.[*].birthCity").value(hasItem(DEFAULT_BIRTH_CITY.toString())))
                .andExpect(jsonPath("$.[*].birthCountry").value(hasItem(DEFAULT_BIRTH_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].passportNumber").value(hasItem(DEFAULT_PASSPORT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].passport").value(hasItem(DEFAULT_PASSPORT.toString())))
                .andExpect(jsonPath("$.[*].birthAct").value(hasItem(DEFAULT_BIRTH_ACT.toString())))
                .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.booleanValue())));
    }

    @Test
    public void getIdentity() throws Exception {
        // Initialize the database
        identityRepository.save(identity);

        // Get the identity
        restIdentityMockMvc.perform(get("/api/identitys/{passportNumber}", identity.getPassportNumber()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.usedLastName").value(DEFAULT_USED_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE_STR))
            .andExpect(jsonPath("$.birthCity").value(DEFAULT_BIRTH_CITY.toString()))
            .andExpect(jsonPath("$.birthCountry").value(DEFAULT_BIRTH_COUNTRY.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.passportNumber").value(DEFAULT_PASSPORT_NUMBER.toString()))
            .andExpect(jsonPath("$.passport").value(DEFAULT_PASSPORT.toString()))
            .andExpect(jsonPath("$.birthAct").value(DEFAULT_BIRTH_ACT.toString()))
            .andExpect(jsonPath("$.test").value(DEFAULT_TEST.booleanValue()));
    }

    @Test
    public void getNonExistingIdentity() throws Exception {
        // Get the identity
        restIdentityMockMvc.perform(get("/api/identitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateIdentity() throws Exception {
        // Initialize the database
        identityRepository.save(identity);

		int databaseSizeBeforeUpdate = identityRepository.findAll().size();

        // Update the identity
        identity.setLastName(UPDATED_LAST_NAME);
        identity.setUsedLastName(UPDATED_USED_LAST_NAME);
        identity.setFirstName(UPDATED_FIRST_NAME);
        identity.setSex(UPDATED_SEX);
        identity.setBirthDate(UPDATED_BIRTH_DATE);
        identity.setBirthCity(UPDATED_BIRTH_CITY);
        identity.setBirthCountry(UPDATED_BIRTH_COUNTRY);
        identity.setNationality(UPDATED_NATIONALITY);
        identity.setPassportNumber(UPDATED_PASSPORT_NUMBER);
        identity.setPassport(UPDATED_PASSPORT);
        identity.setBirthAct(UPDATED_BIRTH_ACT);
        
        IdentityDTO identityDTO = identityMapper.identityToIdentityDTO(identity);

        restIdentityMockMvc.perform(put("/api/identitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(identityDTO)))
                .andExpect(status().isOk());

        // Validate the Identity in the database
        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeUpdate);
        Identity testIdentity = identitys.get(identitys.size() - 1);
        assertThat(testIdentity.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIdentity.getUsedLastName()).isEqualTo(UPDATED_USED_LAST_NAME);
        assertThat(testIdentity.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIdentity.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testIdentity.getBirthDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testIdentity.getBirthCity()).isEqualTo(UPDATED_BIRTH_CITY);
        assertThat(testIdentity.getBirthCountry()).isEqualTo(UPDATED_BIRTH_COUNTRY);
        assertThat(testIdentity.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIdentity.getPassportNumber()).isEqualTo(UPDATED_PASSPORT_NUMBER);
        assertThat(testIdentity.getPassport()).isEqualTo(UPDATED_PASSPORT);
        assertThat(testIdentity.getBirthAct()).isEqualTo(UPDATED_BIRTH_ACT);
    }

    @Test
    public void deleteIdentity() throws Exception {
        // Initialize the database
        identityRepository.save(identity);

		int databaseSizeBeforeDelete = identityRepository.findAll().size();

        // Get the identity
        restIdentityMockMvc.perform(delete("/api/identitys/{passportNumber}", identity.getPassportNumber())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Identity> identitys = identityRepository.findAll();
        assertThat(identitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
