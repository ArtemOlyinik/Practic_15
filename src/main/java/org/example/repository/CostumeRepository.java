package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Costume;

public class CostumeRepository extends BaseRepository<Costume, Long> {
    public CostumeRepository(EntityManager em) {
        super(em, Costume.class);
    }
}