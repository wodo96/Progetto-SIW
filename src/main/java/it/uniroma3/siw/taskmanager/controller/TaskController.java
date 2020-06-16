package it.uniroma3.siw.taskmanager.controller;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.ProjectService;
import it.uniroma3.siw.taskmanager.service.TaskService;
import it.uniroma3.siw.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    SessionData sessionData;

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = {"/projects/{projectId}/addTask"}, method = RequestMethod.GET)
    public String goToAddTask(Model model, @PathVariable Long projectId){
        Project project = projectService.getProject(projectId);
        model.addAttribute("project",project);
        model.addAttribute("task", new Task());
        return "addTask";
    }

    @RequestMapping(value = {"/projects/{projectId}/addTask"}, method = RequestMethod.POST)
    public String addTask(Model model, @PathVariable Long projectId, @Valid @ModelAttribute("task") Task task){
        Project project = projectService.getProject(projectId);
        task.setCompleted(false);
        project.addTask(task);

        projectService.saveProject(project);
        return "redirect:/projects/"+projectId;
    }

    @RequestMapping(value = {"/projects/{projectId}/task/{taskId}"}, method = RequestMethod.GET)
    public String goToTask(Model model, @PathVariable Long projectId, @PathVariable Long taskId){
        Project project = projectService.getProject(projectId);
        Task task = taskService.getTask(taskId);
        List<Tag> tags = task.getTags();
        model.addAttribute("project", project);
        model.addAttribute("task", task);
        model.addAttribute("tags",tags);//TODO
        return "task";
    }

    @RequestMapping(value = {"/task/{taskId}/modifyTask"}, method = RequestMethod.GET)
    public  String goToModifyTask(Model model, @PathVariable Long taskId){
        Task task = taskService.getTask(taskId);
        model.addAttribute("task", task);
        return "modifyTask";
    }

    @RequestMapping(value = {"/task/{taskId}/modifyTask"}, method = RequestMethod.POST)
    public String modifyTask (Model model, @PathVariable Long taskId, @Valid @ModelAttribute("task") Task task){
        Task currentTask = taskService.getTask(taskId);
        currentTask.setName(task.getName());
        currentTask.setDescription(task.getDescription());
        taskService.saveTask(currentTask);
        return "modifySuccessful";
    }

    @RequestMapping(value = {"/task/{taskId}/delete"}, method = RequestMethod.POST)
    public String removeTask(Model model, @PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);
        Long projectId = projectService.projectFromTask(taskId);
        this.taskService.deleteTask(task);
        //model.addAttribute("projectId",projectId);
        //model.addAttribute("loggedUser",sessionData.getLoggedUser());
       // model.addAttribute("members",project.getMembers());

        return ("redirect:/projects/"+projectId);
    }

    @RequestMapping(value = {"/task/{taskId}/assignTask"}, method = RequestMethod.GET)
    public String goToAssignTask(Model model, @PathVariable Long taskId){
        Task task = taskService.getTask(taskId);
        Project project = projectService.getProject(projectService.projectFromTask(taskId));
        model.addAttribute("task",task);
        model.addAttribute("usersList",project.getMembers());
        return "assignTask";
    }

    @RequestMapping(value = {"/task/{taskId}/assignTask/{userId}"}, method = RequestMethod.POST)
    public String assignTask(Model model, @PathVariable Long userId, @PathVariable Long taskId){
        Task task = taskService.getTask(taskId);
        User user = userService.getUser(userId);
        user.addTask(task);
        task.setUser(user);
        userService.saveUser(user);
        return "modifySuccessful";
    }

    @RequestMapping(value = {"/tasksAssigned"}, method = RequestMethod.GET)
    public String tasksAssigned(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        List<Task> tasks = loggedUser.getTasks();
        model.addAttribute("tasksAssigned",tasks);
        model.addAttribute("loggedUser", loggedUser);
        return "tasksAssigned";
    }

    @RequestMapping(value = {"/tasks/{taskId}"}, method = RequestMethod.GET)
    public String task (Model model, @PathVariable Long taskId){
        Project project = this.projectService.getProject(this.projectService.projectFromTask(taskId));
        return ("redirect:/projects/" + project.getId() + "/task/" + taskId);
    }



}
