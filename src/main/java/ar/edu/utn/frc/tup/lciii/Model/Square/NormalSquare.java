package ar.edu.utn.frc.tup.lciii.Model.Square;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Event.Event;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * modela un casillero normal, suele tener eventos asignados parta cuando un jugador "caiga en este casillero"
 */
@Getter
@Setter
@AllArgsConstructor
public class NormalSquare extends Square {

    private Event event;
    private Event eventWhenPassing;
    private String name;
    private Square square;
    private Integer amount;
    private CardType cardType;




    public NormalSquare(Long id,Event event, String name, Square square, Integer amount) {

        this.event = event;
        this.name = name;
        this.square = square;
        this.amount = amount;
    }
    public NormalSquare(Long id,Event event, Event eventWhenPassing, String name, Integer amount, CardType cardType) {
        super(id);
        this.event = event;
        this.eventWhenPassing = eventWhenPassing;
        this.name = name;
        this.square = square;
        this.amount = amount;
        this.cardType=cardType;
    }
    public NormalSquare() {
    }

    @Override
    public void execute(Game game, Player player) {
        if(event!=null){
            event.execute(game,player,square, amount, this.cardType);
        }
    }

    @Override
    public void executeWhenPassing(Game game, Player player) {
        if(eventWhenPassing!=null){
            game.addEvent(player.getName() + " pasó por el casillero " +this.name );
            eventWhenPassing.execute(game,player,square, amount, this.cardType);
        }

    }

}
