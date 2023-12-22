package com.a00n.web.rest;

import com.a00n.domain.Tooth;
import com.a00n.repository.ToothRepository;
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
 * REST controller for managing {@link com.a00n.domain.Tooth}.
 */
@RestController
@RequestMapping("/api/teeth")
@Transactional
public class ToothResource {

    private final Logger log = LoggerFactory.getLogger(ToothResource.class);

    private static final String ENTITY_NAME = "tooth";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ToothRepository toothRepository;

    public ToothResource(ToothRepository toothRepository) {
        this.toothRepository = toothRepository;
    }

    /**
     * {@code POST  /teeth} : Create a new tooth.
     *
     * @param tooth the tooth to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tooth, or with status {@code 400 (Bad Request)} if the tooth has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tooth> createTooth(@RequestBody Tooth tooth) throws URISyntaxException {
        log.debug("REST request to save Tooth : {}", tooth);
        if (tooth.getId() != null) {
            throw new BadRequestAlertException("A new tooth cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tooth result = toothRepository.save(tooth);
        return ResponseEntity
            .created(new URI("/api/teeth/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teeth/:id} : Updates an existing tooth.
     *
     * @param id the id of the tooth to save.
     * @param tooth the tooth to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tooth,
     * or with status {@code 400 (Bad Request)} if the tooth is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tooth couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tooth> updateTooth(@PathVariable(value = "id", required = false) final Long id, @RequestBody Tooth tooth)
        throws URISyntaxException {
        log.debug("REST request to update Tooth : {}, {}", id, tooth);
        if (tooth.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tooth.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!toothRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tooth result = toothRepository.save(tooth);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tooth.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /teeth/:id} : Partial updates given fields of an existing tooth, field will ignore if it is null
     *
     * @param id the id of the tooth to save.
     * @param tooth the tooth to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tooth,
     * or with status {@code 400 (Bad Request)} if the tooth is not valid,
     * or with status {@code 404 (Not Found)} if the tooth is not found,
     * or with status {@code 500 (Internal Server Error)} if the tooth couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tooth> partialUpdateTooth(@PathVariable(value = "id", required = false) final Long id, @RequestBody Tooth tooth)
        throws URISyntaxException {
        log.debug("REST request to partial update Tooth partially : {}, {}", id, tooth);
        if (tooth.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tooth.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!toothRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tooth> result = toothRepository
            .findById(tooth.getId())
            .map(existingTooth -> {
                if (tooth.getName() != null) {
                    existingTooth.setName(tooth.getName());
                }

                return existingTooth;
            })
            .map(toothRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tooth.getId().toString())
        );
    }

    /**
     * {@code GET  /teeth} : get all the teeth.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teeth in body.
     */
    @GetMapping("")
    public List<Tooth> getAllTeeth() {
        log.debug("REST request to get all Teeth");
        return toothRepository.findAll();
    }

    /**
     * {@code GET  /teeth/:id} : get the "id" tooth.
     *
     * @param id the id of the tooth to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tooth, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tooth> getTooth(@PathVariable("id") Long id) {
        log.debug("REST request to get Tooth : {}", id);
        Optional<Tooth> tooth = toothRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tooth);
    }

    /**
     * {@code DELETE  /teeth/:id} : delete the "id" tooth.
     *
     * @param id the id of the tooth to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTooth(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tooth : {}", id);
        toothRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
