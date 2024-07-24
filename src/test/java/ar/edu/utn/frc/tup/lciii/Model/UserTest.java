package ar.edu.utn.frc.tup.lciii.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserConstructor() {
        String userName = "johndoe";
        String passWord = "password123";
        String name = "John Doe";

        User user = new User(userName, passWord, name);

        assertEquals(userName, user.getUserName());
        assertEquals(passWord, user.getPassWord());
        assertEquals(name, user.getName());
    }

    @Test
    public void testUserSetterGetter() {
        String userName = "janedoe";
        String passWord = "securepassword";
        String name = "Jane Doe";

        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        user.setName(name);

        assertEquals(userName, user.getUserName());
        assertEquals(passWord, user.getPassWord());
        assertEquals(name, user.getName());
    }
}
