package org.example;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Category;
import org.example.model.Costume;
import org.example.model.Customer;
import org.example.repository.CostumeRepository;
import org.example.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CarnivalPU");
        EntityManager em = emf.createEntityManager();

        // Ініціалізуємо наші репозиторії
        CustomerRepository customerRepo = new CustomerRepository(em);
        CostumeRepository costumeRepo = new CostumeRepository(em);

        Faker faker = new Faker(new Locale("uk"));

        System.out.println("=== ПЕРЕВІРКА ПАТЕРНУ REPOSITORY ===");

        // 1. Створення (через Repository)
        Category cat = new Category(null, "Фентезі", new ArrayList<>());
        // Для спрощення persist категорії зробимо через em,
        // або можна було б створити CategoryRepository
        em.getTransaction().begin();
        em.persist(cat);
        em.getTransaction().commit();

        Customer newCustomer = new Customer(null, faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber(), new ArrayList<>());
        customerRepo.save(newCustomer);
        System.out.println("Клієнта збережено через CustomerRepository: " + newCustomer.getFirstName());

        Costume newCostume = new Costume(null, "Ельф", 450.0, cat, new ArrayList<>());
        costumeRepo.save(newCostume);
        System.out.println("Костюм збережено через CostumeRepository: " + newCostume.getCostumeName());

        // 2. Читання всіх записів
        System.out.println("\n--- Список усіх костюмів (findAll) ---");
        List<Costume> allCostumes = costumeRepo.findAll();
        allCostumes.forEach(c -> System.out.println(" > " + c.getCostumeName() + " (" + c.getPricePerDay() + " ₴)"));

        // 3. Оновлення
        System.out.println("\n--- Оновлення ціни ---");
        newCostume.setPricePerDay(500.0);
        costumeRepo.update(newCostume);
        System.out.println("Ціну оновлено для: " + costumeRepo.findById(newCostume.getId()).getCostumeName());

        // 4. Видалення
        System.out.println("\n--- Видалення клієнта ---");
        Long idToDelete = newCustomer.getId();
        customerRepo.delete(idToDelete);
        System.out.println("Клієнта з ID " + idToDelete + " видалено.");

        em.close();
        emf.close();
        System.out.println("\n=== РОБОТУ ЗАВЕРШЕНО УСПІШНО ===");
    }
}