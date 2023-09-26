package com.app.bookstore.repository.role;

import com.app.bookstore.model.Role;
import com.app.bookstore.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(RoleName name);
}
