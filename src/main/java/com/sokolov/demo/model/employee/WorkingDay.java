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
@Table(name = "working_day", schema = "public")
public class WorkingDay {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "day_name")
    private String dayName;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "timesheet_id")
    private Timesheet timesheet;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "working_day_to_day_segment",
            joinColumns = @JoinColumn(name = "working_day_id"),
            inverseJoinColumns = @JoinColumn(name = "day_segment_id"))
    private Set<DaySegment> daySegmentSet;

}
