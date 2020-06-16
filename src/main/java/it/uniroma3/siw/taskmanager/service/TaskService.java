package it.uniroma3.siw.taskmanager.service;

import java.util.List;
import java.util.Optional;

import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    protected UserService userService;

    @Transactional
    public Task getTask(long id) {
        Optional<Task> result = this.taskRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Task saveTask(Task task) {
        return this.taskRepository.save(task);
    }

    @Transactional
    public Task setCompleted(Task task) {
        task.setCompleted(true);
        return this.taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Task task) {
        this.taskRepository.delete(task);
    }

}
