package com.sokolov.demo.repository.employee;

import com.sokolov.demo.model.employee.Employee;
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
 * @created 25.06.2021
 */
@Repository
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Long create(Employee entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Employee employee = createOrUpdate(session, entity);
            return employee.getId();
        }
    }

    @Override
    public Long delete(Long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaDelete<Employee> criteriaDelete = criteriaBuilder.createCriteriaDelete(Employee.class);
            Root<Employee> root = criteriaDelete.from(Employee.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
            return id;
        }
    }

    @Override
    public Employee update(Employee entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return createOrUpdate(session, entity);
        }
    }

    @Override
    public Optional<Employee> findById(long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);
            criteriaQuery.where(idPredicate);

            TypedQuery<Employee> query = session.createQuery(criteriaQuery);
            return Optional.ofNullable(query.getSingleResult());
        }
    }

    @Override
    public List<Employee> findAll() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            TypedQuery<Employee> query = getEmployeeQueryFindAll(session);
            return query.getResultList();
        }
    }

    @Override
    public List<Employee> findAll(int startPage, int pageSize) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            TypedQuery<Employee> query = getEmployeeQueryFindAll(session);
            query.setFirstResult(startPage);
            query.setMaxResults(pageSize);
            return query.getResultList();
        }
    }

    private TypedQuery<Employee> getEmployeeQueryFindAll(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery);
    }

    private Employee createOrUpdate(Session session, Employee entity) {
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        return entity;
    }
}
