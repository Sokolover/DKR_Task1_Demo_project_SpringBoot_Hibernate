package com.sokolov.demo.repository.client;

import com.sokolov.demo.model.Client;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
@Repository
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClientRepositoryImpl implements ClientRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Long create(Client entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Client createdClient = createOrUpdate(session, entity);
            return createdClient.getId();
        }
    }

    @Override
    public Long delete(Long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaDelete<Client> criteriaDelete = criteriaBuilder.createCriteriaDelete(Client.class);
            Root<Client> root = criteriaDelete.from(Client.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

            Transaction transaction = session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
            return id;
        }
    }

    @Override
    public Client update(Client entity) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return createOrUpdate(session, entity);
        }
    }

    @Override
    public Optional<Client> findById(long id) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);
            criteriaQuery.where(idPredicate);

            TypedQuery<Client> query = session.createQuery(criteriaQuery);
            return Optional.ofNullable(query.getSingleResult());
        }
    }

    @Override
    public List<Client> findAll() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            criteriaQuery.select(root);

            TypedQuery<Client> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    /**
     * SELECT age, SUM(salary) as sum FROM workers GROUP BY age
     * http://old.code.mu/sql/group-by.html 1-1
     *
     * @return grouped client list
     */
    @Override
    public List<Client> countSalaryByAgeAscending() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);

            Path<Object> age = root.get("age");
            Path<Number> salary = root.get("salary");
            criteriaQuery.select(criteriaBuilder.construct(Client.class, age, criteriaBuilder.sum(salary)))
                    .groupBy(age)
                    .orderBy(criteriaBuilder.asc(age));

            TypedQuery<Client> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }


    /**
     * SELECT age, COUNT(*) as count FROM client GROUP BY age HAVING count <= 30 ORDER BY age;
     * http://old.code.mu/sql/having.html 2-2
     *
     * @return Map age:countRecords
     */
    @Override
    public Map<Integer, Long> countRecordsGroupByAgeHavingLessThenConditionAscending(Long lessThanCondition) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
            Root<Client> root = criteriaQuery.from(Client.class);

            Path<Object> age = root.get("age");
            Expression<Long> countRecords = criteriaBuilder.count(root);
            criteriaQuery.multiselect(age, countRecords)
                    .groupBy(age)
                    .having(criteriaBuilder.lessThanOrEqualTo(countRecords, lessThanCondition))
                    .orderBy(criteriaBuilder.asc(age));

            List<Tuple> tuples = session.createQuery(criteriaQuery).getResultList();
            Map<Integer, Long> result = new HashMap<>();
            tuples.forEach(tuple -> result.put((Integer) tuple.get(0), (Long) tuple.get(1)));

            return result;
        }
    }

    @Override
    public List<Client> selectClientsWhereSalaryEqualsMinSalaryOrderBySecondNameDesc() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);

            Subquery<Double> subquery = criteriaQuery.subquery(Double.class);
            Root<Client> subqueryRoot = subquery.from(Client.class);
            subquery.select(criteriaBuilder.min(subqueryRoot.get("salary")));

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(
                            root.get("salary"),
                            subquery))
                    .orderBy(criteriaBuilder.desc(root.get("secondName")));

            Query<Client> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    private Client createOrUpdate(Session session, Client entity) {
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        return entity;
    }

}
