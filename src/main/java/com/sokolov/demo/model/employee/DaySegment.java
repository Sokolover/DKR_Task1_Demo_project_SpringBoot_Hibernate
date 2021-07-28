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
@Table(name = "day_segment", schema = "public")
public class DaySegment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String period;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "working_day_to_day_segment",
            joinColumns = @JoinColumn(name = "day_segment_id"),
            inverseJoinColumns = @JoinColumn(name = "working_day_id"))
    private Set<WorkingDay> workingDaySet;

}
