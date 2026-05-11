package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Customer;

public class CustomerRepository extends BaseRepository<Customer, Long> {
    public CustomerRepository(EntityManager em) {
        super(em, Customer.class);
    }
}