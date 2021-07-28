package com.sokolov.demo.repository.student;

import com.sokolov.demo.model.student.lazy.StudentLazy;
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
public class StudentRepositoryLazyImpl implements StudentRepositoryLazy {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Long create(StudentLazy entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            StudentLazy studentLazy = createOrUpdate(session, entity);
            return studentLazy.getId();
        }
    }

    @Override
    public Long delete(Long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaDelete<StudentLazy> criteriaDelete = criteriaBuilder.createCriteriaDelete(StudentLazy.class);
            Root<StudentLazy> root = criteriaDelete.from(StudentLazy.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
            return id;
        }
    }

    @Override
    public StudentLazy update(StudentLazy entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return createOrUpdate(session, entity);
        }
    }

    @Override
    public Optional<StudentLazy> findById(long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<StudentLazy> criteriaQuery = criteriaBuilder.createQuery(StudentLazy.class);
            Root<StudentLazy> root = criteriaQuery.from(StudentLazy.class);
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);
            criteriaQuery.where(idPredicate);

            TypedQuery<StudentLazy> query = session.createQuery(criteriaQuery);
            return Optional.ofNullable(query.getSingleResult());
        }
    }

    @Override
    public List<StudentLazy> findAll() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<StudentLazy> criteriaQuery = criteriaBuilder.createQuery(StudentLazy.class);
            Root<StudentLazy> root = criteriaQuery.from(StudentLazy.class);
            criteriaQuery.select(root);

            Query<StudentLazy> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    private StudentLazy createOrUpdate(Session session, StudentLazy entity) {
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        return entity;
    }
}
