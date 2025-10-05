package org.eirzarog.pharmacy.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eirzarog.pharmacy.entities.Drug;
import org.eirzarog.pharmacy.entities.StockMovement;
import org.eirzarog.pharmacy.entities.criteria.StockMovementSearchCriteria;
import org.eirzarog.pharmacy.entities.dtos.ApiResponse;
import org.eirzarog.pharmacy.entities.dtos.CreateDrugRequest;
import org.eirzarog.pharmacy.entities.dtos.DrugDTO;
import org.eirzarog.pharmacy.exceptions.DuplicateResourceException;
import org.eirzarog.pharmacy.exceptions.ResourceNotFoundException;
import org.eirzarog.pharmacy.services.DrugService;
import org.eirzarog.pharmacy.services.StockMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;
    private final StockMovementService stockMovementService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DrugDTO>>> getAllDrugs() {
        List<DrugDTO> drugs = drugService.getAllDrugs();
        return ResponseEntity.ok(ApiResponse.success(drugs));
    }

    @PostMapping
    public ResponseEntity<?> createDrug(@RequestBody DrugDTO drugDTO) {
        Drug createdDrug = drugService.createDrug(drugDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDrug);
    }


    // GET http://localhost:8080/api/drugs?from=2025-09-13&to=2025-09-20
    @GetMapping(params = {"from", "to"})
    public List<Drug> getAllDrugsByDateRange(@RequestParam String from, @RequestParam String to) {
        return drugService.filterAllDrugsByDateRange(from, to);
    }







//
//    @GetMapping("/search")
//    public ResponseEntity<List<StockMovement>> search(
//            @RequestParam(required = false) Long drugId,
//            @RequestParam(required = false) Long categoryId,
//            @RequestParam(required = false) String from,
//            @RequestParam(required = false) String to
//    ) {
//        StockMovementSearchCriteria criteria = StockMovementSearchCriteria.builder()
//                .drugId(drugId)
//                .categoryId(categoryId)
//                .from(from != null ? LocalDate.parse(from).atStartOfDay(ZoneOffset.UTC).toInstant() : null)
//                .to(to != null ? LocalDate.parse(to).atStartOfDay(ZoneOffset.UTC).toInstant() : null)
//                .build();
//
//        List<StockMovement> results = stockMovementService.searchStockMovements(criteria);
//        return ResponseEntity.ok(results);
//    }

}