package ar.edu.utn.frc.tup.lciii.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
public class User implements Serializable {
    private Long id;
    private String userName;
    private String passWord;
    private String name;

    public User(String userName, String passWord, String name) {
        this.userName = userName;
        this.passWord = passWord;
        this.name = name;
    }
}
