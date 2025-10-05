package org.eirzarog.pharmacy.services;

import jakarta.validation.Valid;
import org.eirzarog.pharmacy.entities.dtos.CreateStockMovementRequest;
import org.eirzarog.pharmacy.entities.dtos.StockMovementDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockMovementService {

    public StockMovementDTO createStockMovement(@Valid CreateStockMovementRequest request) {
        return null;
    }

    public List<StockMovementDTO> getAllStockMovements() {
        return null;
    }

    public List<StockMovementDTO> getStockMovementsByDrug(Long drugId) {
        return null;
    }

    public List<StockMovementDTO> getOutboundStockMovementsByDrugAndDateRange(Long drugId, LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    public List<StockMovementDTO> getOutboundStockMovementsByDrugsAndDateRange(List<Long> drugIds, LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    public List<StockMovementDTO> getStockMovementsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }
}
