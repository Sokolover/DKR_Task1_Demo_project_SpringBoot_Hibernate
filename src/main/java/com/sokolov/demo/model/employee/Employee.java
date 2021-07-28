package com.sokolov.demo.model.employee;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "employee", schema = "public")
public class Employee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Timesheet timesheet;

}
