package it.uniroma3.siw.taskmanager.controller;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.controller.validation.CredentialsValidator;
import it.uniroma3.siw.taskmanager.controller.validation.UserValidator;
import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.CredentialsService;
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
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    SessionData sessionData;

    @Autowired
    UserValidator userValidator;

    @Autowired
    CredentialsValidator credentialsValidator;

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        model.addAttribute("user", loggedUser);
        return "home";
    }

    @RequestMapping(value = {"/users/me"}, method = RequestMethod.GET)
    public String me(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        Credentials credentials = sessionData.getLoggedCredentials();
        model.addAttribute("user", loggedUser);
        model.addAttribute("credentials", credentials);
        return "userProfile";
    }


    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String admin(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        model.addAttribute("user", loggedUser);
        return "admin";
    }

    @RequestMapping(value = {"/admin/users"}, method = RequestMethod.GET)
    public String usersList(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        List<Credentials> allCredentials = this.credentialsService.getAllCredentials();

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("credentialsList", allCredentials);

        return "allUsers";
    }


    @RequestMapping(value = {"/admin/users/{username}/delete"}, method = RequestMethod.POST)
    public String removeUser(Model model, @PathVariable String username) {
        this.credentialsService.deleteCredentials(username);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = {"users/modifyUser"}, method = RequestMethod.GET)
    public String goToModifyUser(Model model) {
        model.addAttribute("credentialsForm", sessionData.getLoggedCredentials());
        model.addAttribute("userForm", sessionData.getLoggedUser());
        return "modifyUser";
    }

    @RequestMapping(value = {"users/modifyUser"}, method = RequestMethod.POST)
    public String modifyUser(Model model, @Valid @ModelAttribute("userForm") User user,
                             BindingResult userBindingResult,
                             @Valid @ModelAttribute("credentialsForm") Credentials credentials,
                             BindingResult credentialsBindingResult) {

        Credentials oldCredentials = credentialsService.getCredentials(sessionData.getLoggedCredentials().getId());
        User oldUser = userService.getUser(sessionData.getLoggedUser().getId());

        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);//TODO

        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {

            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setLastUpdateTimeStamp(LocalDateTime.now());
            oldUser.setCreationTimeStamp(sessionData.getLoggedUser().getCreationTimeStamp());
            oldCredentials.setUsername(credentials.getUsername());
            oldCredentials.setPassword(credentials.getPassword());
           // oldCredentials.setRole(sessionData.getLoggedCredentials().getRole());
            oldCredentials.setUser(oldUser);
            sessionData.setLoggedUser(oldUser);
            sessionData.setLoggedCredentials(oldCredentials);
            credentialsService.saveCredentials(oldCredentials);
            return "modifySuccessful";
        }
        return "modifyUser";


    }


}
