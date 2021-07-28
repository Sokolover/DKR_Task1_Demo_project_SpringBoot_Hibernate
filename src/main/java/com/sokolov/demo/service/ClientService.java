package com.sokolov.demo.service;

import com.sokolov.demo.model.Client;

import java.util.List;
import java.util.Map;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
public interface ClientService {

    List<Client> countSalaryGroupByAgeAscending();

    Map<Integer, Long> countRecordsGroupByAgeHavingLessThenConditionAscending(Long lessThanCondition);

}
