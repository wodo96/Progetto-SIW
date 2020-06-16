package it.uniroma3.siw.taskmanager.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Comment {

    public Comment() {
        super();
    }

    public Comment(String text) {
        super();
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column
    private LocalDateTime creationTimeStamp;

    @PrePersist
    protected void onPersist() {
        this.creationTimeStamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return getId().equals(comment.getId()) &&
                getText().equals(comment.getText()) &&
                Objects.equals(getCreationTimeStamp(), comment.getCreationTimeStamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText(), getCreationTimeStamp());
    }
}
