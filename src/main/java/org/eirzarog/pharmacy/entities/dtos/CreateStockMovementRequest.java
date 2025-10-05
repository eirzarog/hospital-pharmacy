package org.eirzarog.pharmacy.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStockMovementRequest {
    private String movementType;
    private Long drugId;
    private Integer quantity;
    private Instant movementDate;
    private String notes;
    private Long performedBy;
    private Instant createdAt;
}
