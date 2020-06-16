package it.uniroma3.siw.taskmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	protected ProjectRepository projectRepository;


	@Transactional
	public Project getProject(long id) {
		Optional<Project> result = this.projectRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Project saveProject(Project project) {
		return this.projectRepository.save(project);
	}

	@Transactional
	public void deleteProject(Project project) {
		this.projectRepository.delete(project);
	}

	@Transactional
	public Project shareProjectWithUser (Project project, User user) {
		project.addMember(user);
		return this.projectRepository.save(project);
	}


	public List<Project> retrieveProjectsOwnedBy(User loggedUser) {
		return this.projectRepository.findByOwner(loggedUser);
	}

	public List<Project> retrieveProjectsSharedBy(User loggedUser){
		return  this.projectRepository.findByMembers(loggedUser);
	}


	public void deleteById(Long id){
		this.projectRepository.deleteById(id);
	}

	public Long projectFromTask(Long id){
		return this.projectRepository.findproject_idFromId(id);
	}
}