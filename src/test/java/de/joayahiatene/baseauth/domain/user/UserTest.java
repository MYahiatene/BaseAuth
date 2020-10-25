package de.joayahiatene.baseauth.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }


    @Test
    void addRole() {
        user.addRole("Admin");
        assertEquals(List.of("Admin"), user.getRoles());
    }

    @Test
    void getUsername() {
        String username = "test";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    void getFirstname() {
        String firstname = "ervin";
        user.setUsername(firstname);
        assertEquals(firstname, user.getFirstname());
    }

    @Test
    void getLastname() {
        String lastname = "mo";
        user.setUsername(lastname);
        assertEquals(lastname, user.getLastname());
    }

    @Test
    void getPassword() {
        String password = "password";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    void getEmail() {
        String email = "test@domain.de";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    void getRoles() {
        String role = "Admin";
        user.addRole(role);
        assertEquals(List.of(role), user.getRoles());
    }
}