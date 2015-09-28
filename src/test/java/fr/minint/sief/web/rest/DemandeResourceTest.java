package fr.minint.sief.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
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
import fr.minint.sief.domain.Demande;
import fr.minint.sief.domain.enumeration.NatureDemande;
import fr.minint.sief.domain.enumeration.StatutDemande;
import fr.minint.sief.domain.enumeration.TypeDemande;
import fr.minint.sief.repository.DemandeRepository;
import fr.minint.sief.web.rest.dto.DemandeDTO;
import fr.minint.sief.web.rest.mapper.DemandeMapper;

/**
 * Test class for the DemandeResource REST controller.
 *
 * @see DemandeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DemandeResourceTest {


    private static final NatureDemande DEFAULT_NATURE = NatureDemande.sejour_etudiant;
    private static final NatureDemande UPDATED_NATURE = NatureDemande.sejour_salarie;

    private static final TypeDemande DEFAULT_TYPE = TypeDemande.premiere;
    private static final TypeDemande UPDATED_TYPE = TypeDemande.reexamen;

    private static final StatutDemande DEFAULT_STATUT = StatutDemande.payment;
    private static final StatutDemande UPDATED_STATUT = StatutDemande.recevability;

    private static final Boolean DEFAULT_DEMANDE_COMPLEMENTAIRE = false;
    private static final Boolean UPDATED_DEMANDE_COMPLEMENTAIRE = true;

    @Inject
    private DemandeRepository demandeRepository;

    @Inject
    private DemandeMapper demandeMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restDemandeMockMvc;

    private Demande demande;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DemandeResource demandeResource = new DemandeResource();
        ReflectionTestUtils.setField(demandeResource, "demandeRepository", demandeRepository);
        ReflectionTestUtils.setField(demandeResource, "demandeMapper", demandeMapper);
        this.restDemandeMockMvc = MockMvcBuilders.standaloneSetup(demandeResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        demandeRepository.deleteAll();
        demande = new Demande();
        demande.setNature(DEFAULT_NATURE);
        demande.setType(DEFAULT_TYPE);
        demande.setStatut(DEFAULT_STATUT);
    }

    @Test
    public void createDemande() throws Exception {
        int databaseSizeBeforeCreate = demandeRepository.findAll().size();

        // Create the Demande
        DemandeDTO demandeDTO = demandeMapper.demandeToDemandeDTO(demande);

        restDemandeMockMvc.perform(post("/api/demandes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(demandeDTO)))
                .andExpect(status().isCreated());

        // Validate the Demande in the database
        List<Demande> demandes = demandeRepository.findAll();
        assertThat(demandes).hasSize(databaseSizeBeforeCreate + 1);
        Demande testDemande = demandes.get(demandes.size() - 1);
        assertThat(testDemande.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testDemande.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDemande.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    public void checkNatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeRepository.findAll().size();
        // set the field null
        demande.setNature(null);

        // Create the Demande, which fails.
        DemandeDTO demandeDTO = demandeMapper.demandeToDemandeDTO(demande);

        restDemandeMockMvc.perform(post("/api/demandes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(demandeDTO)))
                .andExpect(status().isBadRequest());

        List<Demande> demandes = demandeRepository.findAll();
        assertThat(demandes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeRepository.findAll().size();
        // set the field null
        demande.setType(null);

        // Create the Demande, which fails.
        DemandeDTO demandeDTO = demandeMapper.demandeToDemandeDTO(demande);

        restDemandeMockMvc.perform(post("/api/demandes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(demandeDTO)))
                .andExpect(status().isBadRequest());

        List<Demande> demandes = demandeRepository.findAll();
        assertThat(demandes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeRepository.findAll().size();
        // set the field null
        demande.setStatut(null);

        // Create the Demande, which fails.
        DemandeDTO demandeDTO = demandeMapper.demandeToDemandeDTO(demande);

        restDemandeMockMvc.perform(post("/api/demandes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(demandeDTO)))
                .andExpect(status().isBadRequest());

        List<Demande> demandes = demandeRepository.findAll();
        assertThat(demandes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDemandes() throws Exception {
        // Initialize the database
        demandeRepository.save(demande);

        // Get all the demandes
        restDemandeMockMvc.perform(get("/api/demandes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(demande.getId())))
                .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
                .andExpect(jsonPath("$.[*].demandeComplementaire").value(hasItem(DEFAULT_DEMANDE_COMPLEMENTAIRE.booleanValue())));
    }

    @Test
    public void getDemande() throws Exception {
        // Initialize the database
        demandeRepository.save(demande);

        // Get the demande
        restDemandeMockMvc.perform(get("/api/demandes/{id}", demande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(demande.getId()))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.demandeComplementaire").value(DEFAULT_DEMANDE_COMPLEMENTAIRE.booleanValue()));
    }

    @Test
    public void getNonExistingDemande() throws Exception {
        // Get the demande
        restDemandeMockMvc.perform(get("/api/demandes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDemande() throws Exception {
        // Initialize the database
        demandeRepository.save(demande);

		int databaseSizeBeforeUpdate = demandeRepository.findAll().size();

        // Update the demande
        demande.setNature(UPDATED_NATURE);
        demande.setType(UPDATED_TYPE);
        demande.setStatut(UPDATED_STATUT);
        
        DemandeDTO demandeDTO = demandeMapper.demandeToDemandeDTO(demande);

        restDemandeMockMvc.perform(put("/api/demandes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(demandeDTO)))
                .andExpect(status().isOk());

        // Validate the Demande in the database
        List<Demande> demandes = demandeRepository.findAll();
        assertThat(demandes).hasSize(databaseSizeBeforeUpdate);
        Demande testDemande = demandes.get(demandes.size() - 1);
        assertThat(testDemande.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testDemande.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDemande.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    public void deleteDemande() throws Exception {
        // Initialize the database
        demandeRepository.save(demande);

		int databaseSizeBeforeDelete = demandeRepository.findAll().size();

        // Get the demande
        restDemandeMockMvc.perform(delete("/api/demandes/{id}", demande.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Demande> demandes = demandeRepository.findAll();
        assertThat(demandes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
