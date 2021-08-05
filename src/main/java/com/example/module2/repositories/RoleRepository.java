package com.example.module2.repositories;

import com.example.module2.entities.Role;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    @Query("select r from Role r join r.users u where u.email = :email")
    Set<Role> findByUserId(@Param("email")String email);
}
