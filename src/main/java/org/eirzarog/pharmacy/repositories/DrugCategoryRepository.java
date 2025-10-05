package org.eirzarog.pharmacy.repositories;

import org.eirzarog.pharmacy.entities.DrugCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DrugCategoryRepository extends JpaRepository<DrugCategory, Long> {

    boolean existsByName(String name);

    Optional<DrugCategory> findByName(String name);

    @Query("SELECT d FROM Drug d WHERE d.code = :code")
    Optional<DrugCategory> findByCode(String code);

    @Query("SELECT c FROM DrugCategory c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<DrugCategory> searchCategories(@Param("keyword") String keyword);
}
