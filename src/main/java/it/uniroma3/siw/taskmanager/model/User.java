package it.uniroma3.siw.taskmanager.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User {

    public User() {
        this.tasks = new ArrayList<Task>();
    }

    public User(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(List<Project> ownerProjects, List<Project> visibleProjects) {
        super();
        this.ownerProjects = ownerProjects;
        this.visibleProjects = visibleProjects;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDateTime creationTimeStamp;

    @Column(nullable = false)
    private LocalDateTime lastUpdateTimeStamp;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)//fechtype lazy di default
    private List<Project> ownerProjects;

    @ManyToMany(mappedBy = "members", cascade = CascadeType.REMOVE)//fechtype lazy di default
    private List<Project> visibleProjects;

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Task> tasks;

    @PrePersist
    protected void onPersist() {
        this.creationTimeStamp = LocalDateTime.now();
        this.lastUpdateTimeStamp = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateTimeStamp = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public LocalDateTime getLastUpdateTimeStamp() {
        return lastUpdateTimeStamp;
    }

    public void setLastUpdateTimeStamp(LocalDateTime lastUpdateTimeStamp) {
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    }

    public List<Project> getOwnerProjects() {
        return ownerProjects;
    }

    public void setOwnerProjects(List<Project> ownerProjects) {
        this.ownerProjects = ownerProjects;
    }

    public List<Project> getVisibleProjects() {
        return visibleProjects;
    }

    public void setVisibleProjects(List<Project> visibleProjects) {
        this.visibleProjects = visibleProjects;
    }

    public void addOwnerProject(Project ownerProject) {
        this.ownerProjects.add(ownerProject);
    }

    public void addVisibleProject(Project visibleProject) {
        this.visibleProjects.add(visibleProject);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Boolean addTask(Task task){
        return this.tasks.add(task);
    }

    @Override
    public String toString() {
        return "User [firstName=" + firstName + ", lastName="
                + lastName + ", creationTimeStamp=" + creationTimeStamp + ", lastUpdateTimeStamp=" + lastUpdateTimeStamp
                + ", ownerProjects=" + ownerProjects + ", visibleProjects=" + visibleProjects + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }

}
