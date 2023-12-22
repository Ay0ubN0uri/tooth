package com.a00n.repository;

import com.a00n.domain.Groupe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Groupe entity.
 */
@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
    default Optional<Groupe> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Groupe> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Groupe> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select groupe from Groupe groupe left join fetch groupe.academicYear",
        countQuery = "select count(groupe) from Groupe groupe"
    )
    Page<Groupe> findAllWithToOneRelationships(Pageable pageable);

    @Query("select groupe from Groupe groupe left join fetch groupe.academicYear")
    List<Groupe> findAllWithToOneRelationships();

    @Query("select groupe from Groupe groupe left join fetch groupe.academicYear where groupe.id =:id")
    Optional<Groupe> findOneWithToOneRelationships(@Param("id") Long id);
}
