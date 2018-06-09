package urlshortener.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import urlshortener.entity.URL;

@Repository
public interface URLDao extends JpaRepository<URL, Long> {
	@Query("select u from URL u where u.shortURL = ?1")
	URL findURLByShortURL(String shortURL);
	
	@Query("select u from URL u where u.user.id = ?1")
	List<URL> findAllByUserId(Long userId);
}
