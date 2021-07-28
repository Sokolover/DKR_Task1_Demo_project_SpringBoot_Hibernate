package com.sokolov.demo.model.student.eager;

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
@Table(name = "contact_info", schema = "public")
public class ContactInfoEager {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String city;
    private String phone;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEager studentEager;

}
