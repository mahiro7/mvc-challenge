package com.gft.mvcproject.repositories.user;

import com.gft.mvcproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);

    String cargo = "sm";

    @Query("SELECT u FROM User u WHERE u.cargo = :cargo")
    public List<User> listScrumMasters(@Param("cargo") String cargo);

    default List<User> listScrumMasters() {
        return listScrumMasters(cargo);
    }

    @Query("SELECT u FROM User u WHERE u.cargo = 2")
    public List<User> listSm();

    @Query("SELECT u FROM User u WHERE u.cargo = 1")
    public List<User> listAdm();
}
