package urlshortener.service;

import java.security.Principal;
import java.util.List;

import urlshortener.entity.User;

public interface UserService {
	public User saveUser(User user);

	public boolean deleteUser(Long id);

	public List<User> getAllUsers();

	public User getUserById(Long id);

	public User getUserByPrincipal(Principal principal);

	public User getUserByPhoneNumber(String phoneNumber);

	public Long getUserIdByPhoneNumber(String phoneNumber);
	
	public boolean existsUserByPhoneNumber(String phoneNumber);

	public User hasAccess(Principal principal, Long userId);
	
	public void initRoles();
}
