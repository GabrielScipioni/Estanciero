package ar.edu.utn.frc.tup.lciii.Model.Card;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Deck implements Serializable {

    private List<Card> cards;

    //    /**
//     * Check to see if there's any card in deck.
//     */
//    public boolean isEmpty() {
//        return cards.isEmpty();
//    }
//    /**
//     * Check to see if there's card od CardType in deck.
//     *
//     * @param cardType cardType to search for.
//     */
//    public boolean hasMore(CardType cardType) {
//        Predicate<Card> condition = e-> e.getCardType().equals(cardType);
//        return !isEmpty() && cards.stream().anyMatch(condition);
//    }


    /**
     * Take the next card of card type.
     * If card is salvable by player, then gives it.
     * If not. It's executed and returned to deck.
     *
     * @param game game playing/context.
     * @param player player picking up. to assign or execute.
     * @param cardType cardType to search for.
     */
    public void takeAndExecute(Game game, Player player, CardType cardType) {
        Card card = null;
        for(Card cardI : cards){
            if(cardI.getCardType().equals(cardType)){
                card = cardI;
                break;
            }
        }
        if (card == null){
            return;
        }
        cards.remove(card);
        game.addEvent(player.getName()+" obtuvo la carta "+ card.getMessage());
        if (card.isSavableByPlayer()){
            player.getCards().add(card);
        }else {
            returnCardAndExecute(game, player,card);
        }

    }

    public void returnCardAndExecute(Game game,Player player,Card card){
        card.execute(game,player);
        cards.add(card);
    }
    /**
     * Shuffles cards
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }


}
