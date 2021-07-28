package com.sokolov.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client", schema = "public")
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    private Integer age;
    private BigDecimal salary;

    public Client(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Need for grouping select in Hibernate task
     *
     * @param age    client's age
     * @param salary client's salary
     */
    public Client(Integer age, BigDecimal salary) {
        this.age = age;
        this.salary = salary;
    }

    public Double getDoubleSalary() {
        return salary.doubleValue();
    }
}
