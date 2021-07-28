package com.sokolov.demo.utils;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.Random;

/**
 * @author Sokolov_SA
 * @created 15.07.2021
 */

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FillingDirectoryFactScript {

    public static final int DIRECTORY_AMOUNT = 1_000_000;
    public static final int FACT_AMOUNT = 1;

    public static final int MAX_FACT_RATE = 10;
    public static final int MIN_FACT_RATE = 1;

    public static final Random RANDOM_GENERATOR = new Random();
    private final EntityManagerFactory entityManagerFactory;

    public void createDirectoryWithFact_random() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Transaction transaction = session.getTransaction();

            transaction.begin();
            for (int i = 1; i <= DIRECTORY_AMOUNT; i++) {
                int randomNumberLength6 = RANDOM_GENERATOR.nextInt(900000) + 100000;
                int randomNumberLength7 = RANDOM_GENERATOR.nextInt(9000000) + 1000000;
                session.createNativeQuery(
                        "INSERT INTO public.directory " +
                                "(name, address, address2, city_id, phone, postcode, district) " +
                                "VALUES (:name, :address, :address2, :city_id, :phone, :postcode, :district)"
                )
                        .setParameter("name", String.format("directory_%d", i))
                        .setParameter("address", String.format("address_№%d", i))
                        .setParameter("address2", String.format("address2_№%d", i))
                        .setParameter("city_id", String.format("city_id_#%d", i))
                        .setParameter("phone", String.format("%s%d", "+37529", randomNumberLength7))
                        .setParameter("postcode", String.valueOf(randomNumberLength6))
                        .setParameter("district", String.format("district_%d", i))
                        .executeUpdate();

                for (int j = 1; j <= FACT_AMOUNT; j++) {
                    session.createNativeQuery(
                            "INSERT INTO public.fact " +
                                    "(short_description, full_description, rate, comment, directory_id) " +
                                    "VALUES (:short_description, :full_description, :rate, :comment, :directory_id)"
                    )
                            .setParameter("short_description", RandomStringUtils.random(2, true, false))
                            .setParameter("full_description", RandomStringUtils.random(10, true, false))
                            .setParameter("rate", RANDOM_GENERATOR.nextInt(MAX_FACT_RATE - MIN_FACT_RATE + 1) + MIN_FACT_RATE)
                            .setParameter("comment", RandomStringUtils.random(4, true, false))
                            .setParameter("directory_id", i)
                            .executeUpdate();
                }
            }
            transaction.commit();
        }
    }

}
