package org.eirzarog.pharmacy.repositories;

import org.eirzarog.pharmacy.entities.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long> {

    boolean existsByCode(String code);

    Optional<Drug> findByCode(String code);

    @Query("SELECT d FROM Drug d WHERE (:from IS NULL OR d.createdAt >= :from) AND (:to IS NULL OR d.createdAt <= :to)")
    List<Drug> findAllDrugsbyDateRange(@Param("from") Instant from, @Param("to") Instant to);
}
