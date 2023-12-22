package com.a00n.repository;

import com.a00n.domain.AcademicYear;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AcademicYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {}
