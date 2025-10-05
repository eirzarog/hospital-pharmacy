package org.eirzarog.pharmacy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "drugs")
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ColumnDefault("0")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "category_id", nullable = false)
    private DrugCategory categoryId;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;


    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreatedDate
    private Instant createdAt;


    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @LastModifiedDate
    private Instant updatedAt;

    @OneToMany(mappedBy = "drug")
    private Set<StockMovement> stockMovements = new LinkedHashSet<>();

}