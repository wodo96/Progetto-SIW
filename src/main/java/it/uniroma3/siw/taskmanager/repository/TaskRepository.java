package it.uniroma3.siw.taskmanager.repository;

import it.uniroma3.siw.taskmanager.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.taskmanager.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long>{

    public void deleteById(Long taskId);



}