package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import lombok.AllArgsConstructor;
import lombok.Getter;

//conocido como MovementType
@Getter
public class Event {
    EventType type;
    EventStrategy strategy;

    public Event(EventType type, EventStrategy strategy) {
        this.type = type;
        this.strategy = strategy;
    }

    public Event() {
    }

    public EventStrategy getStrategy(){
        return strategy;
    }

    public EventType getType() {
        return type;
    }

    public void setStrategy(EventStrategy strategy){
        this.strategy = strategy;
    }
    public void execute(Game game, Player player, Square square, Integer amount, CardType cardType){

        this.strategy.execute(game, player, square,amount, cardType);
    }
}
