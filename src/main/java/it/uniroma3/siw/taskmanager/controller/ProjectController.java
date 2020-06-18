package it.uniroma3.siw.taskmanager.controller;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.controller.validation.ProjectValidator;
import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.CredentialsService;
import it.uniroma3.siw.taskmanager.service.ProjectService;
import it.uniroma3.siw.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    ProjectValidator projectValidator;

    @Autowired
    SessionData sessionData;

    @RequestMapping(value = {"/projects"}, method = RequestMethod.GET)
    public String myOwnedProjects(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        List<Project> projectList = projectService.retrieveProjectsOwnedBy(loggedUser);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("projectsList", projectList);
        return "projects";
    }

    @RequestMapping(value = {"/sharedProjects"}, method = RequestMethod.GET)
    public String sharedProjects(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        List<Project> sharedProjectList = projectService.retrieveProjectsSharedBy(loggedUser);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("projectsShared", sharedProjectList);
        return "sharedProjects";
    }

    @RequestMapping(value = {"projects/{projectId}"}, method = RequestMethod.GET)
    public String project(Model model, @PathVariable Long projectId) {
        //if no project with passed ID exists,
        //redirect to the view with the list of my projects
        Project project = projectService.getProject(projectId);
        if (project == null) {
            return "redirect:/projects";
        }
        User loggedUser = sessionData.getLoggedUser();

        //if i don't have access to any projects with the passed ID,
        // redirect to the view with the list of my project
        List<User> members = userService.getMembers(project);
        if (!project.getOwner().equals(loggedUser) && !members.contains(loggedUser)) {
            return "redirect:/projects";
        }
        List<Credentials> credentialsMembers = this.credentialsService.getCredentialsByUsers(members);
        model.addAttribute("credentialsService",this.credentialsService);
        model.addAttribute("credentialsOwner", this.credentialsService.getCredentialByUserId(project.getOwner().getId()));
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("project", project);
        model.addAttribute("credentialsMembers", credentialsMembers);
        return "project";
    }

    @RequestMapping(value = {"/projects/add"}, method = RequestMethod.GET)
    public String createProjectForm(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("projectForm", new Project());
        return "addProject";
    }

    @RequestMapping(value = {"/projects/add"}, method = RequestMethod.POST)
    public String createProject(@Valid @ModelAttribute("projectForm") Project project, BindingResult projectBindingResult, Model model) {
        User loggedUser = sessionData.getLoggedUser();
        projectValidator.validate(project, projectBindingResult);
        if (!projectBindingResult.hasErrors()) {
            project.setOwner(loggedUser);
            this.projectService.saveProject(project);
            return ("redirect:/projects/" + project.getId());
        }
        model.addAttribute("loggedUser", loggedUser);
        return "addProject";
    }

    @RequestMapping(value = {"/projects/{projectId}/delete"}, method = RequestMethod.POST)
    public String removeProject(Model model, @PathVariable Long projectId) {
        this.projectService.deleteById(projectId);
        return "redirect:/projects";
    }

    @RequestMapping(value = {"/projects/{projectId}/shareProject"}, method = RequestMethod.GET)
    public String goShareProject(Model model, @PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        List<User> userAlreadyMember = project.getMembers();
        User loggedUser = sessionData.getLoggedUser();
        List<Credentials> credentials = this.credentialsService.getCredentialsByUsers(this.userService.removeMembersFromAllUsers(userAlreadyMember, loggedUser));
        model.addAttribute("project", project);
        model.addAttribute("credentials", credentials);
        return "shareProject";
    }

    @RequestMapping(value = {"/projects/{projectId}/shareProject/{userId}"}, method = RequestMethod.POST)
    public String shareProject(Model model, @PathVariable("userId") Long userId, @PathVariable("projectId") Long projectId) {
        Project currentProject = projectService.getProject(projectId);
        User user = userService.getUser(userId);
        currentProject.addMember(user);
        projectService.saveProject(currentProject);
        return "modifySuccessful";
    }

    @RequestMapping(value = {"/projects/{projectId}/modifyProject"}, method = RequestMethod.GET)
    public String goToModifyProject(Model model, @PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        model.addAttribute("projectForm", project);

        return "modifyProject";
    }

    @RequestMapping(value = {"/projects/{projectId}/modifyProject"}, method = RequestMethod.POST)
    public String modifyProject(Model model, @Valid @ModelAttribute("projectForm") Project project, BindingResult projectBindingResult,
                                @PathVariable Long projectId) {
        Project currentProject = projectService.getProject(projectId);
        this.projectValidator.validate(project, projectBindingResult);
        if (!projectBindingResult.hasErrors()) {
            currentProject.setName(project.getName());
            currentProject.setDescription(project.getDescription());
            this.projectService.saveProject(currentProject);
            return ("redirect:/projects/" + projectId);
        }
        return "modifyProject";
    }

}
