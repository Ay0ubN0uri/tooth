package com.a00n.repository;

import com.a00n.domain.StudentPW;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StudentPW entity.
 */
@Repository
public interface StudentPWRepository extends JpaRepository<StudentPW, Long> {
    default Optional<StudentPW> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<StudentPW> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<StudentPW> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select studentPW from StudentPW studentPW left join fetch studentPW.pw left join fetch studentPW.academicYear",
        countQuery = "select count(studentPW) from StudentPW studentPW"
    )
    Page<StudentPW> findAllWithToOneRelationships(Pageable pageable);

    @Query("select studentPW from StudentPW studentPW left join fetch studentPW.pw left join fetch studentPW.academicYear")
    List<StudentPW> findAllWithToOneRelationships();

    @Query(
        "select studentPW from StudentPW studentPW left join fetch studentPW.pw left join fetch studentPW.academicYear where studentPW.id =:id"
    )
    Optional<StudentPW> findOneWithToOneRelationships(@Param("id") Long id);

    List<StudentPW> findByStudentId(Long id);
}
