package com.sokolov.demo.repository.employee;

import com.sokolov.demo.model.employee.Employee;
import com.sokolov.demo.repository.CrudRepository;

import java.util.List;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
public interface EmployeeRepository extends CrudRepository<Employee> {

    List<Employee> findAll(int startPage, int pageSize);

}
