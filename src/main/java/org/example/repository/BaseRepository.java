package org.example.repository;

import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class BaseRepository<T, ID> implements IRepository<T, ID> {
    protected final EntityManager em;
    private final Class<T> entityClass;

    protected BaseRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    @Override
    public void save(T entity) {
        executeInTransaction(() -> em.persist(entity));
    }

    @Override
    public void update(T entity) {
        executeInTransaction(() -> em.merge(entity));
    }

    @Override
    public void delete(ID id) {
        executeInTransaction(() -> {
            T entity = findById(id);
            if (entity != null) {
                em.remove(entity);
            }
        });
    }

    @Override
    public T findById(ID id) {
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    private void executeInTransaction(Runnable action) {
        try {
            em.getTransaction().begin();
            action.run();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}