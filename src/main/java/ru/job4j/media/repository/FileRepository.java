package ru.job4j.media.repository;

import ru.job4j.media.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Integer> {
}
