package com.sokolov.demo.utils;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.Random;

/**
 * @author Sokolov_SA
 * @created 20.07.2021
 */

@Component
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FillingClientScript {

    public static final int CLIENT_AMOUNT = 3_000_000;
    public static final int MIN_CLIENT_AGE = 20;
    public static final int MAX_CLIENT_AGE = 50;
    public static final int MAX_CLIENT_SALARY = 1500;
    public static final int MIN_CLIENT_SALARY = 100;

    public static final Random RANDOM_GENERATOR = new Random();
    private final EntityManagerFactory entityManagerFactory;

    public void fillClientTable() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Transaction transaction = session.getTransaction();

            transaction.begin();
            int age;
            int salary;
            for (int i = 0; i <= CLIENT_AMOUNT; i++) {
                age = RANDOM_GENERATOR.nextInt(MAX_CLIENT_AGE - MIN_CLIENT_AGE + 1) + MIN_CLIENT_AGE;
                salary = RANDOM_GENERATOR.nextInt(MAX_CLIENT_SALARY - MIN_CLIENT_SALARY + 1) + MIN_CLIENT_SALARY;
                session.createSQLQuery(
                        "INSERT INTO public.client " +
                                "(first_name, second_name, age, salary) " +
                                "VALUES (:first_name, :second_name, :age, :salary)"
                ).setParameter("first_name", String.format("first_name_%d", i))
                        .setParameter("second_name", String.format("second_name_%d", i))
                        .setParameter("age", age)
                        .setParameter("salary", salary)
                        .executeUpdate();
            }
            transaction.commit();
        }
    }
}
