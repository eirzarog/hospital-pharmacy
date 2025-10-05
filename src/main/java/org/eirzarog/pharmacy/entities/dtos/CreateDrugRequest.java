package org.eirzarog.pharmacy.entities.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDrugRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters")
    private String name;

    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 50, message = "Code must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Code must contain only uppercase letters and numbers")
    private String code;

    @NotNull(message = "Price is required")
    @Digits(integer = 6, fraction = 2, message = "Price must have up to 6 digits and 2 decimals")
    private BigDecimal price;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock = 0;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Size(max = 100, message = "Manufacturer cannot exceed 100 characters")
    private String manufacturer;
}