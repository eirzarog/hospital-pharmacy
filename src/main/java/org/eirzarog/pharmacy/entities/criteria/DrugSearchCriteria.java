package org.eirzarog.pharmacy.entities.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugSearchCriteria {
    private Instant from;
    private Instant to;
}
