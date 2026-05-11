package org.example.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String categoryName; // Перевіримо як Hibernate зробить з цього category_name

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Costume> costumes;
}
