package ar.edu.utn.frc.tup.lciii.Model.Player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerOrder implements Serializable {

    private Player player;
    private Integer dicePoints;

    public PlayerOrder(Player player) {
        this.player = player;
    }


}
