package it.uniroma3.siw.taskmanager.controller.session;


import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    private Long temp_id;

    public void setTemp_id(Long temp_id){
        this.temp_id=temp_id;
    }

    public Long getTemp_id(){
        return this.temp_id;
    }


    /**
     * Currently logged User
     */
    private User user;

    /**
     * Credentials for the currently logged User
     */
    private Credentials credentials;

    @Autowired
    private CredentialsRepository credentialsRepository;

    /**
     * Retrieve from Session the credentials for the currently logged user.
     * If they are not stored in Session already, retrieve them from the SecurityContext and from the DB
     * and store them in session.
     */
    public Credentials getLoggedCredentials(){
        if(this.credentials == null){
            this.update();
        }
        return this.credentials;
    }

    public void setLoggedCredentials(Credentials credentials){
        this.credentials=credentials;
    }

    /**
     * Retrieve from Session the currently logged User.
     * If it is not stored in Session already, retrieve it from the DB and store it in session.
     */
    public User getLoggedUser() {
        if(this.user == null){
            this.update();
        }
        return this.user;
    }

    public void setLoggedUser(User user){
        this.user=user;
    }

    /**
     * Store the Credentials and User objects for the currently logged user in Session
     */
    private void update(){
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUserDetails = (UserDetails) obj;

        this.credentials = this.credentialsRepository.findByUsername(loggedUserDetails.getUsername()).get();
        this.credentials.setPassword("[PROTECTED]");
        this.user = this.credentials.getUser();
    }
}