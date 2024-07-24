package ar.edu.utn.frc.tup.lciii.Entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {

    private UserEntity user;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setName("testuser@example.com");
    }

    @Test
    public void testUserIdGetterAndSetter() {
        assertEquals(1L, user.getUserId());
        user.setUserId(2L);
        assertEquals(2L, user.getUserId());
    }

    @Test
    public void testUsernameGetterAndSetter() {
        assertEquals("testuser", user.getUsername());
        user.setUsername("newusername");
        assertEquals("newusername", user.getUsername());
    }

    @Test
    public void testPasswordGetterAndSetter() {
        assertEquals("password123", user.getPassword());
        user.setPassword("newpassword456");
        assertEquals("newpassword456", user.getPassword());
    }

    @Test
    public void testEmailGetterAndSetter() {
        assertEquals("testuser@example.com", user.getName());
        user.setName("newuser@example.com");
        assertEquals("newuser@example.com", user.getName());
    }

    @Test
    public void testNoArgsConstructor() {
        UserEntity newUser = new UserEntity();
        assertNull(newUser.getUserId());
        assertNull(newUser.getUsername());
        assertNull(newUser.getPassword());
        assertNull(newUser.getName());
    }

    @Test
    public void testAllArgsConstructor() {
        UserEntity newUser = new UserEntity(3L, "anotheruser", "anotherpassword", "anotheruser@example.com");
        assertEquals(3L, newUser.getUserId());
        assertEquals("anotheruser", newUser.getUsername());
        assertEquals("anotherpassword", newUser.getPassword());
        assertEquals("anotheruser@example.com", newUser.getName());
    }
}
