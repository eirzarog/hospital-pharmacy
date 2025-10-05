package org.eirzarog.pharmacy.services;

import jakarta.validation.Valid;
import org.eirzarog.pharmacy.entities.dtos.StaffDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    public StaffDTO createStaff(@Valid StaffDTO dto) {
        return null;
    }

    public List<StaffDTO> getAllStaff() {
        return null;
    }

    public StaffDTO getStafById(Long id) {
        return null;
    }
}
