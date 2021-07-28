package com.sokolov.demo.repository.directory;

import com.sokolov.demo.model.directory.lazy.DirectoryLazy;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * @author Sokolov_SA
 * @created 15.07.2021
 */
@Repository
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DirectoryRepositoryLazyImpl implements DirectoryRepositoryLazy {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Long create(DirectoryLazy entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            DirectoryLazy createdDirectory = createOrUpdate(session, entity);
            return createdDirectory.getId();
        }
    }

    @Override
    public Long delete(Long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaDelete<DirectoryLazy> criteriaDelete = criteriaBuilder.createCriteriaDelete(DirectoryLazy.class);
            Root<DirectoryLazy> root = criteriaDelete.from(DirectoryLazy.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
            return id;
        }
    }

    @Override
    public DirectoryLazy update(DirectoryLazy entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return createOrUpdate(session, entity);
        }
    }

    /**
     * <p>Analog in HQL:</p>
     * <code>
     * Query<DirectoryLazy> query = session.createQuery(
     * "SELECT DISTINCT dl FROM DirectoryLazy dl LEFT JOIN FETCH dl.factLazies WHERE dl.id=:id",
     * DirectoryLazy.class)
     * .setParameter("id", id);
     * </code>
     *
     * @param id find id param
     * @return
     */
    @Override
    public Optional<DirectoryLazy> findById(long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<DirectoryLazy> criteriaQuery = criteriaBuilder.createQuery(DirectoryLazy.class);

            Root<DirectoryLazy> root = criteriaQuery.from(DirectoryLazy.class);
            root.fetch("factLazies", JoinType.LEFT);
            criteriaQuery.select(root).distinct(true);
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);
            criteriaQuery.where(idPredicate);

            Query<DirectoryLazy> query = session.createQuery(criteriaQuery);
            return Optional.ofNullable(query.getSingleResult());
        }
    }

    /**
     * <p>Analog in HQL:</p>
     * <code>
     * Query<DirectoryLazy> query = session.createQuery(
     * "SELECT DISTINCT dl FROM DirectoryLazy dl LEFT JOIN FETCH dl.factLazies",
     * DirectoryLazy.class);
     * </code>
     *
     * @return
     */
    @Override
    public List<DirectoryLazy> findAll() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<DirectoryLazy> criteriaQuery = criteriaBuilder.createQuery(DirectoryLazy.class);

            Root<DirectoryLazy> root = criteriaQuery.from(DirectoryLazy.class);
            root.fetch("factLazies", JoinType.LEFT);
            criteriaQuery.select(root).distinct(true);

            Query<DirectoryLazy> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    @Override
    public Optional<DirectoryLazy> findByName(String name) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<DirectoryLazy> criteriaQuery = criteriaBuilder.createQuery(DirectoryLazy.class);
            Root<DirectoryLazy> root = criteriaQuery.from(DirectoryLazy.class);
            Predicate predicate = criteriaBuilder.equal(root.get("name"), name);
            criteriaQuery.select(root).where(predicate);

            TypedQuery<DirectoryLazy> query = session.createQuery(criteriaQuery);
            return Optional.ofNullable(query.getSingleResult());
        }
    }

    private DirectoryLazy createOrUpdate(Session session, DirectoryLazy entity) {
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        return entity;
    }

}
