package it.uniroma3.siw.taskmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.uniroma3.siw.taskmanager.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.UserRepository;

/**
 * 
 * Questo UserService mantiene la logica dell'user
 * @author Renzo
 */
@Service
	public class UserService {

	@Autowired
	protected UserRepository userRepository;

	@Transactional
	public User getUser (long id) {
		Optional<User> result = this.userRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	@Transactional
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		Iterable<User> iterable = this.userRepository.findAll();
		for (User user : iterable) {
			users.add(user);
		}
		return users;
	}

	public List<User> getMembers(Project project) {
		return project.getMembers();
	}
}
