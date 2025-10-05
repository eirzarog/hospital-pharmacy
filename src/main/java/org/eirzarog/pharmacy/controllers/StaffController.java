package org.eirzarog.pharmacy.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eirzarog.pharmacy.entities.dtos.ApiResponse;
import org.eirzarog.pharmacy.entities.dtos.StaffDTO;
import org.eirzarog.pharmacy.services.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<ApiResponse<StaffDTO>> createCategory(
            @Valid @RequestBody StaffDTO dto) {
        StaffDTO staff = staffService.createStaff(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Η καταχώρηση νέου προσωπικού έγινε επιτυχώς", staff));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StaffDTO>>> getAllCategories() {
        List<StaffDTO> staff = staffService.getAllStaff();
        return ResponseEntity.ok(ApiResponse.success(staff));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StaffDTO>> getCategoryById(@PathVariable Long id) {
        StaffDTO staff = staffService.getStafById(id);
        return ResponseEntity.ok(ApiResponse.success(staff));
    }
}