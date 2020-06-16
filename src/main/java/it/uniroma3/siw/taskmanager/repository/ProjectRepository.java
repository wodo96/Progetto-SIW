package it.uniroma3.siw.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;

public interface ProjectRepository extends CrudRepository<Project, Long>{

	public List<Project> findByMembers(User member);
	
	public List<Project> findByOwner(User owner);

	public void deleteById(Long id);

	@Query(value = "SELECT project_id FROM task where id= ?1", nativeQuery=true)
	public Long findproject_idFromId(Long taskId);

}