package com.a00n.repository;

import com.a00n.domain.PW;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PWRepositoryWithBagRelationshipsImpl implements PWRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PW> fetchBagRelationships(Optional<PW> pW) {
        return pW.map(this::fetchGroupes);
    }

    @Override
    public Page<PW> fetchBagRelationships(Page<PW> pWS) {
        return new PageImpl<>(fetchBagRelationships(pWS.getContent()), pWS.getPageable(), pWS.getTotalElements());
    }

    @Override
    public List<PW> fetchBagRelationships(List<PW> pWS) {
        return Optional.of(pWS).map(this::fetchGroupes).orElse(Collections.emptyList());
    }

    PW fetchGroupes(PW result) {
        return entityManager
            .createQuery("select pW from PW pW left join fetch pW.groupes where pW.id = :id", PW.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<PW> fetchGroupes(List<PW> pWS) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, pWS.size()).forEach(index -> order.put(pWS.get(index).getId(), index));
        List<PW> result = entityManager
            .createQuery("select pW from PW pW left join fetch pW.groupes where pW in :pWS", PW.class)
            .setParameter("pWS", pWS)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
