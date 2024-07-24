package ar.edu.utn.frc.tup.lciii.Model.Square;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * modela cualquier tipo de casillero
 */
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NormalSquare.class, name = "normalSquare"),
        @JsonSubTypes.Type(value = PropertySquare.class, name = "PropertySquare")
})
public abstract class Square {
    private Long id;
    private List<Player> playersOnSquare;

    public Square() {
        playersOnSquare = new ArrayList<>();
    }
    public Square(Long id) {
        this.id=id;
        playersOnSquare = new ArrayList<>();
    }

    public abstract void execute(Game game, Player player);
    public abstract void executeWhenPassing(Game game, Player player);

    public void addPlayer(Player player)
    {
        this.playersOnSquare.add(player);
    }

    public void addPlayerAndExecute(Game game,Player player)
    {
        game.addEvent(player.getName() + " cae en casillero "+ this.getName());
        this.playersOnSquare.add(player);
        execute(game, player);
    }
    public List<Player> getPlayers(){
        return this.playersOnSquare;
    }
    public abstract String getName();

    public Player takePlayerOut(Player player){

        if(playersOnSquare.remove(player)){
            return player;
        }
        return null;
    }

    public boolean isPlayerInSquare(Player player){

            for (Player player1: playersOnSquare){
                if (player1.getId()== player.getId()){
                    return true;
                }
            }

            return false;

    }

}
