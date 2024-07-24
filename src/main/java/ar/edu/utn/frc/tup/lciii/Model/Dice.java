package ar.edu.utn.frc.tup.lciii.Model;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;
import java.util.Random;
/**
 * The class dice is responsable for the dice throws in the game,
 * has a method to trow the dice and have 2 results as return being the values you get,
 * also will check for doubles and move spaces.
 * this class will be instanciated via a private object in a static instance,
 * this makes it posible to store the last throw value until someone
 * uses the throw function again changing the values gotten.
 *
 * @autor 405660-NIETO
 * @version 1.0.0
 * @since 2024-04-15
 */

@Getter
public class Dice implements Serializable {
    private Integer[] rolls = new Integer[2];
    @JsonIgnore
    private Random randomNumberGenerator;
    public Dice(){
        randomNumberGenerator = new Random();
        rolls[0]=0;
        rolls[1]=0;
    }

    /**
     * This method gives two values of a dice throw
     *
     * @value rolls Integer Array of 2 Spaces
     * @return Integer[] returns an array of two numbers { first throw , second throw}
     */
    public Integer[] throwDice() {
        for (int i = 0; i < 2; i++) {
            rolls[i] = randomNumberGenerator.nextInt(6) + 1;
        }
        return rolls;
    }
    public Integer[] throwDice(Game game, Player player) {
        throwDice();
        game.addEvent(player.getName()+ " sacó [" + rolls[0] + "] y [" + rolls[1] + "]!");

        return rolls;
    }

    /**
     * This method compares both values from a roll.
     *
     * @return bool returns true if both values are equal
     */
    public boolean checkDouble(){
        return rolls[0].equals(rolls[1]);
    }

    /**
     * Method to calculate how many spaces to move after a roll
     *
     * @return int sum of throw one plus trow two
     */
    @JsonIgnore
    public int getTotalValue(){
        return rolls[0] + rolls[1];
    }
}
