package org.eirzarog.pharmacy.entities.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private String full_name;
    private String role;
    private String department;
    private String email;
    private String phone_number;
    private Instant hire_date;
    private String shift;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
