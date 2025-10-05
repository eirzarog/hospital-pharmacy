package org.eirzarog.pharmacy.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eirzarog.pharmacy.entities.StockMovement;
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
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;




    @PostMapping
    public ResponseEntity<?> createStockMovement(@RequestBody StockMovementDTO dto) {
        StockMovementDTO movement = stockMovementService.createStockMovement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movement);
    }




    @GetMapping
    public ResponseEntity<ApiResponse<List<StockMovementDTO>>> getAllStockMovements() {
        List<StockMovementDTO> stockMovement = stockMovementService.getAllStockMovements();
        return ResponseEntity.ok(ApiResponse.success(stockMovement));
    }








    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<StockMovementDTO>>> getStockMovementsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<StockMovementDTO> stockMovement = stockMovementService.getStockMovementsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(stockMovement));
    }
}
