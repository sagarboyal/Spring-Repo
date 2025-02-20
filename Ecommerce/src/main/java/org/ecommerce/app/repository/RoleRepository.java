package org.ecommerce.app.repository;

import org.ecommerce.app.model.Role;
import org.ecommerce.app.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(Roles roleName);
}
