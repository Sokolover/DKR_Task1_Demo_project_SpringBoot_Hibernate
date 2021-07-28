package com.sokolov.demo.model.directory.lazy;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Sokolov_SA
 * @created 15.07.2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "directory", schema = "public")
public class DirectoryLazy {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String address2;
    @Column(name = "city_id")
    private String cityId;
    private String phone;
    private String postcode;
    private String district;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "directoryLazy", cascade = CascadeType.ALL)
    private Set<FactLazy> factLazies;

}
