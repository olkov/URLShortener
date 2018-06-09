package urlshortener.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Boolean defaultRole;
	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public Role() {}

	public Role(String name) {
		this.name = name;
	}

	public Role(Long id, String name, Boolean defaultRole) {
		this.id = id;
		this.name = name;
		this.defaultRole = defaultRole;
	}
	
	public Role(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.defaultRole = false;
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

	public Boolean isDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(Boolean defaultRole) {
		this.defaultRole = defaultRole;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
