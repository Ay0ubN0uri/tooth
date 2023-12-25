package com.a00n.web.rest;

import com.a00n.domain.Student;
import com.a00n.domain.StudentPW;
import com.a00n.repository.StudentPWRepository;
import com.a00n.repository.StudentRepository;
import com.a00n.security.AuthoritiesConstants;
import com.a00n.service.UserService;
import com.a00n.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.a00n.domain.Student}.
 */
@RestController
@RequestMapping("/api/students")
@Transactional
public class StudentResource {

    private final Logger log = LoggerFactory.getLogger(StudentResource.class);

    private static final String ENTITY_NAME = "student";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentRepository studentRepository;
    private final StudentPWRepository studentPWRepository;
    private final UserService userService;

    public StudentResource(StudentRepository studentRepository, StudentPWRepository studentPWRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.studentPWRepository = studentPWRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /students} : Create a new student.
     *
     * @param student the student to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new student, or with status {@code 400 (Bad Request)} if the student has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) throws URISyntaxException {
        log.debug("REST request to save Student : {}", student);
        if (student.getId() != null) {
            throw new BadRequestAlertException("A new student cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Student result = studentRepository.save(student);
        return ResponseEntity
            .created(new URI("/api/students/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /students/:id} : Updates an existing student.
     *
     * @param id the id of the student to save.
     * @param student the student to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated student,
     * or with status {@code 400 (Bad Request)} if the student is not valid,
     * or with status {@code 500 (Internal Server Error)} if the student couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Student student
    ) throws URISyntaxException {
        log.debug("REST request to update Student : {}, {}", id, student);
        if (student.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, student.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Student result = studentRepository.save(student);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, student.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /students/:id} : Partial updates given fields of an existing student, field will ignore if it is null
     *
     * @param id the id of the student to save.
     * @param student the student to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated student,
     * or with status {@code 400 (Bad Request)} if the student is not valid,
     * or with status {@code 404 (Not Found)} if the student is not found,
     * or with status {@code 500 (Internal Server Error)} if the student couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Student> partialUpdateStudent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Student student
    ) throws URISyntaxException {
        log.debug("REST request to partial update Student partially : {}, {}", id, student);
        if (student.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, student.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Student> result = studentRepository
            .findById(student.getId())
            .map(existingStudent -> {
                if (student.getNumber() != null) {
                    existingStudent.setNumber(student.getNumber());
                }
                if (student.getCne() != null) {
                    existingStudent.setCne(student.getCne());
                }
                if (student.getCin() != null) {
                    existingStudent.setCin(student.getCin());
                }
                if (student.getBirthDay() != null) {
                    existingStudent.setBirthDay(student.getBirthDay());
                }

                return existingStudent;
            })
            .map(studentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, student.getId().toString())
        );
    }

    /**
     * {@code GET  /students} : get all the students.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of students in body.
     */
    @GetMapping("")
    public List<Student> getAllStudents(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Students");
        if (eagerload) {
            return studentRepository.findAllWithEagerRelationships();
        } else {
            return studentRepository.findAll();
        }
    }

    @GetMapping("/mystudents/{yearId}/{groupId}")
    public List<Student> getStudentsByAcademicYearAndGroup(@PathVariable("yearId") Long yearId, @PathVariable("groupId") Long groupId) {
        // System.out.println(yearId);
        // System.out.println(groupId);
        List<Student> students = new ArrayList<>();
        for (Student student : studentRepository.findByGroupIdAndAcademicYearId(groupId, yearId)) {
            var user = userService.getUserWithAuthoritiesByLogin(student.getUser().getLogin()).orElse(null);
            if (user != null) {
                // System.out.println(user.getAuthorities());
                // System.out.println(user.hasRole(AuthoritiesConstants.ADMIN));
                // System.out.println(user.hasRole(AuthoritiesConstants.PROFESSOR));
                if (!user.hasRole(AuthoritiesConstants.PROFESSOR) && !user.hasRole(AuthoritiesConstants.ADMIN)) {
                    students.add(student);
                }
            }
        }
        return students;
    }

    /**
     * {@code GET  /students/:id} : get the "id" student.
     *
     * @param id the id of the student to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the student, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long id) {
        log.debug("REST request to get Student : {}", id);
        Optional<Student> student = studentRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(student);
    }

    @GetMapping("/{id}/pws")
    public List<StudentPW> getStudentPWByStudent(@PathVariable("id") Long id) {
        log.debug("REST request to get StudentPW : {}", id);
        return studentPWRepository.findByStudentId(id);
    }

    /**
     * {@code DELETE  /students/:id} : delete the "id" student.
     *
     * @param id the id of the student to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        log.debug("REST request to delete Student : {}", id);
        studentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
