package it.uniroma3.siw.taskmanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;

public interface ProjectRepository extends CrudRepository<Project, Long>{

	public List<Project> findByMembers(User member);
	
	public List<Project> findByOwner(User owner);
	
	
}