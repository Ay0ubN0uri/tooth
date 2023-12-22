package com.a00n.web.rest;

import com.a00n.domain.Groupe;
import com.a00n.repository.GroupeRepository;
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
 * REST controller for managing {@link com.a00n.domain.Groupe}.
 */
@RestController
@RequestMapping("/api/groupes")
@Transactional
public class GroupeResource {

    private final Logger log = LoggerFactory.getLogger(GroupeResource.class);

    private static final String ENTITY_NAME = "groupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupeRepository groupeRepository;

    public GroupeResource(GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }

    /**
     * {@code POST  /groupes} : Create a new groupe.
     *
     * @param groupe the groupe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupe, or with status {@code 400 (Bad Request)} if the groupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Groupe> createGroupe(@Valid @RequestBody Groupe groupe) throws URISyntaxException {
        log.debug("REST request to save Groupe : {}", groupe);
        if (groupe.getId() != null) {
            throw new BadRequestAlertException("A new groupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Groupe result = groupeRepository.save(groupe);
        return ResponseEntity
            .created(new URI("/api/groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /groupes/:id} : Updates an existing groupe.
     *
     * @param id the id of the groupe to save.
     * @param groupe the groupe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupe,
     * or with status {@code 400 (Bad Request)} if the groupe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Groupe> updateGroupe(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Groupe groupe
    ) throws URISyntaxException {
        log.debug("REST request to update Groupe : {}, {}", id, groupe);
        if (groupe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Groupe result = groupeRepository.save(groupe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /groupes/:id} : Partial updates given fields of an existing groupe, field will ignore if it is null
     *
     * @param id the id of the groupe to save.
     * @param groupe the groupe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupe,
     * or with status {@code 400 (Bad Request)} if the groupe is not valid,
     * or with status {@code 404 (Not Found)} if the groupe is not found,
     * or with status {@code 500 (Internal Server Error)} if the groupe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Groupe> partialUpdateGroupe(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Groupe groupe
    ) throws URISyntaxException {
        log.debug("REST request to partial update Groupe partially : {}, {}", id, groupe);
        if (groupe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Groupe> result = groupeRepository
            .findById(groupe.getId())
            .map(existingGroupe -> {
                if (groupe.getCode() != null) {
                    existingGroupe.setCode(groupe.getCode());
                }
                if (groupe.getYear() != null) {
                    existingGroupe.setYear(groupe.getYear());
                }

                return existingGroupe;
            })
            .map(groupeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupe.getId().toString())
        );
    }

    /**
     * {@code GET  /groupes} : get all the groupes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupes in body.
     */
    @GetMapping("")
    public List<Groupe> getAllGroupes(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Groupes");
        if (eagerload) {
            return groupeRepository.findAllWithEagerRelationships();
        } else {
            return groupeRepository.findAll();
        }
    }

    /**
     * {@code GET  /groupes/:id} : get the "id" groupe.
     *
     * @param id the id of the groupe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Groupe> getGroupe(@PathVariable("id") Long id) {
        log.debug("REST request to get Groupe : {}", id);
        Optional<Groupe> groupe = groupeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(groupe);
    }

    /**
     * {@code DELETE  /groupes/:id} : delete the "id" groupe.
     *
     * @param id the id of the groupe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable("id") Long id) {
        log.debug("REST request to delete Groupe : {}", id);
        groupeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
