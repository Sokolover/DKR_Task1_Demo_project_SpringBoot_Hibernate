package com.sokolov.demo.repository;

import com.sokolov.demo.Task1SprintBootJpaApplication;
import com.sokolov.demo.model.Client;
import com.sokolov.demo.repository.client.ClientRepository;
import com.sokolov.demo.utils.FillingClientScript;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author Sokolov_SA
 * @created 20.07.2021
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Task1SprintBootJpaApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@Sql(value = "classpath:drop_create_client_table.sql")
class ClientRepositoryImplTest_TaskC {

    private final ClientRepository clientRepository;
    private final FillingClientScript fillingClientScript;

//    @BeforeAll
//    void initDatabase() {
//        fillingClientScript.fillClientTable();
//    }

    @Test
    void selectClientsWhereSalaryLessThenAverageOrderByLastName() {
        List<Client> clients = clientRepository.selectClientsWhereSalaryEqualsMinSalaryOrderBySecondNameDesc();

        System.out.println(clients.size());
    }
}