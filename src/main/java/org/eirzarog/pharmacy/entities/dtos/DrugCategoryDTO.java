package org.eirzarog.pharmacy.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugCategoryDTO {
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
