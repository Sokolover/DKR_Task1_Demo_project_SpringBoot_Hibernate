package com.sokolov.demo.service;

import com.google.gson.Gson;
import com.sokolov.demo.Task1SprintBootJpaApplication;
import com.sokolov.demo.model.Client;
import com.sokolov.demo.repository.client.ClientRepository;
import com.sokolov.demo.utils.FillingClientScript;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Task1SprintBootJpaApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ClientServiceImplTest_TaskB3 {

    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final FillingClientScript fillingClientScript;

//    @BeforeAll
//    void initDatabase() {
//        fillingClientScript.fillClientTable();
//    }

    @Test
    void countSalaryByAgeAscending_StreamAPI() throws IOException {
        List<Client> clients = clientService.countSalaryGroupByAgeAscending();

        writeAnswerToJsonFile(clients, "result/countSalaryByAge_StreamAPI.json");
    }

    @Test
    void countSalaryByAgeAscending_JPA() throws IOException {
        List<Client> clients = clientRepository.countSalaryByAgeAscending();

        writeAnswerToJsonFile(clients, "result/countSalaryByAge_JPA.json");
    }

    @Test
    void countRecordsGroupByAgeHavingLessThenConditionAscending_StreamAPI() throws IOException {
        Map<Integer, Long> result = clientService.countRecordsGroupByAgeHavingLessThenConditionAscending(320L);
        Map<Integer, Long> sortedResult = getSortedResult(result);

        writeAnswerToJsonFile(sortedResult, "result/countRecordsGroupByAgeHavingLessThenConditionAscending_StreamAPI.json");
    }

    @Test
    void countRecordsGroupByAgeHavingLessThenConditionAscending_JPA() throws IOException {
        Map<Integer, Long> result = clientRepository.countRecordsGroupByAgeHavingLessThenConditionAscending(320L);
        Map<Integer, Long> sortedResult = getSortedResult(result);

        writeAnswerToJsonFile(sortedResult, "result/countRecordsGroupByAgeHavingLessThenConditionAscending_JPA.json");
    }

    private LinkedHashMap<Integer, Long> getSortedResult(Map<Integer, Long> result) {
        return result.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private void writeAnswerToJsonFile(Map toWrite, String fileName) throws IOException {
        Gson gson = new Gson();
        String result = gson.toJson(toWrite);

        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(result);
        fileWriter.close();
    }

    private void writeAnswerToJsonFile(List<Client> clients, String fileName) throws IOException {
        Gson gson = new Gson();
        String result = gson.toJson(clients);

        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(result);
        fileWriter.close();
    }
}