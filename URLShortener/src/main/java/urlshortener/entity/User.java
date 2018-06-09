package urlshortener.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String phoneNumber;
	private String firstName;
	private String lastName;
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user")
	private List<URL> URLs = new ArrayList<URL>();
	
	public User() {
	}

	public User(String phoneNumber, String firstName, String lastName, String password) {
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setRole(Role role) {
		this.roles = new ArrayList<Role>();
		this.roles.add(role);
	}

	public boolean isAdmin() {
		try {
			return (getRoles().stream().filter(r -> r.getName().equalsIgnoreCase("ADMIN")).findAny().get()) != null;
		} catch (NoSuchElementException e) {
		}
		return false;
	}

	public boolean isValid() {
		return StringUtils.isNotBlank(getPhoneNumber()) && StringUtils.isNotBlank(getFirstName())
				&& StringUtils.isNotBlank(getLastName()) && StringUtils.isNotBlank(getPassword());
	}
	
	public User getCopy() {
		return new User(phoneNumber, firstName, lastName, null);
	}

	public List<URL> getURLs() {
		return URLs;
	}

	public void setURLs(List<URL> uRLs) {
		URLs = uRLs;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", phoneNumber=" + phoneNumber + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", roles=" + roles + "]";
	}
}
