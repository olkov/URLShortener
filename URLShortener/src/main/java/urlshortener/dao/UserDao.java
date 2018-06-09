package urlshortener.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import urlshortener.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	@Query(value = "select u from User u where u.phoneNumber = :phoneNumber")
	public User findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
	
	@Query(value = "select u.id from User u where u.phoneNumber = :phoneNumber")
	public Long findUserIdByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
