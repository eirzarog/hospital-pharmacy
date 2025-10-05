package org.eirzarog.pharmacy.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eirzarog.pharmacy.entities.Staff;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementDTO {
    private Long id;
    private Long drugId;
    private Long performedBy; // Staff ID
    private String MovementType;
    private Integer quantity ;
    private Instant createdAt;
}
