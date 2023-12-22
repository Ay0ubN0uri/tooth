package com.a00n.repository;

import com.a00n.domain.PW;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PWRepositoryWithBagRelationships {
    Optional<PW> fetchBagRelationships(Optional<PW> pW);

    List<PW> fetchBagRelationships(List<PW> pWS);

    Page<PW> fetchBagRelationships(Page<PW> pWS);
}
