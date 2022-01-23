package com.c0721g2srsrealestatebe.repository.account;


import com.c0721g2srsrealestatebe.model.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IRoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM roles where name =?1", nativeQuery = true)
    Role findRoleByName(String name);

    //Thinh
    @Query(value = "select * from roles where roles.id = :id", nativeQuery = true)
    Role getRoleById(@Param("id") Long id);

}
