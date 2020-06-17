package it.uniroma3.siw.taskmanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Project {

	public Project() {
		this.members = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.tags = new ArrayList<>();
	}

	public Project(String name, String description) {
		super();
		this.members = new ArrayList<>();
		this.name = name;
		this.description = description;
	}

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;

	@Column (nullable = false)
	private String name;

	@Column (nullable = false)
	private String description;

	@ManyToOne//già EAGER di suo
	private User owner;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<User> members;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private List<Task> tasks;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Tag> tags;

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void addMember(User member) {
		this.members.add(member);
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Boolean addTag(Tag tag){
		return this.tags.add(tag);
	}

	public Boolean deleteMemeber(User user){
		return this.members.remove(user);
	}


	public List<Task> tasksWithoutTag(Tag tag) {
		List<Task> taskList = new ArrayList<>();
		for (Task t :
				this.getTasks()) {
			if (!t.getTags().contains(tag)) {
				taskList.add(t);
			}
		}
		return taskList;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + ", owner=" + owner
				+ ", members=" + members + ", tasks=" + tasks + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
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
		Project other = (Project) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}
