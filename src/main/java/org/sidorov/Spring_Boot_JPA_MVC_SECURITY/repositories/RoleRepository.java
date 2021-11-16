package org.sidorov.Spring_Boot_JPA_MVC_SECURITY.repositories;

import org.sidorov.Spring_Boot_JPA_MVC_SECURITY.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRole(String role);
}
