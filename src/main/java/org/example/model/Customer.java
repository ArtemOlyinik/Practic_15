package org.example.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;
  private String phoneNumber;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Rental> rentals;
}
