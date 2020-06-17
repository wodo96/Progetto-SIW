package it.uniroma3.siw.taskmanager.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import it.uniroma3.siw.taskmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.repository.CredentialsRepository;

@Service
public class CredentialsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredentialsRepository credentialsRepository;

    @Transactional
    public Credentials getCredentials(long id) {
        Optional<Credentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Credentials getCredentials(String username) {
        Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }

    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

    @Transactional
    public Credentials updateCredentials(Credentials credentials, String role) {
        if (role.equals(Credentials.ADMIN_ROLE)) {
            credentials.setRole(Credentials.ADMIN_ROLE);
        } else {
            credentials.setRole(Credentials.DEFAULT_ROLE);
        }
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

    @Transactional
    public List<Credentials> getAllCredentials() {
        Iterable<Credentials> iterable = this.credentialsRepository.findAll();
        List<Credentials> credentials = new ArrayList<Credentials>();
        for (Credentials c :
                iterable) {
            credentials.add(c);
        }
        return credentials;
    }

    @Transactional
    public void deleteCredentials(String username) {
        credentialsRepository.deleteByUsername(username);
    }

    @Transactional
    public Credentials getCredentialByUserId(Long userId) {
        return this.credentialsRepository.findCredentialsByUserId(userId);
    }

    @Transactional
    public List<Credentials> getCredentialsByUsers(List<User> users) {
        List<Credentials> credentials = new ArrayList<>();
        for (User u :
                users) {
            Credentials c = this.credentialsRepository.findCredentialsByUserId(u.getId());
            credentials.add(c);
        }
        return credentials;
    }
}
