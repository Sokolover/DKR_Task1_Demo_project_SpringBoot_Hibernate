package com.sokolov.demo.repository;

import com.google.gson.Gson;
import com.sokolov.demo.Task1SprintBootJpaApplication;
import com.sokolov.demo.converter.EmployeeConverter;
import com.sokolov.demo.dto.EmployeeDto;
import com.sokolov.demo.model.employee.Employee;
import com.sokolov.demo.repository.employee.EmployeeRepository;
import com.sokolov.demo.utils.FillingEmployeeScripts;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sokolov_SA
 * @created 02.07.2021
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Task1SprintBootJpaApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@Sql(value = "classpath:drop_create_employee_timesheet_table.sql")
class EmployeeRepositoryImplTest_TaskB {

    private final EmployeeRepository employeeRepository;
    private final FillingEmployeeScripts fillingEmployeeScripts;
    private final EmployeeConverter employeeConverter;

//    @BeforeAll
//    void initDatabase() {
//        fillingDatabaseScripts.createEmployees();
//        fillingDatabaseScripts.fillTable_DaySegment();
//        fillingDatabaseScripts.fillTable_WorkingDay_DaySegment();
//    }

    @Test
    void findById() throws IOException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(1);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            EmployeeDto employeeDto = employeeConverter.convertToDto(employee);

            String result = new Gson().toJson(employeeDto);
            writeResultToFile("D:\\findEmployeeById.json", result);
        }
    }

    @Test
    void findAll() throws IOException {
        List<Employee> all = employeeRepository.findAll();
        List<EmployeeDto> collect = all.stream()
                .map(employeeConverter::convertToDto)
                .collect(Collectors.toList());

        String result = new Gson().toJson(collect);
        writeResultToFile("D:\\findAllEmployee.json", result);
    }

    @Test
    void findAllPagination_startPage0_pageSize10() throws IOException {
        int startPage = 0;
        int pageSize = 10;

        List<EmployeeDto> result = findAllEmployeeWithPagination(startPage, pageSize);
        String fileName = String.format("D:\\findAllEmployee_page_%d.json", startPage);
        String jsonResult = new Gson().toJson(result);
        writeResultToFile(fileName, jsonResult);
    }

    @Test
    void findAllPagination_startPage10_pageSize10() throws IOException {
        int startPage = 10;
        int pageSize = 10;

        List<EmployeeDto> result = findAllEmployeeWithPagination(startPage, pageSize);
        String fileName = String.format("D:\\findAllEmployee_page_%d.json", startPage);
        String jsonResult = new Gson().toJson(result);
        writeResultToFile(fileName, jsonResult);
    }

    private List<EmployeeDto> findAllEmployeeWithPagination(int startPage1, int pageSize) {
        return employeeRepository.findAll(startPage1, pageSize).stream()
                .map(employeeConverter::convertToDto)
                .collect(Collectors.toList());
    }

    private void writeResultToFile(String fileName, String result) throws IOException {
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(result);
        fileWriter.close();
    }
}