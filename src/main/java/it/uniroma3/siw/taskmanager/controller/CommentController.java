package it.uniroma3.siw.taskmanager.controller;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Comment;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.service.CommentService;
import it.uniroma3.siw.taskmanager.service.ProjectService;
import it.uniroma3.siw.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class CommentController {

    @Autowired
    SessionData sessionData;

    @Autowired
    CommentService commentService;

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectService projectService;

   /* @RequestMapping(value = "{/task/{taskId}/addComment}", method = RequestMethod.GET)
    public String goToAddComment(Model model, @PathVariable Long taskId){

        model.addAttribute("task",taskService.getTask(taskId));
        model.addAttribute("loggedUser",sessionData.getLoggedUser());
        return "addComment";
    }*/

    @RequestMapping(value = {"/task/{taskId}/addComment"},method = RequestMethod.POST)
    public String addComment (Model model, @PathVariable Long taskId, @Valid @ModelAttribute("commentForm") Comment comment){
        Task task = taskService.getTask(taskId);
        task.addComment(comment);
        comment.setTask(task);
        Long projectId = this.projectService.projectFromTask(taskId);
        taskService.saveTask(task);
        return ("redirect:/projects/" + projectId + "/task/" + taskId);
    }

    @RequestMapping(value = {"/task/{taskId}/deleteComment/{commentId}"},method = RequestMethod.POST)
    public String deleteComment (Model model, @PathVariable("taskId") Long taskId, @PathVariable("commentId") Long commentId){
        Task task = taskService.getTask(taskId);
        Comment comment = commentService.getComment(commentId);
        task.getComments().remove(comment);
        commentService.deleteComment(comment);
        Long projectId = this.projectService.projectFromTask(taskId);
        return ("redirect:/projects/" + projectId + "/task/" + taskId);
    }
}
