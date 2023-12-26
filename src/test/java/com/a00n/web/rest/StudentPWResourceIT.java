package com.a00n.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.a00n.IntegrationTest;
import com.a00n.domain.AcademicYear;
import com.a00n.domain.PW;
import com.a00n.domain.Student;
import com.a00n.domain.StudentPW;
import com.a00n.repository.StudentPWRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
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
 * Integration tests for the {@link StudentPWResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StudentPWResourceIT {

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_FRONT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_FRONT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_FRONT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_FRONT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_SIDE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_SIDE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_SIDE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_SIDE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_NOTE = 1D;
    private static final Double UPDATED_NOTE = 2D;

    private static final Double DEFAULT_A_1_IMAGE_SIDE = 1D;
    private static final Double UPDATED_A_1_IMAGE_SIDE = 2D;

    private static final Double DEFAULT_A_2_IMAGE_SIDE = 1D;
    private static final Double UPDATED_A_2_IMAGE_SIDE = 2D;

    private static final Double DEFAULT_A_3_IMAGE_SIDE = 1D;
    private static final Double UPDATED_A_3_IMAGE_SIDE = 2D;

    private static final Double DEFAULT_P_1_IMAGE_SIDE = 1D;
    private static final Double UPDATED_P_1_IMAGE_SIDE = 2D;

    private static final Double DEFAULT_P_2_IMAGE_SIDE = 1D;
    private static final Double UPDATED_P_2_IMAGE_SIDE = 2D;

    private static final Double DEFAULT_P_3_IMAGE_SIDE = 1D;
    private static final Double UPDATED_P_3_IMAGE_SIDE = 2D;

    private static final Double DEFAULT_A_1_IMAGE_FRONT = 1D;
    private static final Double UPDATED_A_1_IMAGE_FRONT = 2D;

    private static final Double DEFAULT_A_2_IMAGE_FRONT = 1D;
    private static final Double UPDATED_A_2_IMAGE_FRONT = 2D;

    private static final Double DEFAULT_A_3_IMAGE_FRONT = 1D;
    private static final Double UPDATED_A_3_IMAGE_FRONT = 2D;

    private static final Double DEFAULT_P_1_IMAGE_FRONT = 1D;
    private static final Double UPDATED_P_1_IMAGE_FRONT = 2D;

    private static final Double DEFAULT_P_2_IMAGE_FRONT = 1D;
    private static final Double UPDATED_P_2_IMAGE_FRONT = 2D;

    private static final Double DEFAULT_P_3_IMAGE_FRONT = 1D;
    private static final Double UPDATED_P_3_IMAGE_FRONT = 2D;

    private static final String ENTITY_API_URL = "/api/student-pws";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudentPWRepository studentPWRepository;

    @Mock
    private StudentPWRepository studentPWRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentPWMockMvc;

    private StudentPW studentPW;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentPW createEntity(EntityManager em) {
        StudentPW studentPW = new StudentPW()
            .time(DEFAULT_TIME)
            .imageFront(DEFAULT_IMAGE_FRONT)
            .imageFrontContentType(DEFAULT_IMAGE_FRONT_CONTENT_TYPE)
            .imageSide(DEFAULT_IMAGE_SIDE)
            .imageSideContentType(DEFAULT_IMAGE_SIDE_CONTENT_TYPE)
            .date(DEFAULT_DATE)
            .note(DEFAULT_NOTE)
            .a1ImageSide(DEFAULT_A_1_IMAGE_SIDE)
            .a2ImageSide(DEFAULT_A_2_IMAGE_SIDE)
            .a3ImageSide(DEFAULT_A_3_IMAGE_SIDE)
            .p1ImageSide(DEFAULT_P_1_IMAGE_SIDE)
            .p2ImageSide(DEFAULT_P_2_IMAGE_SIDE)
            .p3ImageSide(DEFAULT_P_3_IMAGE_SIDE)
            .a1ImageFront(DEFAULT_A_1_IMAGE_FRONT)
            .a2ImageFront(DEFAULT_A_2_IMAGE_FRONT)
            .a3ImageFront(DEFAULT_A_3_IMAGE_FRONT)
            .p1ImageFront(DEFAULT_P_1_IMAGE_FRONT)
            .p2ImageFront(DEFAULT_P_2_IMAGE_FRONT)
            .p3ImageFront(DEFAULT_P_3_IMAGE_FRONT);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        studentPW.setStudent(student);
        // Add required entity
        PW pW;
        if (TestUtil.findAll(em, PW.class).isEmpty()) {
            pW = PWResourceIT.createEntity(em);
            em.persist(pW);
            em.flush();
        } else {
            pW = TestUtil.findAll(em, PW.class).get(0);
        }
        studentPW.setPw(pW);
        // Add required entity
        AcademicYear academicYear;
        if (TestUtil.findAll(em, AcademicYear.class).isEmpty()) {
            academicYear = AcademicYearResourceIT.createEntity(em);
            em.persist(academicYear);
            em.flush();
        } else {
            academicYear = TestUtil.findAll(em, AcademicYear.class).get(0);
        }
        studentPW.setAcademicYear(academicYear);
        return studentPW;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentPW createUpdatedEntity(EntityManager em) {
        StudentPW studentPW = new StudentPW()
            .time(UPDATED_TIME)
            .imageFront(UPDATED_IMAGE_FRONT)
            .imageFrontContentType(UPDATED_IMAGE_FRONT_CONTENT_TYPE)
            .imageSide(UPDATED_IMAGE_SIDE)
            .imageSideContentType(UPDATED_IMAGE_SIDE_CONTENT_TYPE)
            .date(UPDATED_DATE)
            .note(UPDATED_NOTE)
            .a1ImageSide(UPDATED_A_1_IMAGE_SIDE)
            .a2ImageSide(UPDATED_A_2_IMAGE_SIDE)
            .a3ImageSide(UPDATED_A_3_IMAGE_SIDE)
            .p1ImageSide(UPDATED_P_1_IMAGE_SIDE)
            .p2ImageSide(UPDATED_P_2_IMAGE_SIDE)
            .p3ImageSide(UPDATED_P_3_IMAGE_SIDE)
            .a1ImageFront(UPDATED_A_1_IMAGE_FRONT)
            .a2ImageFront(UPDATED_A_2_IMAGE_FRONT)
            .a3ImageFront(UPDATED_A_3_IMAGE_FRONT)
            .p1ImageFront(UPDATED_P_1_IMAGE_FRONT)
            .p2ImageFront(UPDATED_P_2_IMAGE_FRONT)
            .p3ImageFront(UPDATED_P_3_IMAGE_FRONT);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createUpdatedEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        studentPW.setStudent(student);
        // Add required entity
        PW pW;
        if (TestUtil.findAll(em, PW.class).isEmpty()) {
            pW = PWResourceIT.createUpdatedEntity(em);
            em.persist(pW);
            em.flush();
        } else {
            pW = TestUtil.findAll(em, PW.class).get(0);
        }
        studentPW.setPw(pW);
        // Add required entity
        AcademicYear academicYear;
        if (TestUtil.findAll(em, AcademicYear.class).isEmpty()) {
            academicYear = AcademicYearResourceIT.createUpdatedEntity(em);
            em.persist(academicYear);
            em.flush();
        } else {
            academicYear = TestUtil.findAll(em, AcademicYear.class).get(0);
        }
        studentPW.setAcademicYear(academicYear);
        return studentPW;
    }

    @BeforeEach
    public void initTest() {
        studentPW = createEntity(em);
    }

    @Test
    @Transactional
    void createStudentPW() throws Exception {
        int databaseSizeBeforeCreate = studentPWRepository.findAll().size();
        // Create the StudentPW
        restStudentPWMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentPW)))
            .andExpect(status().isCreated());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeCreate + 1);
        StudentPW testStudentPW = studentPWList.get(studentPWList.size() - 1);
        assertThat(testStudentPW.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testStudentPW.getImageFront()).isEqualTo(DEFAULT_IMAGE_FRONT);
        assertThat(testStudentPW.getImageFrontContentType()).isEqualTo(DEFAULT_IMAGE_FRONT_CONTENT_TYPE);
        assertThat(testStudentPW.getImageSide()).isEqualTo(DEFAULT_IMAGE_SIDE);
        assertThat(testStudentPW.getImageSideContentType()).isEqualTo(DEFAULT_IMAGE_SIDE_CONTENT_TYPE);
        assertThat(testStudentPW.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testStudentPW.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testStudentPW.geta1ImageSide()).isEqualTo(DEFAULT_A_1_IMAGE_SIDE);
        assertThat(testStudentPW.geta2ImageSide()).isEqualTo(DEFAULT_A_2_IMAGE_SIDE);
        assertThat(testStudentPW.geta3ImageSide()).isEqualTo(DEFAULT_A_3_IMAGE_SIDE);
        assertThat(testStudentPW.getp1ImageSide()).isEqualTo(DEFAULT_P_1_IMAGE_SIDE);
        assertThat(testStudentPW.getp2ImageSide()).isEqualTo(DEFAULT_P_2_IMAGE_SIDE);
        assertThat(testStudentPW.getp3ImageSide()).isEqualTo(DEFAULT_P_3_IMAGE_SIDE);
        assertThat(testStudentPW.geta1ImageFront()).isEqualTo(DEFAULT_A_1_IMAGE_FRONT);
        assertThat(testStudentPW.geta2ImageFront()).isEqualTo(DEFAULT_A_2_IMAGE_FRONT);
        assertThat(testStudentPW.geta3ImageFront()).isEqualTo(DEFAULT_A_3_IMAGE_FRONT);
        assertThat(testStudentPW.getp1ImageFront()).isEqualTo(DEFAULT_P_1_IMAGE_FRONT);
        assertThat(testStudentPW.getp2ImageFront()).isEqualTo(DEFAULT_P_2_IMAGE_FRONT);
        assertThat(testStudentPW.getp3ImageFront()).isEqualTo(DEFAULT_P_3_IMAGE_FRONT);
    }

    @Test
    @Transactional
    void createStudentPWWithExistingId() throws Exception {
        // Create the StudentPW with an existing ID
        studentPW.setId(1L);

        int databaseSizeBeforeCreate = studentPWRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentPWMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentPW)))
            .andExpect(status().isBadRequest());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStudentPWS() throws Exception {
        // Initialize the database
        studentPWRepository.saveAndFlush(studentPW);

        // Get all the studentPWList
        restStudentPWMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentPW.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].imageFrontContentType").value(hasItem(DEFAULT_IMAGE_FRONT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageFront").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE_FRONT))))
            .andExpect(jsonPath("$.[*].imageSideContentType").value(hasItem(DEFAULT_IMAGE_SIDE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageSide").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE_SIDE))))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.doubleValue())))
            .andExpect(jsonPath("$.[*].a1ImageSide").value(hasItem(DEFAULT_A_1_IMAGE_SIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].a2ImageSide").value(hasItem(DEFAULT_A_2_IMAGE_SIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].a3ImageSide").value(hasItem(DEFAULT_A_3_IMAGE_SIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].p1ImageSide").value(hasItem(DEFAULT_P_1_IMAGE_SIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].p2ImageSide").value(hasItem(DEFAULT_P_2_IMAGE_SIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].p3ImageSide").value(hasItem(DEFAULT_P_3_IMAGE_SIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].a1ImageFront").value(hasItem(DEFAULT_A_1_IMAGE_FRONT.doubleValue())))
            .andExpect(jsonPath("$.[*].a2ImageFront").value(hasItem(DEFAULT_A_2_IMAGE_FRONT.doubleValue())))
            .andExpect(jsonPath("$.[*].a3ImageFront").value(hasItem(DEFAULT_A_3_IMAGE_FRONT.doubleValue())))
            .andExpect(jsonPath("$.[*].p1ImageFront").value(hasItem(DEFAULT_P_1_IMAGE_FRONT.doubleValue())))
            .andExpect(jsonPath("$.[*].p2ImageFront").value(hasItem(DEFAULT_P_2_IMAGE_FRONT.doubleValue())))
            .andExpect(jsonPath("$.[*].p3ImageFront").value(hasItem(DEFAULT_P_3_IMAGE_FRONT.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStudentPWSWithEagerRelationshipsIsEnabled() throws Exception {
        when(studentPWRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStudentPWMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(studentPWRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStudentPWSWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(studentPWRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStudentPWMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(studentPWRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStudentPW() throws Exception {
        // Initialize the database
        studentPWRepository.saveAndFlush(studentPW);

        // Get the studentPW
        restStudentPWMockMvc
            .perform(get(ENTITY_API_URL_ID, studentPW.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentPW.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.imageFrontContentType").value(DEFAULT_IMAGE_FRONT_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageFront").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE_FRONT)))
            .andExpect(jsonPath("$.imageSideContentType").value(DEFAULT_IMAGE_SIDE_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageSide").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE_SIDE)))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.doubleValue()))
            .andExpect(jsonPath("$.a1ImageSide").value(DEFAULT_A_1_IMAGE_SIDE.doubleValue()))
            .andExpect(jsonPath("$.a2ImageSide").value(DEFAULT_A_2_IMAGE_SIDE.doubleValue()))
            .andExpect(jsonPath("$.a3ImageSide").value(DEFAULT_A_3_IMAGE_SIDE.doubleValue()))
            .andExpect(jsonPath("$.p1ImageSide").value(DEFAULT_P_1_IMAGE_SIDE.doubleValue()))
            .andExpect(jsonPath("$.p2ImageSide").value(DEFAULT_P_2_IMAGE_SIDE.doubleValue()))
            .andExpect(jsonPath("$.p3ImageSide").value(DEFAULT_P_3_IMAGE_SIDE.doubleValue()))
            .andExpect(jsonPath("$.a1ImageFront").value(DEFAULT_A_1_IMAGE_FRONT.doubleValue()))
            .andExpect(jsonPath("$.a2ImageFront").value(DEFAULT_A_2_IMAGE_FRONT.doubleValue()))
            .andExpect(jsonPath("$.a3ImageFront").value(DEFAULT_A_3_IMAGE_FRONT.doubleValue()))
            .andExpect(jsonPath("$.p1ImageFront").value(DEFAULT_P_1_IMAGE_FRONT.doubleValue()))
            .andExpect(jsonPath("$.p2ImageFront").value(DEFAULT_P_2_IMAGE_FRONT.doubleValue()))
            .andExpect(jsonPath("$.p3ImageFront").value(DEFAULT_P_3_IMAGE_FRONT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingStudentPW() throws Exception {
        // Get the studentPW
        restStudentPWMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStudentPW() throws Exception {
        // Initialize the database
        studentPWRepository.saveAndFlush(studentPW);

        int databaseSizeBeforeUpdate = studentPWRepository.findAll().size();

        // Update the studentPW
        StudentPW updatedStudentPW = studentPWRepository.findById(studentPW.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStudentPW are not directly saved in db
        em.detach(updatedStudentPW);
        updatedStudentPW
            .time(UPDATED_TIME)
            .imageFront(UPDATED_IMAGE_FRONT)
            .imageFrontContentType(UPDATED_IMAGE_FRONT_CONTENT_TYPE)
            .imageSide(UPDATED_IMAGE_SIDE)
            .imageSideContentType(UPDATED_IMAGE_SIDE_CONTENT_TYPE)
            .date(UPDATED_DATE)
            .note(UPDATED_NOTE)
            .a1ImageSide(UPDATED_A_1_IMAGE_SIDE)
            .a2ImageSide(UPDATED_A_2_IMAGE_SIDE)
            .a3ImageSide(UPDATED_A_3_IMAGE_SIDE)
            .p1ImageSide(UPDATED_P_1_IMAGE_SIDE)
            .p2ImageSide(UPDATED_P_2_IMAGE_SIDE)
            .p3ImageSide(UPDATED_P_3_IMAGE_SIDE)
            .a1ImageFront(UPDATED_A_1_IMAGE_FRONT)
            .a2ImageFront(UPDATED_A_2_IMAGE_FRONT)
            .a3ImageFront(UPDATED_A_3_IMAGE_FRONT)
            .p1ImageFront(UPDATED_P_1_IMAGE_FRONT)
            .p2ImageFront(UPDATED_P_2_IMAGE_FRONT)
            .p3ImageFront(UPDATED_P_3_IMAGE_FRONT);

        restStudentPWMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStudentPW.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStudentPW))
            )
            .andExpect(status().isOk());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeUpdate);
        StudentPW testStudentPW = studentPWList.get(studentPWList.size() - 1);
        assertThat(testStudentPW.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testStudentPW.getImageFront()).isEqualTo(UPDATED_IMAGE_FRONT);
        assertThat(testStudentPW.getImageFrontContentType()).isEqualTo(UPDATED_IMAGE_FRONT_CONTENT_TYPE);
        assertThat(testStudentPW.getImageSide()).isEqualTo(UPDATED_IMAGE_SIDE);
        assertThat(testStudentPW.getImageSideContentType()).isEqualTo(UPDATED_IMAGE_SIDE_CONTENT_TYPE);
        assertThat(testStudentPW.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStudentPW.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testStudentPW.geta1ImageSide()).isEqualTo(UPDATED_A_1_IMAGE_SIDE);
        assertThat(testStudentPW.geta2ImageSide()).isEqualTo(UPDATED_A_2_IMAGE_SIDE);
        assertThat(testStudentPW.geta3ImageSide()).isEqualTo(UPDATED_A_3_IMAGE_SIDE);
        assertThat(testStudentPW.getp1ImageSide()).isEqualTo(UPDATED_P_1_IMAGE_SIDE);
        assertThat(testStudentPW.getp2ImageSide()).isEqualTo(UPDATED_P_2_IMAGE_SIDE);
        assertThat(testStudentPW.getp3ImageSide()).isEqualTo(UPDATED_P_3_IMAGE_SIDE);
        assertThat(testStudentPW.geta1ImageFront()).isEqualTo(UPDATED_A_1_IMAGE_FRONT);
        assertThat(testStudentPW.geta2ImageFront()).isEqualTo(UPDATED_A_2_IMAGE_FRONT);
        assertThat(testStudentPW.geta3ImageFront()).isEqualTo(UPDATED_A_3_IMAGE_FRONT);
        assertThat(testStudentPW.getp1ImageFront()).isEqualTo(UPDATED_P_1_IMAGE_FRONT);
        assertThat(testStudentPW.getp2ImageFront()).isEqualTo(UPDATED_P_2_IMAGE_FRONT);
        assertThat(testStudentPW.getp3ImageFront()).isEqualTo(UPDATED_P_3_IMAGE_FRONT);
    }

    @Test
    @Transactional
    void putNonExistingStudentPW() throws Exception {
        int databaseSizeBeforeUpdate = studentPWRepository.findAll().size();
        studentPW.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentPWMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentPW.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentPW))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudentPW() throws Exception {
        int databaseSizeBeforeUpdate = studentPWRepository.findAll().size();
        studentPW.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentPWMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentPW))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudentPW() throws Exception {
        int databaseSizeBeforeUpdate = studentPWRepository.findAll().size();
        studentPW.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentPWMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentPW)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentPWWithPatch() throws Exception {
        // Initialize the database
        studentPWRepository.saveAndFlush(studentPW);

        int databaseSizeBeforeUpdate = studentPWRepository.findAll().size();

        // Update the studentPW using partial update
        StudentPW partialUpdatedStudentPW = new StudentPW();
        partialUpdatedStudentPW.setId(studentPW.getId());

        partialUpdatedStudentPW
            .time(UPDATED_TIME)
            .imageSide(UPDATED_IMAGE_SIDE)
            .imageSideContentType(UPDATED_IMAGE_SIDE_CONTENT_TYPE)
            .date(UPDATED_DATE)
            .a1ImageSide(UPDATED_A_1_IMAGE_SIDE)
            .a2ImageSide(UPDATED_A_2_IMAGE_SIDE)
            .p3ImageSide(UPDATED_P_3_IMAGE_SIDE)
            .a1ImageFront(UPDATED_A_1_IMAGE_FRONT)
            .a2ImageFront(UPDATED_A_2_IMAGE_FRONT)
            .p1ImageFront(UPDATED_P_1_IMAGE_FRONT)
            .p2ImageFront(UPDATED_P_2_IMAGE_FRONT)
            .p3ImageFront(UPDATED_P_3_IMAGE_FRONT);

        restStudentPWMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentPW.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudentPW))
            )
            .andExpect(status().isOk());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeUpdate);
        StudentPW testStudentPW = studentPWList.get(studentPWList.size() - 1);
        assertThat(testStudentPW.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testStudentPW.getImageFront()).isEqualTo(DEFAULT_IMAGE_FRONT);
        assertThat(testStudentPW.getImageFrontContentType()).isEqualTo(DEFAULT_IMAGE_FRONT_CONTENT_TYPE);
        assertThat(testStudentPW.getImageSide()).isEqualTo(UPDATED_IMAGE_SIDE);
        assertThat(testStudentPW.getImageSideContentType()).isEqualTo(UPDATED_IMAGE_SIDE_CONTENT_TYPE);
        assertThat(testStudentPW.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStudentPW.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testStudentPW.geta1ImageSide()).isEqualTo(UPDATED_A_1_IMAGE_SIDE);
        assertThat(testStudentPW.geta2ImageSide()).isEqualTo(UPDATED_A_2_IMAGE_SIDE);
        assertThat(testStudentPW.geta3ImageSide()).isEqualTo(DEFAULT_A_3_IMAGE_SIDE);
        assertThat(testStudentPW.getp1ImageSide()).isEqualTo(DEFAULT_P_1_IMAGE_SIDE);
        assertThat(testStudentPW.getp2ImageSide()).isEqualTo(DEFAULT_P_2_IMAGE_SIDE);
        assertThat(testStudentPW.getp3ImageSide()).isEqualTo(UPDATED_P_3_IMAGE_SIDE);
        assertThat(testStudentPW.geta1ImageFront()).isEqualTo(UPDATED_A_1_IMAGE_FRONT);
        assertThat(testStudentPW.geta2ImageFront()).isEqualTo(UPDATED_A_2_IMAGE_FRONT);
        assertThat(testStudentPW.geta3ImageFront()).isEqualTo(DEFAULT_A_3_IMAGE_FRONT);
        assertThat(testStudentPW.getp1ImageFront()).isEqualTo(UPDATED_P_1_IMAGE_FRONT);
        assertThat(testStudentPW.getp2ImageFront()).isEqualTo(UPDATED_P_2_IMAGE_FRONT);
        assertThat(testStudentPW.getp3ImageFront()).isEqualTo(UPDATED_P_3_IMAGE_FRONT);
    }

    @Test
    @Transactional
    void fullUpdateStudentPWWithPatch() throws Exception {
        // Initialize the database
        studentPWRepository.saveAndFlush(studentPW);

        int databaseSizeBeforeUpdate = studentPWRepository.findAll().size();

        // Update the studentPW using partial update
        StudentPW partialUpdatedStudentPW = new StudentPW();
        partialUpdatedStudentPW.setId(studentPW.getId());

        partialUpdatedStudentPW
            .time(UPDATED_TIME)
            .imageFront(UPDATED_IMAGE_FRONT)
            .imageFrontContentType(UPDATED_IMAGE_FRONT_CONTENT_TYPE)
            .imageSide(UPDATED_IMAGE_SIDE)
            .imageSideContentType(UPDATED_IMAGE_SIDE_CONTENT_TYPE)
            .date(UPDATED_DATE)
            .note(UPDATED_NOTE)
            .a1ImageSide(UPDATED_A_1_IMAGE_SIDE)
            .a2ImageSide(UPDATED_A_2_IMAGE_SIDE)
            .a3ImageSide(UPDATED_A_3_IMAGE_SIDE)
            .p1ImageSide(UPDATED_P_1_IMAGE_SIDE)
            .p2ImageSide(UPDATED_P_2_IMAGE_SIDE)
            .p3ImageSide(UPDATED_P_3_IMAGE_SIDE)
            .a1ImageFront(UPDATED_A_1_IMAGE_FRONT)
            .a2ImageFront(UPDATED_A_2_IMAGE_FRONT)
            .a3ImageFront(UPDATED_A_3_IMAGE_FRONT)
            .p1ImageFront(UPDATED_P_1_IMAGE_FRONT)
            .p2ImageFront(UPDATED_P_2_IMAGE_FRONT)
            .p3ImageFront(UPDATED_P_3_IMAGE_FRONT);

        restStudentPWMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudentPW.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudentPW))
            )
            .andExpect(status().isOk());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeUpdate);
        StudentPW testStudentPW = studentPWList.get(studentPWList.size() - 1);
        assertThat(testStudentPW.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testStudentPW.getImageFront()).isEqualTo(UPDATED_IMAGE_FRONT);
        assertThat(testStudentPW.getImageFrontContentType()).isEqualTo(UPDATED_IMAGE_FRONT_CONTENT_TYPE);
        assertThat(testStudentPW.getImageSide()).isEqualTo(UPDATED_IMAGE_SIDE);
        assertThat(testStudentPW.getImageSideContentType()).isEqualTo(UPDATED_IMAGE_SIDE_CONTENT_TYPE);
        assertThat(testStudentPW.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStudentPW.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testStudentPW.geta1ImageSide()).isEqualTo(UPDATED_A_1_IMAGE_SIDE);
        assertThat(testStudentPW.geta2ImageSide()).isEqualTo(UPDATED_A_2_IMAGE_SIDE);
        assertThat(testStudentPW.geta3ImageSide()).isEqualTo(UPDATED_A_3_IMAGE_SIDE);
        assertThat(testStudentPW.getp1ImageSide()).isEqualTo(UPDATED_P_1_IMAGE_SIDE);
        assertThat(testStudentPW.getp2ImageSide()).isEqualTo(UPDATED_P_2_IMAGE_SIDE);
        assertThat(testStudentPW.getp3ImageSide()).isEqualTo(UPDATED_P_3_IMAGE_SIDE);
        assertThat(testStudentPW.geta1ImageFront()).isEqualTo(UPDATED_A_1_IMAGE_FRONT);
        assertThat(testStudentPW.geta2ImageFront()).isEqualTo(UPDATED_A_2_IMAGE_FRONT);
        assertThat(testStudentPW.geta3ImageFront()).isEqualTo(UPDATED_A_3_IMAGE_FRONT);
        assertThat(testStudentPW.getp1ImageFront()).isEqualTo(UPDATED_P_1_IMAGE_FRONT);
        assertThat(testStudentPW.getp2ImageFront()).isEqualTo(UPDATED_P_2_IMAGE_FRONT);
        assertThat(testStudentPW.getp3ImageFront()).isEqualTo(UPDATED_P_3_IMAGE_FRONT);
    }

    @Test
    @Transactional
    void patchNonExistingStudentPW() throws Exception {
        int databaseSizeBeforeUpdate = studentPWRepository.findAll().size();
        studentPW.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentPWMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentPW.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentPW))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudentPW() throws Exception {
        int databaseSizeBeforeUpdate = studentPWRepository.findAll().size();
        studentPW.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentPWMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentPW))
            )
            .andExpect(status().isBadRequest());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudentPW() throws Exception {
        int databaseSizeBeforeUpdate = studentPWRepository.findAll().size();
        studentPW.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentPWMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(studentPW))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StudentPW in the database
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudentPW() throws Exception {
        // Initialize the database
        studentPWRepository.saveAndFlush(studentPW);

        int databaseSizeBeforeDelete = studentPWRepository.findAll().size();

        // Delete the studentPW
        restStudentPWMockMvc
            .perform(delete(ENTITY_API_URL_ID, studentPW.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentPW> studentPWList = studentPWRepository.findAll();
        assertThat(studentPWList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
