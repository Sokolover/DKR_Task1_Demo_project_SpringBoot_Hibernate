package com.sokolov.demo.repository.directory;

import com.sokolov.demo.model.directory.eager.DirectoryEager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
 * @created 19.07.2021
 */
@Repository
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DirectoryRepositoryEagerImpl implements DirectoryRepositoryEager {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Long create(DirectoryEager entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            DirectoryEager createdDirectory = createOrUpdate(session, entity);
            return createdDirectory.getId();
        }
    }

    @Override
    public Long delete(Long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaDelete<DirectoryEager> criteriaDelete = criteriaBuilder.createCriteriaDelete(DirectoryEager.class);
            Root<DirectoryEager> root = criteriaDelete.from(DirectoryEager.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
            return id;
        }
    }

    @Override
    public DirectoryEager update(DirectoryEager entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return createOrUpdate(session, entity);
        }
    }

    @Override
    public Optional<DirectoryEager> findById(long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<DirectoryEager> criteriaQuery = criteriaBuilder.createQuery(DirectoryEager.class);
            Root<DirectoryEager> root = criteriaQuery.from(DirectoryEager.class);
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);
            criteriaQuery.where(idPredicate);

            TypedQuery<DirectoryEager> query = session.createQuery(criteriaQuery);
            return Optional.ofNullable(query.getSingleResult());
        }
    }

    @Override
    public List<DirectoryEager> findAll() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<DirectoryEager> criteriaQuery = criteriaBuilder.createQuery(DirectoryEager.class);
            Root<DirectoryEager> root = criteriaQuery.from(DirectoryEager.class);
            criteriaQuery.select(root);

            TypedQuery<DirectoryEager> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    private DirectoryEager createOrUpdate(Session session, DirectoryEager entity) {
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        return entity;
    }

}
