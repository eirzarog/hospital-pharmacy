package org.eirzarog.pharmacy.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eirzarog.pharmacy.entities.dtos.ApiResponse;
import org.eirzarog.pharmacy.entities.dtos.DrugCategoryDTO;
import org.eirzarog.pharmacy.services.DrugCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class DrugCategoryController {

    private final DrugCategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<DrugCategoryDTO>> createCategory(
            @Valid @RequestBody DrugCategoryDTO dto) {
        DrugCategoryDTO category = categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("The category registered successfully", category));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DrugCategoryDTO>>> getAllCategories() {
        List<DrugCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DrugCategoryDTO>> getCategoryById(@PathVariable Long id) {
        DrugCategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success(category));
    }
}