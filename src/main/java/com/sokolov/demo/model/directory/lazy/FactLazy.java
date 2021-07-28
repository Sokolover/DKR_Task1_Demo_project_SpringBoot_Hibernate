package com.sokolov.demo.model.directory.lazy;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Sokolov_SA
 * @created 15.07.2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fact", schema = "public")
public class FactLazy {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "full_description")
    private String fullDescription;
    private Integer rate;
    private String comment;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "directory_id")
    private DirectoryLazy directoryLazy;

}
