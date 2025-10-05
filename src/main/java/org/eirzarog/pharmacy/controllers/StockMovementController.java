package org.eirzarog.pharmacy.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eirzarog.pharmacy.entities.dtos.ApiResponse;
import org.eirzarog.pharmacy.entities.dtos.CreateStockMovementRequest;
import org.eirzarog.pharmacy.entities.dtos.StockMovementDTO;
import org.eirzarog.pharmacy.services.StockMovementService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stock_movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping
    public ResponseEntity<ApiResponse<StockMovementDTO>> createMovement(
            @Valid @RequestBody CreateStockMovementRequest request) {
        StockMovementDTO stockMovement = stockMovementService.createStockMovement(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("The stock movement has registered successfully", stockMovement));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StockMovementDTO>>> getAllMovements() {
        List<StockMovementDTO> stockMovement = stockMovementService.
                getAllStockMovements();
        return ResponseEntity.ok(ApiResponse.success(stockMovement));
    }

    @GetMapping("/drug/{drugId}")
    public ResponseEntity<ApiResponse<List<StockMovementDTO>>> getMovementsByDrug(
            @PathVariable Long drugId) {
        List<StockMovementDTO> stockMovement = stockMovementService.getStockMovementsByDrug(drugId);
        return ResponseEntity.ok(ApiResponse.success(stockMovement));
    }

    @GetMapping("/outbound/drug/{drugId}")
    public ResponseEntity<ApiResponse<List<StockMovementDTO>>> getOutboundStockMovementsByDrugAndDateRange(
            @PathVariable Long drugId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<StockMovementDTO> stockMovement = stockMovementService
                .getOutboundStockMovementsByDrugAndDateRange(drugId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(stockMovement));
    }

    @GetMapping("/outbound/drugs")
    public ResponseEntity<ApiResponse<List<StockMovementDTO>>> getOutboundMovementsByDrugsAndDateRange(
            @RequestParam List<Long> drugIds,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<StockMovementDTO> stockMovement = stockMovementService
                .getOutboundStockMovementsByDrugsAndDateRange(drugIds, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(stockMovement));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<StockMovementDTO>>> getMovementsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<StockMovementDTO> stockMovement = stockMovementService.getStockMovementsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(stockMovement));
    }
}
