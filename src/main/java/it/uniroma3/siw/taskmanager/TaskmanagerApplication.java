package it.uniroma3.siw.taskmanager;

import it.uniroma3.siw.taskmanager.controller.validation.CredentialsValidator;
import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.CredentialsRepository;
import it.uniroma3.siw.taskmanager.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Renzo Cuomo -524782-
 */
@SpringBootApplication
public class TaskmanagerApplication {


	public static void main(String[] args) {

		SpringApplication.run(TaskmanagerApplication.class, args);

	}
	
}
