package it.uniroma3.siw.taskmanager.repository;

import it.uniroma3.siw.taskmanager.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {

}
