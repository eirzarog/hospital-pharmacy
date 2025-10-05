package org.eirzarog.pharmacy.entities.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eirzarog.pharmacy.entities.DrugCategory;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementSearchCriteria {
    private DrugCategory drugCategory;
    private Long drugId;
    private List<Long> drugIds;
    private Long categoryId;
    private String performedBy;
    private Instant from;
    private Instant to;
}
