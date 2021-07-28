package com.sokolov.demo.repository;

import com.sokolov.demo.Task1SprintBootJpaApplication;
import com.sokolov.demo.model.Client;
import com.sokolov.demo.repository.client.ClientRepository;
import com.sokolov.demo.utils.FillingClientScript;
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
 * Test single table without relationships
 *
 * @author Sokolov_SA
 * @created 06.07.2021
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Task1SprintBootJpaApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = "classpath:drop_create_client_table.sql")
class ClientRepositoryImplTest_TaskA1 {

    private final ClientRepository clientRepository;

    @Test
    void create() {
        Client client = new Client();
        client.setFirstName("Ivan");
        Long newClientId = clientRepository.create(client);

        Optional<Client> optionalClient = clientRepository.findById(newClientId);
        Client clientFromDatabase = optionalClient.orElseThrow(() -> new RuntimeException("not found"));

        Assertions.assertEquals(newClientId, clientFromDatabase.getId());
    }

    @Test
    void delete() {
        Client client = new Client();
        client.setFirstName("Ivan");
        Long newClientId = clientRepository.create(client);

        Optional<Client> optionalClient = clientRepository.findById(newClientId);
        Client clientFromDatabase = optionalClient.orElseThrow(() -> new RuntimeException("not found"));

        Long deletedClient = clientRepository.delete(clientFromDatabase.getId());

        Assertions.assertEquals(deletedClient, clientFromDatabase.getId());
    }

    @Test
    void update() {
        Client client = new Client();
        client.setFirstName("Ivan");
        Long newClientId = clientRepository.create(client);

        client.setFirstName("Sergey");
        clientRepository.update(client);

        Optional<Client> optionalClient = clientRepository.findById(newClientId);
        Client updatedClientFromDatabase = optionalClient.orElseThrow(() -> new RuntimeException("not found"));

        Assertions.assertEquals(client.getFirstName(), updatedClientFromDatabase.getFirstName());
    }

    @Test
    void findById() {
        Client client = new Client();
        client.setFirstName("Ivan");
        Long newClientId = clientRepository.create(client);
        client.setId(newClientId);

        Optional<Client> optionalClient = clientRepository.findById(newClientId);
        Client clientFromDatabase = optionalClient.orElseThrow(() -> new RuntimeException("not found"));

        Assertions.assertEquals(client, clientFromDatabase);
    }

    @Test
    void findAll() {
        List<Client> clients = Arrays.asList(
                new Client("Ivan"),
                new Client("Sergey"),
                new Client("Andrew"));
        clients.forEach(clientRepository::create);

        List<Client> clientsFromDatabase = clientRepository.findAll();

        Assertions.assertEquals(3 + FillingClientScript.CLIENT_AMOUNT, clientsFromDatabase.size());
    }

}