package com.a00n.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.a00n.IntegrationTest;
import com.a00n.domain.Tooth;
import com.a00n.repository.ToothRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link ToothResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ToothResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/teeth";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ToothRepository toothRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restToothMockMvc;

    private Tooth tooth;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tooth createEntity(EntityManager em) {
        Tooth tooth = new Tooth().name(DEFAULT_NAME);
        return tooth;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tooth createUpdatedEntity(EntityManager em) {
        Tooth tooth = new Tooth().name(UPDATED_NAME);
        return tooth;
    }

    @BeforeEach
    public void initTest() {
        tooth = createEntity(em);
    }

    @Test
    @Transactional
    void createTooth() throws Exception {
        int databaseSizeBeforeCreate = toothRepository.findAll().size();
        // Create the Tooth
        restToothMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tooth)))
            .andExpect(status().isCreated());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeCreate + 1);
        Tooth testTooth = toothList.get(toothList.size() - 1);
        assertThat(testTooth.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createToothWithExistingId() throws Exception {
        // Create the Tooth with an existing ID
        tooth.setId(1L);

        int databaseSizeBeforeCreate = toothRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restToothMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tooth)))
            .andExpect(status().isBadRequest());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeeth() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList
        restToothMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tooth.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getTooth() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get the tooth
        restToothMockMvc
            .perform(get(ENTITY_API_URL_ID, tooth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tooth.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTooth() throws Exception {
        // Get the tooth
        restToothMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTooth() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        int databaseSizeBeforeUpdate = toothRepository.findAll().size();

        // Update the tooth
        Tooth updatedTooth = toothRepository.findById(tooth.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTooth are not directly saved in db
        em.detach(updatedTooth);
        updatedTooth.name(UPDATED_NAME);

        restToothMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTooth.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTooth))
            )
            .andExpect(status().isOk());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
        Tooth testTooth = toothList.get(toothList.size() - 1);
        assertThat(testTooth.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTooth() throws Exception {
        int databaseSizeBeforeUpdate = toothRepository.findAll().size();
        tooth.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToothMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tooth.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tooth))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTooth() throws Exception {
        int databaseSizeBeforeUpdate = toothRepository.findAll().size();
        tooth.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToothMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tooth))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTooth() throws Exception {
        int databaseSizeBeforeUpdate = toothRepository.findAll().size();
        tooth.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToothMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tooth)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateToothWithPatch() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        int databaseSizeBeforeUpdate = toothRepository.findAll().size();

        // Update the tooth using partial update
        Tooth partialUpdatedTooth = new Tooth();
        partialUpdatedTooth.setId(tooth.getId());

        restToothMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTooth.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTooth))
            )
            .andExpect(status().isOk());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
        Tooth testTooth = toothList.get(toothList.size() - 1);
        assertThat(testTooth.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateToothWithPatch() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        int databaseSizeBeforeUpdate = toothRepository.findAll().size();

        // Update the tooth using partial update
        Tooth partialUpdatedTooth = new Tooth();
        partialUpdatedTooth.setId(tooth.getId());

        partialUpdatedTooth.name(UPDATED_NAME);

        restToothMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTooth.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTooth))
            )
            .andExpect(status().isOk());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
        Tooth testTooth = toothList.get(toothList.size() - 1);
        assertThat(testTooth.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTooth() throws Exception {
        int databaseSizeBeforeUpdate = toothRepository.findAll().size();
        tooth.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToothMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tooth.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tooth))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTooth() throws Exception {
        int databaseSizeBeforeUpdate = toothRepository.findAll().size();
        tooth.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToothMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tooth))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTooth() throws Exception {
        int databaseSizeBeforeUpdate = toothRepository.findAll().size();
        tooth.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restToothMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tooth)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTooth() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        int databaseSizeBeforeDelete = toothRepository.findAll().size();

        // Delete the tooth
        restToothMockMvc
            .perform(delete(ENTITY_API_URL_ID, tooth.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
