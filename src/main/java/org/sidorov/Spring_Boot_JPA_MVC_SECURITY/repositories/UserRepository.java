package org.sidorov.Spring_Boot_JPA_MVC_SECURITY.repositories;

import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User readUserByUsername(String username);
}
