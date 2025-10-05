package org.eirzarog.pharmacy.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eirzarog.pharmacy.entities.DrugCategory;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugDTO {
    private String name;
    private String code;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private String categoryName;
    private String description;
    private String manufacturer;
    private Instant createdAt;
    private Instant updatedAt;
}