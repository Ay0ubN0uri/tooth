package com.a00n.web.rest;

import com.a00n.domain.AcademicYear;
import com.a00n.repository.AcademicYearRepository;
import com.a00n.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.a00n.domain.AcademicYear}.
 */
@RestController
@RequestMapping("/api/academic-years")
@Transactional
public class AcademicYearResource {

    private final Logger log = LoggerFactory.getLogger(AcademicYearResource.class);

    private static final String ENTITY_NAME = "academicYear";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicYearRepository academicYearRepository;

    public AcademicYearResource(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    /**
     * {@code POST  /academic-years} : Create a new academicYear.
     *
     * @param academicYear the academicYear to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicYear, or with status {@code 400 (Bad Request)} if the academicYear has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AcademicYear> createAcademicYear(@RequestBody AcademicYear academicYear) throws URISyntaxException {
        log.debug("REST request to save AcademicYear : {}", academicYear);
        if (academicYear.getId() != null) {
            throw new BadRequestAlertException("A new academicYear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicYear result = academicYearRepository.save(academicYear);
        return ResponseEntity
            .created(new URI("/api/academic-years/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academic-years/:id} : Updates an existing academicYear.
     *
     * @param id the id of the academicYear to save.
     * @param academicYear the academicYear to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicYear,
     * or with status {@code 400 (Bad Request)} if the academicYear is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicYear couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AcademicYear> updateAcademicYear(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcademicYear academicYear
    ) throws URISyntaxException {
        log.debug("REST request to update AcademicYear : {}, {}", id, academicYear);
        if (academicYear.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicYear.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicYearRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AcademicYear result = academicYearRepository.save(academicYear);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicYear.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /academic-years/:id} : Partial updates given fields of an existing academicYear, field will ignore if it is null
     *
     * @param id the id of the academicYear to save.
     * @param academicYear the academicYear to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicYear,
     * or with status {@code 400 (Bad Request)} if the academicYear is not valid,
     * or with status {@code 404 (Not Found)} if the academicYear is not found,
     * or with status {@code 500 (Internal Server Error)} if the academicYear couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AcademicYear> partialUpdateAcademicYear(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcademicYear academicYear
    ) throws URISyntaxException {
        log.debug("REST request to partial update AcademicYear partially : {}, {}", id, academicYear);
        if (academicYear.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicYear.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicYearRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcademicYear> result = academicYearRepository
            .findById(academicYear.getId())
            .map(existingAcademicYear -> {
                if (academicYear.getYear() != null) {
                    existingAcademicYear.setYear(academicYear.getYear());
                }
                if (academicYear.getStartingDate() != null) {
                    existingAcademicYear.setStartingDate(academicYear.getStartingDate());
                }
                if (academicYear.getEndingDate() != null) {
                    existingAcademicYear.setEndingDate(academicYear.getEndingDate());
                }

                return existingAcademicYear;
            })
            .map(academicYearRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicYear.getId().toString())
        );
    }

    /**
     * {@code GET  /academic-years} : get all the academicYears.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicYears in body.
     */
    @GetMapping("")
    public List<AcademicYear> getAllAcademicYears() {
        log.debug("REST request to get all AcademicYears");
        return academicYearRepository.findAll();
    }

    /**
     * {@code GET  /academic-years/:id} : get the "id" academicYear.
     *
     * @param id the id of the academicYear to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicYear, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AcademicYear> getAcademicYear(@PathVariable("id") Long id) {
        log.debug("REST request to get AcademicYear : {}", id);
        Optional<AcademicYear> academicYear = academicYearRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(academicYear);
    }

    /**
     * {@code DELETE  /academic-years/:id} : delete the "id" academicYear.
     *
     * @param id the id of the academicYear to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicYear(@PathVariable("id") Long id) {
        log.debug("REST request to delete AcademicYear : {}", id);
        academicYearRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
