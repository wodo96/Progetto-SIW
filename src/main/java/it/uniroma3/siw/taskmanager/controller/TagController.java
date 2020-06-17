package it.uniroma3.siw.taskmanager.controller;


import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Tag;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.service.ProjectService;
import it.uniroma3.siw.taskmanager.service.TagService;
import it.uniroma3.siw.taskmanager.service.TaskService;
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
public class TagController {

    @Autowired
    TagService tagService;

    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @RequestMapping(value = {"/projects/{projectId}/addTag"}, method = RequestMethod.GET)
    public String goToAddTagFromProject(Model model, @PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        model.addAttribute("tag", new Tag());
        model.addAttribute("entity", project);
        return "addTag";
    }

    @RequestMapping(value = {"/target/{id}/tags/addTag"}, method = RequestMethod.POST)
    public String addTag(Model model, @PathVariable("id") Long id, @Valid @ModelAttribute("tag") Tag tag) {
        Project project = this.projectService.getProject(id);
        project.addTag(tag);
        this.projectService.saveProject(project);
        return ("redirect:/projects/" + project.getId());
    }

    @RequestMapping(value= {"/projects/{projectId}/tag/{tagId}/assignTag"}, method=RequestMethod.GET)
    public String goToAssignTag(Model model, @PathVariable ("projectId")Long projectId, @PathVariable("tagId")Long tagId){
        Tag tag = tagService.getTag(tagId);
        Project project = projectService.getProject(projectId);
        model.addAttribute("availableTasks", project.tasksWithoutTag(tag));
        model.addAttribute("project",project);
        model.addAttribute("tag",tag);
        return "assignTag";
    }

    @RequestMapping(value = {"/tasks/{taskId}/tag/{tagId}/assignTag"},method = RequestMethod.POST)
    public String assignTag(Model model, @PathVariable ("taskId")Long taskId, @PathVariable("tagId")Long tagId){
        Tag tag = tagService.getTag(tagId);
        Task task = taskService.getTask(taskId);
        task.addTag(tag);
        tag.addTask(task);
        tagService.saveTag(tag);
        taskService.saveTask(task);
        return ("redirect:/tasks/" + taskId);
    }

}
