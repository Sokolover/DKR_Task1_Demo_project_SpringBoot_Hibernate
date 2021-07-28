package com.sokolov.demo.repository;

import com.sokolov.demo.Task1SprintBootJpaApplication;
import com.sokolov.demo.model.student.eager.ContactInfoEager;
import com.sokolov.demo.model.student.eager.StudentEager;
import com.sokolov.demo.model.student.lazy.ContactInfoLazy;
import com.sokolov.demo.model.student.lazy.StudentLazy;
import com.sokolov.demo.repository.student.StudentRepositoryEager;
import com.sokolov.demo.repository.student.StudentRepositoryLazy;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Sokolov_SA
 * @created 19.07.2021
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Task1SprintBootJpaApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = "classpath:drop_create_student_contactInfo_table.sql")
class StudentLazyRepositoryImplTest_TaskA2 {

    private final StudentRepositoryLazy studentRepositoryLazy;
    private final StudentRepositoryEager studentRepositoryEager;

    @Test
    void create() {
        StudentLazy studentLazy = createStudent();
        Long newStudentId = studentRepositoryLazy.create(studentLazy);

        Optional<StudentLazy> optionalStudent = studentRepositoryLazy.findById(newStudentId);
        StudentLazy studentLazyFromDatabase = optionalStudent.orElseThrow(() -> new RuntimeException("not found"));

        Assertions.assertEquals(newStudentId, studentLazyFromDatabase.getId());
    }

    @Test
    void delete() {
        StudentLazy studentLazy = createStudent();
        Long newStudentId = studentRepositoryLazy.create(studentLazy);

        Optional<StudentLazy> optionalStudent = studentRepositoryLazy.findById(newStudentId);
        StudentLazy studentLazyFromDatabase = optionalStudent.orElseThrow(() -> new RuntimeException("not found"));

        Long deletedStudent = studentRepositoryLazy.delete(studentLazyFromDatabase.getId());

        Assertions.assertEquals(deletedStudent, studentLazyFromDatabase.getId());
    }

    @Test
    void update() {
        StudentLazy studentLazy = createStudent();
        Long newStudentId = studentRepositoryLazy.create(studentLazy);

        studentLazy.setFirstName("Heihachi");
        studentRepositoryLazy.update(studentLazy);

        Optional<StudentLazy> optionalStudent = studentRepositoryLazy.findById(newStudentId);
        StudentLazy updatedStudentLazyFromDatabase = optionalStudent.orElseThrow(() -> new RuntimeException("not found"));

        Assertions.assertEquals(studentLazy.getFirstName(), updatedStudentLazyFromDatabase.getFirstName());
    }

    @Test
    void findByIdLazy() {
        StudentLazy studentLazy = createStudent();
        Long newStudentId = studentRepositoryLazy.create(studentLazy);
        studentLazy.setId(newStudentId);

        Optional<StudentLazy> optionalStudent = studentRepositoryLazy.findById(newStudentId);
        StudentLazy studentLazyFromDatabase = optionalStudent.orElseThrow(() -> new RuntimeException("not found"));

        System.out.println(studentLazyFromDatabase);
        System.out.println(studentLazyFromDatabase.getContactInfoLazy());

        Assertions.assertEquals(studentLazy, studentLazyFromDatabase);
    }

    @Test
    void findAllLazy() {
        StudentLazy studentLazy1 = createStudent();
        StudentLazy studentLazy2 = createStudent();
        StudentLazy studentLazy3 = createStudent();

        studentLazy1.setFirstName("Jin");
        studentLazy2.setFirstName("Kazya");
        studentLazy3.setFirstName("Heihachi");

        List<StudentLazy> studentLazies = Arrays.asList(studentLazy1, studentLazy2, studentLazy3);
        studentLazies.forEach(studentRepositoryLazy::create);

        List<StudentLazy> studentLazyRepositoryAll = studentRepositoryLazy.findAll();

        studentLazyRepositoryAll.forEach(e -> {
            System.out.println(e);
            System.out.println(e.getContactInfoLazy());
        });

        Assertions.assertEquals(3, studentLazyRepositoryAll.size());
    }

    @Test
    void findByIdEager() {
        StudentEager studentEager = createStudentEager();
        Long newStudentId = studentRepositoryEager.create(studentEager);
        studentEager.setId(newStudentId);

        Optional<StudentEager> optionalStudent = studentRepositoryEager.findById(newStudentId);
        StudentEager studentEagerFromDatabase = optionalStudent.orElseThrow(() -> new RuntimeException("not found"));

        System.out.println(studentEagerFromDatabase);
        System.out.println(studentEagerFromDatabase.getContactInfoEager());

        Assertions.assertEquals(studentEager, studentEagerFromDatabase);
    }

    @Test
    void findAllEager() {
        StudentEager studentEager1 = createStudentEager();
        StudentEager studentEager2 = createStudentEager();
        StudentEager studentEager3 = createStudentEager();

        studentEager1.setFirstName("Jin");
        studentEager2.setFirstName("Kazya");
        studentEager3.setFirstName("Heihachi");

        List<StudentEager> studentLazies = Arrays.asList(studentEager1, studentEager2, studentEager3);
        studentLazies.forEach(studentRepositoryEager::create);

        List<StudentEager> studentRepositoryEagerAll = studentRepositoryEager.findAll();

        studentRepositoryEagerAll.forEach(e -> {
            System.out.println(e);
            System.out.println(e.getContactInfoEager());
        });

        Assertions.assertEquals(3, studentRepositoryEagerAll.size());
    }

    private StudentLazy createStudent() {
        StudentLazy studentLazy = new StudentLazy();
        studentLazy.setFirstName("Jin");
        studentLazy.setLastName("Kazama");

        ContactInfoLazy contactInfoLazy = createContactInfo();
        studentLazy.setContactInfoLazy(contactInfoLazy);
        studentLazy.getContactInfoLazy().setStudentLazy(studentLazy);

        return studentLazy;
    }

    private ContactInfoLazy createContactInfo() {
        ContactInfoLazy contactInfoLazy = new ContactInfoLazy();
        contactInfoLazy.setPhone("+375291234567");
        contactInfoLazy.setCity("Tokyo");
        return contactInfoLazy;
    }

    private StudentEager createStudentEager() {
        StudentEager studentEager = new StudentEager();
        studentEager.setFirstName("Jin");
        studentEager.setLastName("Kazama");

        ContactInfoEager contactInfoEager = createContactInfoEager();
        studentEager.setContactInfoEager(contactInfoEager);
        studentEager.getContactInfoEager().setStudentEager(studentEager);

        return studentEager;
    }

    private ContactInfoEager createContactInfoEager() {
        ContactInfoEager contactInfoEager = new ContactInfoEager();
        contactInfoEager.setPhone("+375291234567");
        contactInfoEager.setCity("Tokyo");
        return contactInfoEager;
    }

}