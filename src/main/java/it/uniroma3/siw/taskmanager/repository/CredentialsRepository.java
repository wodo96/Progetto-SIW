package it.uniroma3.siw.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.taskmanager.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long>{

	/**
	 * Restituisce uno User tramite il suo username
	 * @param username
	 * @return an Optional for the User with the passed userName
	 */
	public Optional<Credentials> findByUsername(String username);

	/**
	 * Elimina uno User tramite il suo username
	 * @param username
	 */
	public void deleteByUsername(String username);

}
