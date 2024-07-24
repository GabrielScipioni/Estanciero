package ar.edu.utn.frc.tup.lciii.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 This class/table gathers the general data of each played game
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeckEntity {
    private Long deckId;
    private Long gamesId;
    private List<String> orderCardsById; //se guardaran los id en el orden de las cartas de esta forma "5-3-2-6"



    public String getOrderCardsByIdString() {
        return String.join("-", this.orderCardsById);
    }

    public void setOrderByIdPlayer(String orderByIdPlayer) {
        this.orderCardsById = Arrays.asList(orderByIdPlayer.split("-"));
    }
}