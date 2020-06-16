package it.uniroma3.siw.taskmanager.repository;

import it.uniroma3.siw.taskmanager.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Long> {


}
