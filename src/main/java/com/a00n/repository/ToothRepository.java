package com.a00n.repository;

import com.a00n.domain.Tooth;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tooth entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToothRepository extends JpaRepository<Tooth, Long> {}
