package com.a00n.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.a00n.IntegrationTest;
import com.a00n.domain.AcademicYear;
import com.a00n.repository.AcademicYearRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AcademicYearResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcademicYearResourceIT {

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/academic-years";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcademicYearRepository academicYearRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademicYearMockMvc;

    private AcademicYear academicYear;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicYear createEntity(EntityManager em) {
        AcademicYear academicYear = new AcademicYear()
            .year(DEFAULT_YEAR)
            .startingDate(DEFAULT_STARTING_DATE)
            .endingDate(DEFAULT_ENDING_DATE);
        return academicYear;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicYear createUpdatedEntity(EntityManager em) {
        AcademicYear academicYear = new AcademicYear()
            .year(UPDATED_YEAR)
            .startingDate(UPDATED_STARTING_DATE)
            .endingDate(UPDATED_ENDING_DATE);
        return academicYear;
    }

    @BeforeEach
    public void initTest() {
        academicYear = createEntity(em);
    }

    @Test
    @Transactional
    void createAcademicYear() throws Exception {
        int databaseSizeBeforeCreate = academicYearRepository.findAll().size();
        // Create the AcademicYear
        restAcademicYearMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicYear)))
            .andExpect(status().isCreated());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicYear testAcademicYear = academicYearList.get(academicYearList.size() - 1);
        assertThat(testAcademicYear.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAcademicYear.getStartingDate()).isEqualTo(DEFAULT_STARTING_DATE);
        assertThat(testAcademicYear.getEndingDate()).isEqualTo(DEFAULT_ENDING_DATE);
    }

    @Test
    @Transactional
    void createAcademicYearWithExistingId() throws Exception {
        // Create the AcademicYear with an existing ID
        academicYear.setId(1L);

        int databaseSizeBeforeCreate = academicYearRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicYearMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicYear)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAcademicYears() throws Exception {
        // Initialize the database
        academicYearRepository.saveAndFlush(academicYear);

        // Get all the academicYearList
        restAcademicYearMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].startingDate").value(hasItem(DEFAULT_STARTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endingDate").value(hasItem(DEFAULT_ENDING_DATE.toString())));
    }

    @Test
    @Transactional
    void getAcademicYear() throws Exception {
        // Initialize the database
        academicYearRepository.saveAndFlush(academicYear);

        // Get the academicYear
        restAcademicYearMockMvc
            .perform(get(ENTITY_API_URL_ID, academicYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academicYear.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.startingDate").value(DEFAULT_STARTING_DATE.toString()))
            .andExpect(jsonPath("$.endingDate").value(DEFAULT_ENDING_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAcademicYear() throws Exception {
        // Get the academicYear
        restAcademicYearMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAcademicYear() throws Exception {
        // Initialize the database
        academicYearRepository.saveAndFlush(academicYear);

        int databaseSizeBeforeUpdate = academicYearRepository.findAll().size();

        // Update the academicYear
        AcademicYear updatedAcademicYear = academicYearRepository.findById(academicYear.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAcademicYear are not directly saved in db
        em.detach(updatedAcademicYear);
        updatedAcademicYear.year(UPDATED_YEAR).startingDate(UPDATED_STARTING_DATE).endingDate(UPDATED_ENDING_DATE);

        restAcademicYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAcademicYear.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAcademicYear))
            )
            .andExpect(status().isOk());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeUpdate);
        AcademicYear testAcademicYear = academicYearList.get(academicYearList.size() - 1);
        assertThat(testAcademicYear.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAcademicYear.getStartingDate()).isEqualTo(UPDATED_STARTING_DATE);
        assertThat(testAcademicYear.getEndingDate()).isEqualTo(UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAcademicYear() throws Exception {
        int databaseSizeBeforeUpdate = academicYearRepository.findAll().size();
        academicYear.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academicYear.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicYear))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcademicYear() throws Exception {
        int databaseSizeBeforeUpdate = academicYearRepository.findAll().size();
        academicYear.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicYear))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcademicYear() throws Exception {
        int databaseSizeBeforeUpdate = academicYearRepository.findAll().size();
        academicYear.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicYearMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicYear)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcademicYearWithPatch() throws Exception {
        // Initialize the database
        academicYearRepository.saveAndFlush(academicYear);

        int databaseSizeBeforeUpdate = academicYearRepository.findAll().size();

        // Update the academicYear using partial update
        AcademicYear partialUpdatedAcademicYear = new AcademicYear();
        partialUpdatedAcademicYear.setId(academicYear.getId());

        restAcademicYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicYear.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademicYear))
            )
            .andExpect(status().isOk());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeUpdate);
        AcademicYear testAcademicYear = academicYearList.get(academicYearList.size() - 1);
        assertThat(testAcademicYear.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAcademicYear.getStartingDate()).isEqualTo(DEFAULT_STARTING_DATE);
        assertThat(testAcademicYear.getEndingDate()).isEqualTo(DEFAULT_ENDING_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAcademicYearWithPatch() throws Exception {
        // Initialize the database
        academicYearRepository.saveAndFlush(academicYear);

        int databaseSizeBeforeUpdate = academicYearRepository.findAll().size();

        // Update the academicYear using partial update
        AcademicYear partialUpdatedAcademicYear = new AcademicYear();
        partialUpdatedAcademicYear.setId(academicYear.getId());

        partialUpdatedAcademicYear.year(UPDATED_YEAR).startingDate(UPDATED_STARTING_DATE).endingDate(UPDATED_ENDING_DATE);

        restAcademicYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicYear.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademicYear))
            )
            .andExpect(status().isOk());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeUpdate);
        AcademicYear testAcademicYear = academicYearList.get(academicYearList.size() - 1);
        assertThat(testAcademicYear.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAcademicYear.getStartingDate()).isEqualTo(UPDATED_STARTING_DATE);
        assertThat(testAcademicYear.getEndingDate()).isEqualTo(UPDATED_ENDING_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAcademicYear() throws Exception {
        int databaseSizeBeforeUpdate = academicYearRepository.findAll().size();
        academicYear.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, academicYear.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicYear))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcademicYear() throws Exception {
        int databaseSizeBeforeUpdate = academicYearRepository.findAll().size();
        academicYear.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicYear))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcademicYear() throws Exception {
        int databaseSizeBeforeUpdate = academicYearRepository.findAll().size();
        academicYear.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicYearMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(academicYear))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicYear in the database
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcademicYear() throws Exception {
        // Initialize the database
        academicYearRepository.saveAndFlush(academicYear);

        int databaseSizeBeforeDelete = academicYearRepository.findAll().size();

        // Delete the academicYear
        restAcademicYearMockMvc
            .perform(delete(ENTITY_API_URL_ID, academicYear.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcademicYear> academicYearList = academicYearRepository.findAll();
        assertThat(academicYearList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
