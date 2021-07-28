package com.sokolov.demo.model.student.lazy;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Sokolov_SA
 * @created 19.07.2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student", schema = "public")
public class StudentLazy {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "studentLazy", cascade = CascadeType.ALL)
    private ContactInfoLazy contactInfoLazy;

}
