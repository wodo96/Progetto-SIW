package it.uniroma3.siw.taskmanager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Task {

    public Task() {
        super();
        this.tags = new ArrayList<Tag>();
        this.comments = new ArrayList<Comment>();
    }

    public Task(String name, String description, Boolean completed) {
        super();
        this.name = name;
        this.description = description;
        this.completed = completed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean completed;

    @Column(nullable = false)
    private LocalDateTime creationTimeStamp;

    @Column(nullable = false)
    private LocalDateTime lastUpdateTimeStamp;

    @ManyToMany(mappedBy = "tasks")
    private List<Tag> tags;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private List<Comment> comments;

    @PrePersist
    protected void onPersist() {
        this.creationTimeStamp = LocalDateTime.now();
        this.lastUpdateTimeStamp = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateTimeStamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Boolean addTag(Tag tag){
        return this.tags.add(tag);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userAssigned) {
        this.user = userAssigned;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Boolean addComment(Comment comment) {
        return this.comments.add(comment);
    }


    @Override
    public String toString() {
        return "Task [id=" + id + ", name=" + name + ", description=" + description + ", completed=" + completed
                + ", creationTimeStamp=" + creationTimeStamp + ", lastUpdateTimeStamp=" + lastUpdateTimeStamp + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Task other = (Task) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}