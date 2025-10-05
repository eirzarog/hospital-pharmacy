package org.eirzarog.pharmacy.repositories;

import org.eirzarog.pharmacy.entities.Drug;
import org.eirzarog.pharmacy.entities.DrugCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long> {
    // ============================================
    // Basic Queries
    // ============================================

    Optional<Drug> findByCode(String code);

    boolean existsByCode(String code);

    List<Drug> findByCategory(DrugCategory category);

    Page<Drug> findByCategory(DrugCategory category, Pageable pageable);

    // ============================================
    // Stock Queries
    // ============================================

    List<Drug> findByStockLessThan(Integer threshold);

    @Query("SELECT d FROM Drug d WHERE d.stock = 0")
    List<Drug> findOutOfStockDrugs();

    @Query("SELECT d FROM Drug d WHERE d.stock < :threshold ORDER BY d.stock ASC")
    Page<Drug> findLowStockDrugs(@Param("threshold") Integer threshold, Pageable pageable);

    @Query("SELECT d FROM Drug d WHERE d.stock BETWEEN :min AND :max")
    List<Drug> findByStockBetween(@Param("min") Integer min, @Param("max") Integer max);

    // ============================================
    // Price Queries
    // ============================================

    @Query("SELECT d FROM Drug d WHERE d.price BETWEEN :minPrice AND :maxPrice")
    List<Drug> findByPriceBetween(@Param("minPrice") Double minPrice,
                                  @Param("maxPrice") Double maxPrice);

    @Query("SELECT d FROM Drug d ORDER BY d.price DESC")
    Page<Drug> findMostExpensiveDrugs(Pageable pageable);

    @Query("SELECT d FROM Drug d ORDER BY (d.price * d.stock) DESC")
    Page<Drug> findHighestValueDrugs(Pageable pageable);

    // ============================================
    // Search Queries
    // ============================================

    @Query("SELECT d FROM Drug d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.code) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.manufacturer) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Drug> searchDrugs(@Param("keyword") String keyword);

    @Query("SELECT d FROM Drug d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.code) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.manufacturer) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Drug> searchDrugs(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT d FROM Drug d WHERE d.category.id = :categoryId AND " +
            "(LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.code) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Drug> searchDrugsInCategory(@Param("keyword") String keyword,
                                     @Param("categoryId") Long categoryId);

    // ============================================
    // Manufacturer Queries
    // ============================================

    List<Drug> findByManufacturer(String manufacturer);

    @Query("SELECT DISTINCT d.manufacturer FROM Drug d WHERE d.manufacturer IS NOT NULL " +
            "ORDER BY d.manufacturer")
    List<String> findAllManufacturers();

    @Query("SELECT d.manufacturer, COUNT(d) FROM Drug d " +
            "WHERE d.manufacturer IS NOT NULL " +
            "GROUP BY d.manufacturer " +
            "ORDER BY COUNT(d) DESC")
    List<Object[]> countDrugsByManufacturer();

    // ============================================
    // Statistics Queries
    // ============================================

    @Query("SELECT COUNT(d) FROM Drug d")
    long countAllDrugs();

    @Query("SELECT COUNT(d) FROM Drug d WHERE d.stock < :threshold")
    long countLowStockDrugs(@Param("threshold") Integer threshold);

    @Query("SELECT COUNT(d) FROM Drug d WHERE d.stock = 0")
    long countOutOfStockDrugs();

    @Query("SELECT SUM(d.stock) FROM Drug d")
    Long getTotalStockQuantity();

    @Query("SELECT SUM(d.stock * d.price) FROM Drug d")
    Double getTotalStockValue();

    @Query("SELECT AVG(d.price) FROM Drug d")
    Double getAveragePrice();

    @Query("SELECT AVG(d.stock) FROM Drug d")
    Double getAverageStock();

    // ============================================
    // Category Statistics
    // ============================================

    @Query("SELECT d.category.name, COUNT(d), SUM(d.stock), SUM(d.stock * d.price) " +
            "FROM Drug d " +
            "GROUP BY d.category.id, d.category.name " +
            "ORDER BY SUM(d.stock * d.price) DESC")
    List<Object[]> getStockValueByCategory();

    @Query("SELECT d.category.name, COUNT(d) " +
            "FROM Drug d " +
            "GROUP BY d.category.id, d.category.name " +
            "ORDER BY COUNT(d) DESC")
    List<Object[]> countDrugsByCategory();

    @Query("SELECT d FROM Drug d WHERE d.category.id = :categoryId AND d.stock < :threshold")
    List<Drug> findLowStockDrugsByCategory(@Param("categoryId") Long categoryId,
                                           @Param("threshold") Integer threshold);


}
