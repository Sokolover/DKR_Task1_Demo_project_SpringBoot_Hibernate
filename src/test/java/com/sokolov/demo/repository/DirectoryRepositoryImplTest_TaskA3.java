package com.sokolov.demo.repository;

import com.sokolov.demo.Task1SprintBootJpaApplication;
import com.sokolov.demo.model.directory.eager.DirectoryEager;
import com.sokolov.demo.model.directory.eager.FactEager;
import com.sokolov.demo.model.directory.lazy.DirectoryLazy;
import com.sokolov.demo.model.directory.lazy.FactLazy;
import com.sokolov.demo.repository.directory.DirectoryRepositoryEager;
import com.sokolov.demo.repository.directory.DirectoryRepositoryLazy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

/**
 * Test One-to-Many relationship
 *
 * @author Sokolov_SA
 * @created 16.07.2021
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Task1SprintBootJpaApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = "classpath:drop_create_directory_fact_table.sql")
class DirectoryRepositoryImplTest_TaskA3 {

    private final DirectoryRepositoryLazy directoryRepositoryLazy;
    private final DirectoryRepositoryEager directoryRepositoryEager;

    @Test
    void create() {
        DirectoryLazy directoryWithFacts = createDirectoryWithFactsLazy();
        Long newDirectoryId = directoryRepositoryLazy.create(directoryWithFacts);

        Optional<DirectoryLazy> optionalDirectory = directoryRepositoryLazy.findById(newDirectoryId);
        DirectoryLazy directoryFromDatabase = optionalDirectory.orElseThrow(() -> new RuntimeException("not found"));

        Assertions.assertEquals(newDirectoryId, directoryFromDatabase.getId());
    }

    @Test
    void delete() {
        DirectoryLazy directoryWithFacts = createDirectoryWithFactsLazy();
        Long newDirectoryId = directoryRepositoryLazy.create(directoryWithFacts);

        Optional<DirectoryLazy> optionalDirectory = directoryRepositoryLazy.findById(newDirectoryId);
        DirectoryLazy directoryFromDatabase = optionalDirectory.orElseThrow(() -> new RuntimeException("not found"));

        Long deletedDirectory = directoryRepositoryLazy.delete(directoryFromDatabase.getId());

        Assertions.assertEquals(deletedDirectory, directoryFromDatabase.getId());
    }

    @Test
    void update() {
        DirectoryLazy directory = createDirectoryWithFactsLazy();
        Long newDirectoryId = directoryRepositoryLazy.create(directory);

        directory.setName("Sergey");
        directoryRepositoryLazy.update(directory);

        Optional<DirectoryLazy> optionalDirectory = directoryRepositoryLazy.findById(newDirectoryId);
        DirectoryLazy updatedDirectoryFromDatabase = optionalDirectory.orElseThrow(() -> new RuntimeException("not found"));

        Assertions.assertEquals(directory.getName(), updatedDirectoryFromDatabase.getName());
    }

    @Test
    void findByIdLazy() {
        DirectoryLazy directory = createDirectoryWithFactsLazy();
        Long newDirectoryId = directoryRepositoryLazy.create(directory);
        directory.setId(newDirectoryId);

        Optional<DirectoryLazy> optionalDirectory = directoryRepositoryLazy.findById(newDirectoryId);
        DirectoryLazy directoryFromDatabase = optionalDirectory.orElseThrow(() -> new RuntimeException("not found"));

        System.out.println(directoryFromDatabase);
        directoryFromDatabase.getFactLazies().forEach(System.out::println);

        Assertions.assertEquals(directory, directoryFromDatabase);
    }

    @Test
    void findAllLazy() {
        DirectoryLazy directory1 = createDirectoryWithFactsLazy();
        DirectoryLazy directory2 = createDirectoryWithFactsLazy();
        DirectoryLazy directory3 = createDirectoryWithFactsLazy();

        directory1.setName("directory1");
        directory2.setName("directory2");
        directory3.setName("directory3");

        List<DirectoryLazy> directories = Arrays.asList(directory1, directory2, directory3);
        directories.forEach(directoryRepositoryLazy::create);

        List<DirectoryLazy> directoryRepositoryAll = directoryRepositoryLazy.findAll();

        directoryRepositoryAll.forEach(e -> {
            System.out.println(e);
            e.getFactLazies().forEach(System.out::println);
        });

        Assertions.assertEquals(3, directoryRepositoryAll.size());
    }

    @Test
    void findByIdEager() {
        DirectoryEager directory = createDirectoryWithFactsEager();
        Long newDirectoryId = directoryRepositoryEager.create(directory);
        directory.setId(newDirectoryId);

        Optional<DirectoryEager> optionalDirectory = directoryRepositoryEager.findById(newDirectoryId);
        DirectoryEager directoryFromDatabase = optionalDirectory.orElseThrow(() -> new RuntimeException("not found"));

        System.out.println(directoryFromDatabase);
        directoryFromDatabase.getFactEagers().forEach(System.out::println);

        Assertions.assertEquals(directory, directoryFromDatabase);
    }

    @Test
    void findAllEager() {
        DirectoryEager directory1 = createDirectoryWithFactsEager();
        DirectoryEager directory2 = createDirectoryWithFactsEager();
        DirectoryEager directory3 = createDirectoryWithFactsEager();

        directory1.setName("directory1");
        directory2.setName("directory2");
        directory3.setName("directory3");

        List<DirectoryEager> directories = Arrays.asList(directory1, directory2, directory3);
        directories.forEach(directoryRepositoryEager::create);

        List<DirectoryEager> directoryRepositoryAll = directoryRepositoryEager.findAll();

        directoryRepositoryAll.forEach(e -> {
            System.out.println(e);
            e.getFactEagers().forEach(System.out::println);
        });

        Assertions.assertEquals(3, directoryRepositoryAll.size());
    }

    private DirectoryLazy createDirectoryWithFactsLazy() {
        DirectoryLazy directory = new DirectoryLazy();
        directory.setName("directory");
        directory.setAddress("address1");
        directory.setAddress2("address2");
        directory.setCityId("123456");
        directory.setPhone("+375291234567");
        directory.setPostcode("228322");
        directory.setDistrict("district");
        Set<FactLazy> facts = createFactsLazy();
        directory.setFactLazies(facts);
        facts.forEach(e -> e.setDirectoryLazy(directory));
        return directory;
    }

    private Set<FactLazy> createFactsLazy() {
        FactLazy fact1 = new FactLazy();
        fact1.setShortDescription("shortDescription1");
        fact1.setFullDescription("fullDescription1");
        fact1.setRate(5);
        fact1.setComment("comment1");

        FactLazy fact2 = new FactLazy();
        fact2.setShortDescription("shortDescription2");
        fact2.setFullDescription("fullDescription2");
        fact2.setRate(7);
        fact2.setComment("comment2");

        Set<FactLazy> facts = new HashSet<>();
        facts.add(fact1);
        facts.add(fact2);
        return facts;
    }

    private DirectoryEager createDirectoryWithFactsEager() {
        DirectoryEager directory = new DirectoryEager();
        directory.setName("directory");
        directory.setAddress("address1");
        directory.setAddress2("address2");
        directory.setCityId("123456");
        directory.setPhone("+375291234567");
        directory.setPostcode("228322");
        directory.setDistrict("district");
        Set<FactEager> facts = createFactsEager();
        directory.setFactEagers(facts);
        facts.forEach(e -> e.setDirectoryEager(directory));
        return directory;
    }

    private Set<FactEager> createFactsEager() {
        FactEager fact1 = new FactEager();
        fact1.setShortDescription("shortDescription1");
        fact1.setFullDescription("fullDescription1");
        fact1.setRate(5);
        fact1.setComment("comment1");

        FactEager fact2 = new FactEager();
        fact2.setShortDescription("shortDescription2");
        fact2.setFullDescription("fullDescription2");
        fact2.setRate(7);
        fact2.setComment("comment2");

        Set<FactEager> facts = new HashSet<>();
        facts.add(fact1);
        facts.add(fact2);
        return facts;
    }
}
