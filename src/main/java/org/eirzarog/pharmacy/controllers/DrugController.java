package org.eirzarog.pharmacy.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eirzarog.pharmacy.entities.dtos.ApiResponse;
import org.eirzarog.pharmacy.entities.dtos.CreateDrugRequest;
import org.eirzarog.pharmacy.entities.dtos.DrugDTO;
import org.eirzarog.pharmacy.exceptions.DuplicateResourceException;
import org.eirzarog.pharmacy.exceptions.ResourceNotFoundException;
import org.eirzarog.pharmacy.services.DrugService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DrugDTO>>> getAllDrugs() {
        List<DrugDTO> drugs = drugService.getAllDrugs();
        return ResponseEntity.ok(ApiResponse.success(drugs));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DrugDTO>> createDrug(@Valid @RequestBody CreateDrugRequest request) throws DuplicateResourceException, ResourceNotFoundException {
        DrugDTO drug = drugService.createDrug(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("The drug was successfully created", drug));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DrugDTO>> getDrugById(@PathVariable Long id) {
        DrugDTO drug = drugService.getDrugById(id);
        return ResponseEntity.ok(ApiResponse.success(drug));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<DrugDTO>> getDrugByCode(@PathVariable String code) {
        DrugDTO drug = drugService.getDrugByCode(code);
        return ResponseEntity.ok(ApiResponse.success(drug));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<DrugDTO>>> getDrugsByCategory(@PathVariable Long categoryId) {
        List<DrugDTO> drugs = drugService.getDrugsByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success(drugs));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DrugDTO>>> searchDrugs(@RequestParam String keyword) {
        List<DrugDTO> drugs = drugService.searchDrugs(keyword);
        return ResponseEntity.ok(ApiResponse.success(drugs));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<DrugDTO>>> getLowStockDrugs(
            @RequestParam(defaultValue = "10") Integer threshold) {
        List<DrugDTO> drugs = drugService.getLowStockDrugs(threshold);
        return ResponseEntity.ok(ApiResponse.success(drugs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DrugDTO>> updateDrug(
            @PathVariable Long id,
            @Valid @RequestBody CreateDrugRequest request) {
        DrugDTO drug = drugService.updateDrug(id, request);
        return ResponseEntity.ok(ApiResponse.success("The drug was successfully updated", drug));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDrug(@PathVariable Long id) {
        drugService.deleteDrug(id);
        return ResponseEntity.ok(ApiResponse.success("The drug was successfully deleted", null));
    }
}