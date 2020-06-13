package it.uniroma3.siw.taskmanager.controller;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    SessionData sessionData;

    @Autowired
    CredentialsService credentialsService;

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        model.addAttribute("user",loggedUser);
        return "home";
    }

    @RequestMapping(value = {"/users/me"}, method = RequestMethod.GET)
    public String me(Model model){
        User loggedUser = sessionData.getLoggedUser();
        Credentials credentials = sessionData.getLoggedCredentials();
        model.addAttribute("user",loggedUser);
        model.addAttribute("credentials",credentials);
        return "userProfile";
    }


    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String admin(Model model){
        User loggedUser = sessionData.getLoggedUser();
        model.addAttribute("user",loggedUser);
        return "admin";
    }

    @RequestMapping(value = {"/admin/users"},method = RequestMethod.GET)
    public String usersList(Model model){
        User loggedUser = sessionData.getLoggedUser();
        List<Credentials> allCredentials = this.credentialsService.getAllCredentials();

        model.addAttribute("loggedUser",loggedUser);
        model.addAttribute("credentialsList",allCredentials);

        return "allUsers";
    }


    @RequestMapping(value = {"/admin/users/{username}/delete"}, method = RequestMethod.POST)
    public String removeUser (Model model, @PathVariable String username){
        this.credentialsService.deleteCredentials(username);
        return "redirect:/admin/users";
    }


}
