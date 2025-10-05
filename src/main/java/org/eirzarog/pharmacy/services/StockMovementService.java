package org.eirzarog.pharmacy.services;

import lombok.RequiredArgsConstructor;
import org.eirzarog.pharmacy.entities.Drug;
import org.eirzarog.pharmacy.entities.Staff;
import org.eirzarog.pharmacy.entities.StockMovement;
import org.eirzarog.pharmacy.entities.dtos.StockMovementDTO;
import org.eirzarog.pharmacy.repositories.DrugRepository;
import org.eirzarog.pharmacy.repositories.StaffRepository;
import org.eirzarog.pharmacy.repositories.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final DrugRepository drugRepository;
    private final StaffRepository staffRepository;

    public StockMovementDTO createStockMovement(StockMovementDTO request) {
        Drug drug = drugRepository.findById(request.getDrugId())
                .orElseThrow(() -> new RuntimeException("Drug not found"));

        int newStock = drug.getStock();

        if ("INBOUND".equalsIgnoreCase(request.getMovementType())) {
            newStock += request.getQuantity();
        } else if ("OUTBOUND".equalsIgnoreCase(request.getMovementType())) {
            if (request.getQuantity() > newStock) {
                throw new RuntimeException("Not enough stock to export");
            }
            newStock -= request.getQuantity();
        }

        // Update the stock value of the drug
        drug.setStock(newStock);
        drugRepository.save(drug);

        // Load staff
        Staff staff = staffRepository.findById(request.getPerformedBy())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        // Save stock movement
        StockMovement movement = new StockMovement();
        movement.setDrug(drug);
        movement.setPerformedBy(staff);
        movement.setMovementType(request.getMovementType());
        movement.setQuantity(request.getQuantity());
        StockMovement saved = stockMovementRepository.save(movement);

        // Convert to DTO
        StockMovementDTO response = new StockMovementDTO();
        response.setId(saved.getId());
        response.setDrugId(saved.getDrug().getId());
        response.setPerformedBy(saved.getPerformedBy().getId());
        response.setMovementType(saved.getMovementType());
        response.setQuantity(saved.getQuantity());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }


    public List<StockMovementDTO> getStockMovementsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Instant from = startDate.toInstant(ZoneOffset.UTC);
        Instant to = endDate.toInstant(ZoneOffset.UTC);
        return stockMovementRepository.findAllStockMovementsByDateRange(from, to)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<StockMovementDTO> getAllStockMovements() {
        return stockMovementRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<StockMovementDTO> getStockMovementsByDrug(Long drugId) {
        return stockMovementRepository.findByDrugId(drugId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<StockMovementDTO> getOutboundStockMovementsByDrugAndDateRange(Long drugId, LocalDateTime startDate, LocalDateTime endDate) {
        Instant from = startDate.toInstant(ZoneOffset.UTC);
        Instant to = endDate.toInstant(ZoneOffset.UTC);
        return stockMovementRepository.findOutboundByDrugAndDateRange(drugId, from, to)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<StockMovementDTO> getInboundStockMovementsByDrugAndDateRange(Long drugId, LocalDateTime startDate, LocalDateTime endDate) {
        Instant from = startDate.toInstant(ZoneOffset.UTC);
        Instant to = endDate.toInstant(ZoneOffset.UTC);
        return stockMovementRepository.findInboundByDrugAndDateRange(drugId, from, to)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<StockMovementDTO> getOutboundStockMovementsByDrugsAndDateRange(List<Long> drugIds, LocalDateTime startDate, LocalDateTime endDate) {
        Instant from = startDate.toInstant(ZoneOffset.UTC);
        Instant to = endDate.toInstant(ZoneOffset.UTC);
        return stockMovementRepository.findOutboundByDrugsAndDateRange(drugIds, from, to)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }



    private StockMovementDTO toDTO(StockMovement entity) {
        StockMovementDTO dto = new StockMovementDTO();
        dto.setDrugId(entity.getDrug().getId());
        dto.setMovementType(entity.getMovementType());
        dto.setQuantity(entity.getQuantity());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

}
