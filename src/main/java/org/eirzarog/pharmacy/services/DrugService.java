package org.eirzarog.pharmacy.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eirzarog.pharmacy.entities.Drug;
import org.eirzarog.pharmacy.entities.DrugCategory;
import org.eirzarog.pharmacy.entities.dtos.CreateDrugRequest;
import org.eirzarog.pharmacy.entities.dtos.DrugDTO;
import org.eirzarog.pharmacy.exceptions.DuplicateResourceException;
import org.eirzarog.pharmacy.exceptions.ResourceNotFoundException;
import org.eirzarog.pharmacy.repositories.DrugCategoryRepository;
import org.eirzarog.pharmacy.repositories.DrugRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrugService {

    private final DrugRepository drugRepository;
    private final DrugCategoryRepository drugCategoryRepository;

    @Transactional
    public DrugDTO createDrug(CreateDrugRequest request) throws DuplicateResourceException, ResourceNotFoundException {
        log.info("Creating new drug with code: {}", request.getCode());

        // Check if drug code already exists
        if (drugRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException(
                    "The medicine already exists with code: " + request.getCode()
            );
        }

        // Find category
        DrugCategory category = drugCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The category already exist with ID: " + request.getCategoryId()
                ));

        // Create new drug entity
        Drug drug = new Drug();
        drug.setName(request.getName());
        drug.setCode(request.getCode());
        drug.setPrice(request.getPrice());
        drug.setStock(request.getStock() != null ? request.getStock() : 0);
        drug.setCategory(category);
        drug.setDescription(request.getDescription());
        drug.setManufacturer(request.getManufacturer());

        // Save to database
        Drug savedDrug = drugRepository.save(drug);
        log.info("Drug created successfully with ID: {}", savedDrug.getId());

        return convertToDTO(savedDrug);
    }

    public List<DrugDTO> getAllDrugs() {
        return drugRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public DrugDTO getDrugById(Long id) {
        return null;
    }



    public DrugDTO getDrugByCode(String code) {
        return null;
    }

    public List<DrugDTO> getDrugsByCategory(Long categoryId) {
        return null;
    }

    public List<DrugDTO> searchDrugs(String keyword) {
        return null;
    }

    public List<DrugDTO> getLowStockDrugs(Integer threshold) {
        return null;
    }

    public DrugDTO updateDrug(Long id, @Valid CreateDrugRequest request) {
        return null;
    }

    public void deleteDrug(Long id) {

    }

    private DrugDTO convertToDTO(Drug drug) {
        DrugDTO dto = new DrugDTO();
        dto.setId(drug.getId());
        dto.setName(drug.getName());
        dto.setCode(drug.getCode());
        dto.setPrice(drug.getPrice());
        dto.setStock(drug.getStock());
        dto.setCategoryId(drug.getCategory().getId());
        dto.setCategoryName(drug.getCategory().getName());
        dto.setDescription(drug.getDescription());
        dto.setManufacturer(drug.getManufacturer());
        return dto;
    }
}
