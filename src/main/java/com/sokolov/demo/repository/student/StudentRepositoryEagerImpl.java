package com.sokolov.demo.repository.student;

import com.sokolov.demo.model.student.eager.StudentEager;
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
 * @created 19.07.2021
 */
@Repository
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StudentRepositoryEagerImpl implements StudentRepositoryEager {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Long create(StudentEager entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            StudentEager studentEager = createOrUpdate(session, entity);
            return studentEager.getId();
        }
    }

    @Override
    public Long delete(Long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaDelete<StudentEager> criteriaDelete = criteriaBuilder.createCriteriaDelete(StudentEager.class);
            Root<StudentEager> root = criteriaDelete.from(StudentEager.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
            return id;
        }
    }

    @Override
    public StudentEager update(StudentEager entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return createOrUpdate(session, entity);
        }
    }

    @Override
    public Optional<StudentEager> findById(long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<StudentEager> criteriaQuery = criteriaBuilder.createQuery(StudentEager.class);
            Root<StudentEager> root = criteriaQuery.from(StudentEager.class);
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);
            criteriaQuery.where(idPredicate);

            TypedQuery<StudentEager> query = session.createQuery(criteriaQuery);
            return Optional.ofNullable(query.getSingleResult());
        }
    }

    @Override
    public List<StudentEager> findAll() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<StudentEager> criteriaQuery = criteriaBuilder.createQuery(StudentEager.class);
            Root<StudentEager> root = criteriaQuery.from(StudentEager.class);
            criteriaQuery.select(root);

            Query<StudentEager> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    private StudentEager createOrUpdate(Session session, StudentEager entity) {
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        return entity;
    }
}
