package com.a00n.web.rest;

import com.a00n.domain.StudentPW;
import com.a00n.repository.StudentPWRepository;
import com.a00n.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
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
 * REST controller for managing {@link com.a00n.domain.StudentPW}.
 */
@RestController
@RequestMapping("/api/student-pws")
@Transactional
public class StudentPWResource {

    private final Logger log = LoggerFactory.getLogger(StudentPWResource.class);

    private static final String ENTITY_NAME = "studentPW";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentPWRepository studentPWRepository;

    public StudentPWResource(StudentPWRepository studentPWRepository) {
        this.studentPWRepository = studentPWRepository;
    }

    /**
     * {@code POST  /student-pws} : Create a new studentPW.
     *
     * @param studentPW the studentPW to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentPW, or with status {@code 400 (Bad Request)} if the studentPW has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StudentPW> createStudentPW(@Valid @RequestBody StudentPW studentPW) throws URISyntaxException {
        log.debug("REST request to save StudentPW : {}", studentPW);
        if (studentPW.getId() != null) {
            throw new BadRequestAlertException("A new studentPW cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentPW result = studentPWRepository.save(studentPW);
        return ResponseEntity
            .created(new URI("/api/student-pws/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-pws/:id} : Updates an existing studentPW.
     *
     * @param id the id of the studentPW to save.
     * @param studentPW the studentPW to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentPW,
     * or with status {@code 400 (Bad Request)} if the studentPW is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentPW couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentPW> updateStudentPW(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StudentPW studentPW
    ) throws URISyntaxException {
        log.debug("REST request to update StudentPW : {}, {}", id, studentPW);
        if (studentPW.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentPW.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentPWRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StudentPW result = studentPWRepository.save(studentPW);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentPW.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /student-pws/:id} : Partial updates given fields of an existing studentPW, field will ignore if it is null
     *
     * @param id the id of the studentPW to save.
     * @param studentPW the studentPW to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentPW,
     * or with status {@code 400 (Bad Request)} if the studentPW is not valid,
     * or with status {@code 404 (Not Found)} if the studentPW is not found,
     * or with status {@code 500 (Internal Server Error)} if the studentPW couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StudentPW> partialUpdateStudentPW(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StudentPW studentPW
    ) throws URISyntaxException {
        log.debug("REST request to partial update StudentPW partially : {}, {}", id, studentPW);
        if (studentPW.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentPW.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentPWRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StudentPW> result = studentPWRepository
            .findById(studentPW.getId())
            .map(existingStudentPW -> {
                if (studentPW.getTime() != null) {
                    existingStudentPW.setTime(studentPW.getTime());
                }
                if (studentPW.getImageFront() != null) {
                    existingStudentPW.setImageFront(studentPW.getImageFront());
                }
                if (studentPW.getImageFrontContentType() != null) {
                    existingStudentPW.setImageFrontContentType(studentPW.getImageFrontContentType());
                }
                if (studentPW.getImageSide() != null) {
                    existingStudentPW.setImageSide(studentPW.getImageSide());
                }
                if (studentPW.getImageSideContentType() != null) {
                    existingStudentPW.setImageSideContentType(studentPW.getImageSideContentType());
                }
                if (studentPW.getDate() != null) {
                    existingStudentPW.setDate(studentPW.getDate());
                }

                return existingStudentPW;
            })
            .map(studentPWRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentPW.getId().toString())
        );
    }

    /**
     * {@code GET  /student-pws} : get all the studentPWS.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentPWS in body.
     */
    @GetMapping("")
    public List<StudentPW> getAllStudentPWS(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all StudentPWS");
        if (eagerload) {
            return studentPWRepository.findAllWithEagerRelationships();
        } else {
            return studentPWRepository.findAll();
        }
    }

    /**
     * {@code GET  /student-pws/:id} : get the "id" studentPW.
     *
     * @param id the id of the studentPW to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentPW, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentPW> getStudentPW(@PathVariable("id") Long id) {
        log.debug("REST request to get StudentPW : {}", id);
        Optional<StudentPW> studentPW = studentPWRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(studentPW);
    }

    /**
     * {@code DELETE  /student-pws/:id} : delete the "id" studentPW.
     *
     * @param id the id of the studentPW to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentPW(@PathVariable("id") Long id) {
        log.debug("REST request to delete StudentPW : {}", id);
        studentPWRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
