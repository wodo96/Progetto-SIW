package it.uniroma3.siw.taskmanager.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Tag {

    public Tag() {
        super();
    }

    public Tag(String name, String color, String description) {
        super();
        this.name = name;
        this.color = color;
        this.description = description;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String color;

    @Column(nullable = false)
    private String description;

    @ManyToMany
    private List<Task> tasks;

    @ManyToOne
    private Project project;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return getId().equals(tag.getId()) &&
                getName().equals(tag.getName()) &&
                Objects.equals(getDescription(), tag.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }
}
