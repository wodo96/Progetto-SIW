package it.uniroma3.siw.taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.repository.CredentialsRepository;
import it.uniroma3.siw.taskmanager.service.CredentialsService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.ProjectRepository;
import it.uniroma3.siw.taskmanager.repository.TaskRepository;
import it.uniroma3.siw.taskmanager.repository.UserRepository;
import it.uniroma3.siw.taskmanager.service.ProjectService;
import it.uniroma3.siw.taskmanager.service.TaskService;
import it.uniroma3.siw.taskmanager.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class TaskmanagerApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CredentialsRepository credentialsRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectRepository projectRepository;
	
	@Before
	public void deleteAll() {
		System.out.println("Deleting all data in DB");
		userRepository.deleteAll();
		credentialsRepository.deleteAll();
		taskRepository.deleteAll();
		projectRepository.deleteAll();
		System.out.println("Done");
	}

	@Test
	void testUpdateUser() {
		
		//save first user in DB
		User user1 = new User("Mario", "Rossi");
		Credentials c1 = new Credentials("wodo","password","ADMIN");
		user1 = userService.saveUser(user1);
		c1.setUser(user1);
		c1 = credentialsService.saveCredentials(c1);


		assertEquals(user1.getId().longValue(), 1L);
		assertEquals(user1.getFirstName(), "Mario");
		assertEquals(user1.getLastName(), "Rossi");
		
	/*	//update first user in DB
		User user1Updated = new User("Maria", "Rossi");
		user1Updated.setId(user1.getId());
		user1Updated.setCreationTimeStamp(user1.getCreationTimeStamp());
		user1 = userService.saveUser(user1Updated);
		User user1UpdatedResult = userService.getUser(user1Updated.getId());
		user1Updated =userService.saveUser(user1Updated);
		user1Updated = userService.getUser(user1Updated.getId());
		assertEquals(user1Updated.getId().longValue(), 1L);
		assertEquals(user1Updated.getUsername(), "mariarossi");
		assertEquals(user1Updated.getFirstName(), "Maria");
		assertEquals(user1Updated.getLastName(), "Rossi");
		
		//save second user in DB
		User user2 = new User("lucabianchi", "password", "Luca", "Bianchi");
		user2 = userService.saveUser(user2);
		assertEquals(user2.getId().longValue(), 2L);
		assertEquals(user2.getUsername(), "lucabianchi");
		assertEquals(user2.getFirstName(), "Luca");
		assertEquals(user2.getLastName(), "Bianchi");
		
		//save first project in DB with first user
		Project project1 = new Project("TestProject1", "Just a little test Project");
		project1.setOwner(user1);
		project1 = projectService.saveProject(project1);
		assertEquals(project1.getOwner(), user1);
		assertEquals(project1.getName(), "TestProject1");
		assertEquals(project1.getDescription(), "Just a little test Project");
		
		//save second project in DB with first user
		Project project2 = new Project("TestProject2", "Just another little test Project");
		project2.setOwner(user1);
		project2 = projectService.saveProject(project2);
		assertEquals(project2.getOwner(), user1);
		assertEquals(project2.getName(), "TestProject2");
		assertEquals(project2.getDescription(), "Just another little test Project");
		
		//give visibility over project 1 to user 2
		project1 = projectService.shareProjectWithUser(project1, user2);
		
		//test projects owned by user 1
		List<Project> projects = projectRepository.findByOwner(user1Updated);
		assertEquals(projects.size(), 2);
		assertEquals(projects.get(0).getId(), project1.getId());
		assertEquals(projects.get(1).getId(), project2.getId());
		
		//test projects visible by user 2
		List<User> project1members = userRepository.findByVisibleProjects(project1);
		assertEquals(project1members.size(), 1);
		assertEquals(project1members.get(0), user2);
		
		List<Project> projectsVisibleByUser2 = projectRepository.findByMembers(user2);
		assertEquals(projectsVisibleByUser2.size(), 1);
		assertEquals(projectsVisibleByUser2.get(0).getId(), project1.getId());
		
		*/
		
		
		
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
