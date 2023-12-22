package com.a00n.repository;

import com.a00n.domain.PW;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PW entity.
 *
 * When extending this class, extend PWRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PWRepository extends PWRepositoryWithBagRelationships, JpaRepository<PW, Long> {
    default Optional<PW> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<PW> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<PW> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select pW from PW pW left join fetch pW.tooth left join fetch pW.academicYear",
        countQuery = "select count(pW) from PW pW"
    )
    Page<PW> findAllWithToOneRelationships(Pageable pageable);

    @Query("select pW from PW pW left join fetch pW.tooth left join fetch pW.academicYear")
    List<PW> findAllWithToOneRelationships();

    @Query("select pW from PW pW left join fetch pW.tooth left join fetch pW.academicYear where pW.id =:id")
    Optional<PW> findOneWithToOneRelationships(@Param("id") Long id);
}
