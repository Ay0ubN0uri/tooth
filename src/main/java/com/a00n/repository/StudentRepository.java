package com.a00n.repository;

import com.a00n.domain.Student;
import com.a00n.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Student entity.
 *
 * When extending this class, extend StudentRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface StudentRepository extends StudentRepositoryWithBagRelationships, JpaRepository<Student, Long> {
    default Optional<Student> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Student> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Student> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select student from Student student left join fetch student.user",
        countQuery = "select count(student) from Student student"
    )
    Page<Student> findAllWithToOneRelationships(Pageable pageable);

    @Query("select student from Student student left join fetch student.user")
    List<Student> findAllWithToOneRelationships();

    @Query("select student from Student student left join fetch student.user where student.id =:id")
    Optional<Student> findOneWithToOneRelationships(@Param("id") Long id);

    Optional<Student> findByUser(User user);
}
