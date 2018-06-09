package urlshortener.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import urlshortener.entity.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {}
