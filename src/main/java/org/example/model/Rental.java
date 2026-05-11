package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate rentalDate;
  private LocalDate returnDate;

  // Зв'язок Many-to-One з клієнтом
  @ManyToOne
  @JoinColumn(name = "customer_id")
  @ToString.Exclude
  private Customer customer;

  // Зв'язок Many-to-Many з костюмами (Тут створюється проміжна таблиця)
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "rental_costume",
      joinColumns = @JoinColumn(name = "rental_id"),
      inverseJoinColumns = @JoinColumn(name = "costume_id"))
  @ToString.Exclude
  private List<Costume> rentedCostumes;
}
