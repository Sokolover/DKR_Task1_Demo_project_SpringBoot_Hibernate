package com.sokolov.demo.repository.client;

import com.sokolov.demo.model.Client;
import com.sokolov.demo.repository.CrudRepository;

import java.util.List;
import java.util.Map;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
public interface ClientRepository extends CrudRepository<Client> {

    List<Client> countSalaryByAgeAscending();

    Map<Integer, Long> countRecordsGroupByAgeHavingLessThenConditionAscending(Long lessThanCondition);

    List<Client> selectClientsWhereSalaryEqualsMinSalaryOrderBySecondNameDesc();

}
