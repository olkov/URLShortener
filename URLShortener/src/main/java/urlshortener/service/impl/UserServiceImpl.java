package urlshortener.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import urlshortener.application.WebApplication;
import urlshortener.dao.RoleDao;
import urlshortener.dao.UserDao;
import urlshortener.entity.Role;
import urlshortener.entity.User;
import urlshortener.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDAO;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public User saveUser(User user) {
		if (user != null && user.isValid()) {
			if (user.getRoles() == null || (user.getRoles() != null && user.getRoles().isEmpty())) {
				Role role = WebApplication.roles.stream().filter(r -> r.isDefaultRole()).findFirst().get();
				user.setRole(role);
			}
			user.setPassword(encoder.encode(user.getPassword()));
			return userDAO.save(user);
		}
		return null;
	}

	public boolean deleteUser(Long id) {
		User user = userDAO.getOne(id);
		if (user != null) {
			user.setRole(null);
			userDAO.delete(user);
			return true;
		}
		return false;
	}

	public List<User> getAllUsers() {
		return userDAO.findAll();
	}

	public User getUserById(Long userId) {
		if(userDAO.existsById(userId)) {
			return userDAO.getOne(userId);
		}
		return null;
	}

	@Cacheable("principalUser")
	public User getUserByPrincipal(Principal principal) {
		if (principal != null) {
			return getUserByPhoneNumber(principal.getName());
		}
		return null;
	}

	public User getUserByPhoneNumber(String phoneNumber) {
		return userDAO.findUserByPhoneNumber(phoneNumber);
	}

	public Long getUserIdByPhoneNumber(String phoneNumber) {
		return userDAO.findUserIdByPhoneNumber(phoneNumber);
	}
	
	public boolean existsUserByPhoneNumber(String phoneNumber) {
		return getUserByPhoneNumber(phoneNumber) != null;
	}

	public User hasAccess(Principal principal, Long userId) {
		User user = getUserByPrincipal(principal);
		if (user != null && (user.isAdmin() || user.getId() == userId)) {
			return user;
		}
		return null;
	}
	
	public void initRoles() {
		roleDao.saveAll(WebApplication.roles);
	}
}
