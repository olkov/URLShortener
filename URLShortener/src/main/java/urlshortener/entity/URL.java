package urlshortener.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import urlshortener.application.Utils;

@Entity
@Table(name = "urls")
public class URL {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String URL;
	private String shortURL;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private User user;

	public URL() {
	}

	public URL(String URL, User user) {
		this.URL = Utils.toCanonicalURL(URL);
		this.shortURL = Utils.generateShortURL();
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getShortURL() {
		return shortURL;
	}

	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "{\"id\":" + id + ",\"URL\":\"" + URL + "\",\"shortURL\":\"" + shortURL + "\"}";
	}
}
