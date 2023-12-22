package com.a00n.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.a00n.IntegrationTest;
import com.a00n.domain.AcademicYear;
import com.a00n.domain.PW;
import com.a00n.domain.Tooth;
import com.a00n.repository.PWRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PWResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PWResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIF = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIF = "BBBBBBBBBB";

    private static final String DEFAULT_DOCS = "AAAAAAAAAA";
    private static final String UPDATED_DOCS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEADLINE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEADLINE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/pws";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PWRepository pWRepository;

    @Mock
    private PWRepository pWRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPWMockMvc;

    private PW pW;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PW createEntity(EntityManager em) {
        PW pW = new PW().title(DEFAULT_TITLE).objectif(DEFAULT_OBJECTIF).docs(DEFAULT_DOCS).deadline(DEFAULT_DEADLINE);
        // Add required entity
        Tooth tooth;
        if (TestUtil.findAll(em, Tooth.class).isEmpty()) {
            tooth = ToothResourceIT.createEntity(em);
            em.persist(tooth);
            em.flush();
        } else {
            tooth = TestUtil.findAll(em, Tooth.class).get(0);
        }
        pW.setTooth(tooth);
        // Add required entity
        AcademicYear academicYear;
        if (TestUtil.findAll(em, AcademicYear.class).isEmpty()) {
            academicYear = AcademicYearResourceIT.createEntity(em);
            em.persist(academicYear);
            em.flush();
        } else {
            academicYear = TestUtil.findAll(em, AcademicYear.class).get(0);
        }
        pW.setAcademicYear(academicYear);
        return pW;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PW createUpdatedEntity(EntityManager em) {
        PW pW = new PW().title(UPDATED_TITLE).objectif(UPDATED_OBJECTIF).docs(UPDATED_DOCS).deadline(UPDATED_DEADLINE);
        // Add required entity
        Tooth tooth;
        if (TestUtil.findAll(em, Tooth.class).isEmpty()) {
            tooth = ToothResourceIT.createUpdatedEntity(em);
            em.persist(tooth);
            em.flush();
        } else {
            tooth = TestUtil.findAll(em, Tooth.class).get(0);
        }
        pW.setTooth(tooth);
        // Add required entity
        AcademicYear academicYear;
        if (TestUtil.findAll(em, AcademicYear.class).isEmpty()) {
            academicYear = AcademicYearResourceIT.createUpdatedEntity(em);
            em.persist(academicYear);
            em.flush();
        } else {
            academicYear = TestUtil.findAll(em, AcademicYear.class).get(0);
        }
        pW.setAcademicYear(academicYear);
        return pW;
    }

    @BeforeEach
    public void initTest() {
        pW = createEntity(em);
    }

    @Test
    @Transactional
    void createPW() throws Exception {
        int databaseSizeBeforeCreate = pWRepository.findAll().size();
        // Create the PW
        restPWMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pW)))
            .andExpect(status().isCreated());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeCreate + 1);
        PW testPW = pWList.get(pWList.size() - 1);
        assertThat(testPW.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPW.getObjectif()).isEqualTo(DEFAULT_OBJECTIF);
        assertThat(testPW.getDocs()).isEqualTo(DEFAULT_DOCS);
        assertThat(testPW.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
    }

    @Test
    @Transactional
    void createPWWithExistingId() throws Exception {
        // Create the PW with an existing ID
        pW.setId(1L);

        int databaseSizeBeforeCreate = pWRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPWMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pW)))
            .andExpect(status().isBadRequest());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPWS() throws Exception {
        // Initialize the database
        pWRepository.saveAndFlush(pW);

        // Get all the pWList
        restPWMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pW.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].objectif").value(hasItem(DEFAULT_OBJECTIF)))
            .andExpect(jsonPath("$.[*].docs").value(hasItem(DEFAULT_DOCS)))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPWSWithEagerRelationshipsIsEnabled() throws Exception {
        when(pWRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPWMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pWRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPWSWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pWRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPWMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(pWRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPW() throws Exception {
        // Initialize the database
        pWRepository.saveAndFlush(pW);

        // Get the pW
        restPWMockMvc
            .perform(get(ENTITY_API_URL_ID, pW.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pW.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.objectif").value(DEFAULT_OBJECTIF))
            .andExpect(jsonPath("$.docs").value(DEFAULT_DOCS))
            .andExpect(jsonPath("$.deadline").value(DEFAULT_DEADLINE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPW() throws Exception {
        // Get the pW
        restPWMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPW() throws Exception {
        // Initialize the database
        pWRepository.saveAndFlush(pW);

        int databaseSizeBeforeUpdate = pWRepository.findAll().size();

        // Update the pW
        PW updatedPW = pWRepository.findById(pW.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPW are not directly saved in db
        em.detach(updatedPW);
        updatedPW.title(UPDATED_TITLE).objectif(UPDATED_OBJECTIF).docs(UPDATED_DOCS).deadline(UPDATED_DEADLINE);

        restPWMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPW.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPW))
            )
            .andExpect(status().isOk());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeUpdate);
        PW testPW = pWList.get(pWList.size() - 1);
        assertThat(testPW.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPW.getObjectif()).isEqualTo(UPDATED_OBJECTIF);
        assertThat(testPW.getDocs()).isEqualTo(UPDATED_DOCS);
        assertThat(testPW.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    void putNonExistingPW() throws Exception {
        int databaseSizeBeforeUpdate = pWRepository.findAll().size();
        pW.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPWMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pW.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pW))
            )
            .andExpect(status().isBadRequest());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPW() throws Exception {
        int databaseSizeBeforeUpdate = pWRepository.findAll().size();
        pW.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPWMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pW))
            )
            .andExpect(status().isBadRequest());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPW() throws Exception {
        int databaseSizeBeforeUpdate = pWRepository.findAll().size();
        pW.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPWMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pW)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePWWithPatch() throws Exception {
        // Initialize the database
        pWRepository.saveAndFlush(pW);

        int databaseSizeBeforeUpdate = pWRepository.findAll().size();

        // Update the pW using partial update
        PW partialUpdatedPW = new PW();
        partialUpdatedPW.setId(pW.getId());

        partialUpdatedPW.title(UPDATED_TITLE).objectif(UPDATED_OBJECTIF).deadline(UPDATED_DEADLINE);

        restPWMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPW.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPW))
            )
            .andExpect(status().isOk());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeUpdate);
        PW testPW = pWList.get(pWList.size() - 1);
        assertThat(testPW.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPW.getObjectif()).isEqualTo(UPDATED_OBJECTIF);
        assertThat(testPW.getDocs()).isEqualTo(DEFAULT_DOCS);
        assertThat(testPW.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    void fullUpdatePWWithPatch() throws Exception {
        // Initialize the database
        pWRepository.saveAndFlush(pW);

        int databaseSizeBeforeUpdate = pWRepository.findAll().size();

        // Update the pW using partial update
        PW partialUpdatedPW = new PW();
        partialUpdatedPW.setId(pW.getId());

        partialUpdatedPW.title(UPDATED_TITLE).objectif(UPDATED_OBJECTIF).docs(UPDATED_DOCS).deadline(UPDATED_DEADLINE);

        restPWMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPW.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPW))
            )
            .andExpect(status().isOk());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeUpdate);
        PW testPW = pWList.get(pWList.size() - 1);
        assertThat(testPW.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPW.getObjectif()).isEqualTo(UPDATED_OBJECTIF);
        assertThat(testPW.getDocs()).isEqualTo(UPDATED_DOCS);
        assertThat(testPW.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    @Transactional
    void patchNonExistingPW() throws Exception {
        int databaseSizeBeforeUpdate = pWRepository.findAll().size();
        pW.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPWMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pW.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pW))
            )
            .andExpect(status().isBadRequest());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPW() throws Exception {
        int databaseSizeBeforeUpdate = pWRepository.findAll().size();
        pW.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPWMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pW))
            )
            .andExpect(status().isBadRequest());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPW() throws Exception {
        int databaseSizeBeforeUpdate = pWRepository.findAll().size();
        pW.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPWMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pW)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PW in the database
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePW() throws Exception {
        // Initialize the database
        pWRepository.saveAndFlush(pW);

        int databaseSizeBeforeDelete = pWRepository.findAll().size();

        // Delete the pW
        restPWMockMvc.perform(delete(ENTITY_API_URL_ID, pW.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PW> pWList = pWRepository.findAll();
        assertThat(pWList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
