package org.eirzarog.pharmacy.repositories;

import org.eirzarog.pharmacy.entities.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

     // ============================================
    // Date Range Queries
    // ============================================

    List<StockMovement> findByMovementDateBetween(Instant movementDate, Instant movementDate2);



    @Query("SELECT sm FROM StockMovement sm WHERE sm.drug.id = :drugId " +
            "AND sm.movementType = :movementType " +
            "AND sm.movementDate BETWEEN :startDate AND :endDate " +
            "ORDER BY sm.movementDate DESC")
    List<StockMovement> findMovementsByDrugAndTypeAndDateRange(
            @Param("drugId") Long drugId,
            @Param("movementType") String movementType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT sm FROM StockMovement sm WHERE sm.drug.id IN :drugIds " +
            "AND sm.movementType = :movementType " +
            "AND sm.movementDate BETWEEN :startDate AND :endDate " +
            "ORDER BY sm.movementDate DESC")
    List<StockMovement> findOutboundMovementsByDrugsAndDateRange(
            @Param("drugIds") List<Long> drugIds,
            @Param("movementType") String movementType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT sm FROM StockMovement sm WHERE sm.drug.category.id = :categoryId " +
            "AND sm.movementDate BETWEEN :startDate AND :endDate " +
            "ORDER BY sm.movementDate DESC")
    List<StockMovement> findMovementsByCategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // ============================================
    // Statistics Queries
    // ============================================

    @Query("SELECT COUNT(sm) FROM StockMovement sm WHERE sm.movementType = :type")
    long countByMovementType(@Param("type") String type);

    @Query("SELECT COUNT(sm) FROM StockMovement sm WHERE sm.drug.id = :drugId")
    long countMovementsByDrug(@Param("drugId") Long drugId);

    @Query("SELECT SUM(sm.quantity) FROM StockMovement sm " +
            "WHERE sm.drug.id = :drugId AND sm.movementType = :type")
    Long getTotalQuantityByDrugAndType(@Param("drugId") Long drugId,
                                       @Param("type") String type);

    @Query("SELECT SUM(sm.quantity) FROM StockMovement sm WHERE sm.movementType = :type")
    Long getTotalQuantityByType(@Param("type") String type);

    @Query("SELECT SUM(sm.quantity) FROM StockMovement sm " +
            "WHERE sm.movementType = :type " +
            "AND sm.movementDate BETWEEN :startDate AND :endDate")
    Long getTotalQuantityByTypeAndDateRange(@Param("type") String type,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);



    // ============================================
    // Grouped Statistics
    // ============================================

    @Query("SELECT sm.drug.category.name, sm.movementType, COUNT(sm), SUM(sm.quantity) " +
            "FROM StockMovement sm " +
            "WHERE sm.movementDate BETWEEN :startDate AND :endDate " +
            "GROUP BY sm.drug.category.id, sm.drug.category.name, sm.movementType " +
            "ORDER BY sm.drug.category.name, sm.movementType")
    List<Object[]> getMovementStatsByCategory(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

    @Query("SELECT FUNCTION('DATE', sm.movementDate), sm.movementType, " +
            "COUNT(sm), SUM(sm.quantity) " +
            "FROM StockMovement sm " +
            "WHERE sm.movementDate BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('DATE', sm.movementDate), sm.movementType " +
            "ORDER BY FUNCTION('DATE', sm.movementDate) DESC")
    List<Object[]> getDailyMovementStats(@Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);

    @Query("SELECT FUNCTION('YEAR', sm.movementDate), FUNCTION('MONTH', sm.movementDate), " +
            "sm.movementType, COUNT(sm), SUM(sm.quantity) " +
            "FROM StockMovement sm " +
            "WHERE sm.movementDate BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('YEAR', sm.movementDate), " +
            "FUNCTION('MONTH', sm.movementDate), sm.movementType " +
            "ORDER BY FUNCTION('YEAR', sm.movementDate) DESC, " +
            "FUNCTION('MONTH', sm.movementDate) DESC")
    List<Object[]> getMonthlyMovementStats(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    // ============================================
    // Last Movement Queries
    // ============================================

    @Query("SELECT sm FROM StockMovement sm WHERE sm.drug.id = :drugId " +
            "ORDER BY sm.movementDate DESC LIMIT 1")
    StockMovement findLastMovementByDrug(@Param("drugId") Long drugId);

    @Query("SELECT MAX(sm.movementDate) FROM StockMovement sm WHERE sm.drug.id = :drugId")
    LocalDateTime findLastMovementDateByDrug(@Param("drugId") Long drugId);
}
