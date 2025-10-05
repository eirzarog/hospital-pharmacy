package org.eirzarog.pharmacy.repositories;

import org.eirzarog.pharmacy.entities.DrugCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DrugCategoryRepository extends JpaRepository<DrugCategory, Long> {

    // ============================================
    // Basic Queries
    // ============================================

    Optional<DrugCategory> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT c FROM DrugCategory c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<DrugCategory> searchCategories(@Param("keyword") String keyword);

    // ============================================
    // Statistics Queries
    // ============================================

//    @Query("SELECT c.id, c.name, COUNT(d), SUM(d.stock), SUM(d.stock * d.price) " +
//            "FROM DrugCategory c LEFT JOIN c.id d " +
//            "GROUP BY c.id, c.name " +
//            "ORDER BY c.name")
//    List<Object[]> getCategoriesWithStats();
//
//    @Query("SELECT c FROM DrugCategory c " +
//            "LEFT JOIN c.id d " +
//            "GROUP BY c.id " +
//            "HAVING COUNT(d) > 0")
//    List<DrugCategory> findCategoriesWithDrugs();
//
//    @Query("SELECT c FROM DrugCategory c " +
//            "LEFT JOIN c.id d " +
//            "GROUP BY c.id " +
//            "HAVING COUNT(d) = 0")
//    List<DrugCategory> findEmptyCategories();

    @Query("SELECT COUNT(d) FROM Drug d WHERE d.category.id = :categoryId")
    long countDrugsInCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT SUM(d.stock) FROM Drug d WHERE d.category.id = :categoryId")
    Long getTotalStockInCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT SUM(d.stock * d.price) FROM Drug d WHERE d.category.id = :categoryId")
    Double getTotalValueInCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT COUNT(d) FROM Drug d WHERE d.category.id = :categoryId AND d.stock < :threshold")
    long countLowStockDrugsInCategory(@Param("categoryId") Long categoryId,
                                      @Param("threshold") Integer threshold);
}
