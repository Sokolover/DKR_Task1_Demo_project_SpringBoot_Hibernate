package com.sokolov.demo.repository.directory;

import com.sokolov.demo.model.directory.lazy.DirectoryLazy;
import com.sokolov.demo.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Sokolov_SA
 * @created 15.07.2021
 */
public interface DirectoryRepositoryLazy extends CrudRepository<DirectoryLazy> {

    Optional<DirectoryLazy> findByName(String name);

}
