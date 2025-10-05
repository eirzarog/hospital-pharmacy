package org.eirzarog.pharmacy.repositories;

import org.eirzarog.pharmacy.entities.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {


    @Query("SELECT sm FROM StockMovement sm " +
            "WHERE (:from IS NULL OR sm.createdAt >= :from) AND " +
            "      (:to IS NULL OR sm.createdAt <= :to)")
    List<StockMovement> findAllStockMovementsByDateRange(@Param("from") Instant from,
                                                        @Param("to") Instant to);


    @Query("SELECT sm FROM StockMovement sm WHERE sm.drug.id = :drugId")
    List<StockMovement> findByDrugId(@Param("drugId") Long drugId);

    @Query("SELECT sm FROM StockMovement sm " +
            "WHERE sm.drug.id = :drugId AND sm.movementType = 'OUTBOUND' " +
            "AND sm.createdAt >= :from AND sm.createdAt <= :to")
    List<StockMovement> findOutboundByDrugAndDateRange(@Param("drugId") Long drugId,
                                                       @Param("from") Instant from,
                                                       @Param("to") Instant to);

    @Query("SELECT sm FROM StockMovement sm " +
            "WHERE sm.drug.id = :drugId AND sm.movementType = 'INBOUND' " +
            "AND sm.createdAt >= :from AND sm.createdAt <= :to")
    List<StockMovement> findInboundByDrugAndDateRange(@Param("drugId") Long drugId,
                                                      @Param("from") Instant from,
                                                      @Param("to") Instant to);

    @Query("SELECT sm FROM StockMovement sm " +
            "WHERE sm.drug.id IN :drugIds AND sm.movementType = 'OUTBOUND' " +
            "AND sm.createdAt >= :from AND sm.createdAt <= :to")
    List<StockMovement> findOutboundByDrugsAndDateRange(@Param("drugIds") List<Long> drugIds,
                                                        @Param("from") Instant from,
                                                        @Param("to") Instant to);


}
