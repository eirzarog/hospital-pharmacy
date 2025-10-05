package org.eirzarog.pharmacy.services;

import jakarta.validation.Valid;
import org.eirzarog.pharmacy.entities.dtos.DrugCategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugCategoryService {
    public DrugCategoryDTO createCategory(@Valid DrugCategoryDTO dto) {
        return null;
    }

    public List<DrugCategoryDTO> getAllCategories() {
        return null;
    }

    public DrugCategoryDTO getCategoryById(Long id) {
        return null;
    }
}
