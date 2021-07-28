package com.sokolov.demo.model.employee;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "timesheet", schema = "public")
public class Timesheet {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "day_amount")
    private Integer dayAmount;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "timesheet", cascade = CascadeType.ALL)
    private Set<WorkingDay> workingDaySet;

}
