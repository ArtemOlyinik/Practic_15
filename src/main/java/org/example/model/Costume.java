package org.example.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Costume {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String costumeName;
  private Double pricePerDay;

  // Зв'язок Many-to-One з категорією
  @ManyToOne
  @JoinColumn(name = "category_id")
  @ToString.Exclude
  private Category category;

  // Зв'язок Many-to-Many з орендою
  @ManyToMany(mappedBy = "rentedCostumes")
  @ToString.Exclude
  private List<Rental> rentals;
}
