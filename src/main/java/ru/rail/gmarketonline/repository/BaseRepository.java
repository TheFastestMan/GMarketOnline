package ru.rail.gmarketonline.repository;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.rail.gmarketonline.entity.BaseEntity;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseRepository<K extends Serializable, E extends BaseEntity<K>>
        implements Repository<K, E> {

    private final SessionFactory sessionFactory;
    private final Class<E> clazz;

    @Override
    public E save(E entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(entity);

            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error during save operation", e);
        }
    }

    @Override
    public void delete(K id) {
        @Cleanup var session = sessionFactory.openSession();
        session.delete(session.find(clazz, id));
        session.flush();
    }

    @Override
    public Optional<E> findById(K id) {
        @Cleanup var session = sessionFactory.openSession();
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        @Cleanup var session = sessionFactory.openSession();
        var criteria = session.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public void update(E entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.merge(entity);

    }
}
