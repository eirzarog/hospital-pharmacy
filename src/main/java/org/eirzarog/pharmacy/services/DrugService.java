package org.eirzarog.pharmacy.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eirzarog.pharmacy.entities.Drug;
import org.eirzarog.pharmacy.entities.DrugCategory;
import org.eirzarog.pharmacy.entities.dtos.DrugDTO;
import org.eirzarog.pharmacy.repositories.DrugCategoryRepository;
import org.eirzarog.pharmacy.repositories.DrugRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrugService {

    private final DrugRepository drugRepository;
    private final DrugCategoryRepository drugCategoryRepository;
    private final DrugCategoryService drugCategoryService;


    public List<DrugDTO> getAllDrugs() {
        return drugRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public Drug createDrug(DrugDTO dto) {
        Drug drug = new Drug();

        drug.setName(dto.getName());
        drug.setCode(dto.getCode());
        drug.setPrice(dto.getPrice());
        drug.setStock(dto.getStock());
        drug.setDescription(dto.getDescription());
        drug.setManufacturer(dto.getManufacturer());
        DrugCategory categoryId = drugCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("CategoryId not found") );
        drug.setCategoryId(categoryId);

        return drugRepository.save(drug);
    }


    public List<Drug> filterAllDrugsByDateRange(String from, String to) {
        try {
            LocalDate fromDate = LocalDate.parse(from); // ISO format
            Instant startOfDay = fromDate.atStartOfDay().toInstant(ZoneOffset.UTC);

            LocalDate toDate = LocalDate.parse(to); // ISO format
            Instant endOfDay = toDate.atStartOfDay().toInstant(ZoneOffset.UTC);

            return drugRepository.findAllDrugsbyDateRange(startOfDay, endOfDay);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }
    }



    private DrugDTO convertToDTO(Drug drug) {
        DrugDTO dto = new DrugDTO();
        dto.setName(drug.getName());
        dto.setCode(drug.getCode());
        dto.setPrice(drug.getPrice());
        dto.setStock(drug.getStock());
        dto.setCategoryId(drug.getCategoryId().getId());
        dto.setCategoryName(drug.getDescription());
        dto.setDescription(drug.getDescription());
        dto.setManufacturer(drug.getManufacturer());
        return dto;
    }





}
