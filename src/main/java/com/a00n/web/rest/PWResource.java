package com.a00n.web.rest;

import com.a00n.domain.PW;
import com.a00n.repository.PWRepository;
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
 * REST controller for managing {@link com.a00n.domain.PW}.
 */
@RestController
@RequestMapping("/api/pws")
@Transactional
public class PWResource {

    private final Logger log = LoggerFactory.getLogger(PWResource.class);

    private static final String ENTITY_NAME = "pW";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PWRepository pWRepository;

    public PWResource(PWRepository pWRepository) {
        this.pWRepository = pWRepository;
    }

    /**
     * {@code POST  /pws} : Create a new pW.
     *
     * @param pW the pW to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pW, or with status {@code 400 (Bad Request)} if the pW has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PW> createPW(@Valid @RequestBody PW pW) throws URISyntaxException {
        log.debug("REST request to save PW : {}", pW);
        if (pW.getId() != null) {
            throw new BadRequestAlertException("A new pW cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PW result = pWRepository.save(pW);
        return ResponseEntity
            .created(new URI("/api/pws/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pws/:id} : Updates an existing pW.
     *
     * @param id the id of the pW to save.
     * @param pW the pW to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pW,
     * or with status {@code 400 (Bad Request)} if the pW is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pW couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PW> updatePW(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody PW pW)
        throws URISyntaxException {
        log.debug("REST request to update PW : {}, {}", id, pW);
        if (pW.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pW.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pWRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PW result = pWRepository.save(pW);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pW.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pws/:id} : Partial updates given fields of an existing pW, field will ignore if it is null
     *
     * @param id the id of the pW to save.
     * @param pW the pW to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pW,
     * or with status {@code 400 (Bad Request)} if the pW is not valid,
     * or with status {@code 404 (Not Found)} if the pW is not found,
     * or with status {@code 500 (Internal Server Error)} if the pW couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PW> partialUpdatePW(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody PW pW)
        throws URISyntaxException {
        log.debug("REST request to partial update PW partially : {}, {}", id, pW);
        if (pW.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pW.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pWRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PW> result = pWRepository
            .findById(pW.getId())
            .map(existingPW -> {
                if (pW.getTitle() != null) {
                    existingPW.setTitle(pW.getTitle());
                }
                if (pW.getObjectif() != null) {
                    existingPW.setObjectif(pW.getObjectif());
                }
                if (pW.getDocs() != null) {
                    existingPW.setDocs(pW.getDocs());
                }
                if (pW.getDeadline() != null) {
                    existingPW.setDeadline(pW.getDeadline());
                }

                return existingPW;
            })
            .map(pWRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pW.getId().toString())
        );
    }

    /**
     * {@code GET  /pws} : get all the pWS.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pWS in body.
     */
    @GetMapping("")
    public List<PW> getAllPWS(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all PWS");
        if (eagerload) {
            return pWRepository.findAllWithEagerRelationships();
        } else {
            return pWRepository.findAll();
        }
    }

    /**
     * {@code GET  /pws/:id} : get the "id" pW.
     *
     * @param id the id of the pW to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pW, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PW> getPW(@PathVariable("id") Long id) {
        log.debug("REST request to get PW : {}", id);
        Optional<PW> pW = pWRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(pW);
    }

    /**
     * {@code DELETE  /pws/:id} : delete the "id" pW.
     *
     * @param id the id of the pW to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePW(@PathVariable("id") Long id) {
        log.debug("REST request to delete PW : {}", id);
        pWRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
