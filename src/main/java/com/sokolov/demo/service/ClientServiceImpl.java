package com.sokolov.demo.service;

import com.sokolov.demo.model.Client;
import com.sokolov.demo.repository.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    /**
     * SELECT age, SUM(salary) as sum FROM workers GROUP BY age.
     * http://old.code.mu/sql/group-by.html 1-1
     *
     * @return grouped client list
     */
    @Override
    public List<Client> countSalaryGroupByAgeAscending() {
        List<Client> all = clientRepository.findAll();

        Map<Integer, Map<Integer, Double>> groupedMap = all.stream()
                .collect(groupingBy(Client::getAge, groupingBy(Client::getAge, summingDouble(Client::getDoubleSalary))));

        List<Map<Integer, Double>> paramMap = new ArrayList<>();
        groupedMap.forEach((key, value) -> paramMap.add(value));

        return paramMap.stream().map(e -> e.entrySet()
                .stream()
                .map(this::buildClientFromParamMap)
                .findFirst()
                .orElse(new Client()))
                .collect(Collectors.toList());
    }

    /**
     * SELECT age, COUNT(*) as count FROM client GROUP BY age HAVING count <= 30 ORDER BY age;
     * http://old.code.mu/sql/having.html 2-2
     *
     * @return Map age:countRecords
     */
    @Override
    public Map<Integer, Long> countRecordsGroupByAgeHavingLessThenConditionAscending(Long lessThanCondition) {
        List<Client> all = clientRepository.findAll();

        return all.stream().collect(
                collectingAndThen(
                        groupingBy(
                                Client::getAge,
                                counting()),
                        e -> {
                            e.values().removeIf(i -> i > lessThanCondition);
                            return e;
                        }));
    }

    private Client buildClientFromParamMap(Map.Entry<Integer, Double> i) {
        Client client = new Client();
        Integer age = i.getKey();
        client.setAge(age);
        BigDecimal salary = BigDecimal.valueOf(i.getValue()).setScale(2, BigDecimal.ROUND_CEILING);
        client.setSalary(salary);
        return client;
    }
}