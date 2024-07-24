package ar.edu.utn.frc.tup.lciii.Model.Card;

import ar.edu.utn.frc.tup.lciii.Model.Event.Event;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private CardType cardType;
    private Event event;
    private String message;
    private Square square;
    private Integer amount;
    private boolean savableByPlayer;

    public Card() {}

    public Card(CardType cardType, Event event, String message, Square square, Integer amount, boolean savableByPlayer) {
        this.cardType = cardType;
        this.event = event;
        this.message = message;
        this.square = square;
        this.amount = amount;
        this.savableByPlayer = savableByPlayer;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void execute(Game game, Player player){

        this.event.execute(game,player,this.square, this.amount, cardType);
    }

    public boolean isSavableByPlayer() {
        return savableByPlayer;
    }
}
