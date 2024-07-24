package ar.edu.utn.frc.tup.lciii.Model.Game;

import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
import ar.edu.utn.frc.tup.lciii.Model.Dice;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Model.Card.Deck;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerOrder;
import ar.edu.utn.frc.tup.lciii.Model.User;
import ar.edu.utn.frc.tup.lciii.Services.Bank;
import ar.edu.utn.frc.tup.lciii.Services.DB.Impl.CRUD.GameService;
import ar.edu.utn.frc.tup.lciii.Services.ui.UiService;
import ar.edu.utn.frc.tup.lciii.UserInteraction;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game implements Serializable {
    private Long id;
    private Board board;
    private User userOwner;
    private Player winner;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;
    private final Dice dice = new Dice();
    private Bank bank = new Bank();
    private PlayerIterator playerIterator = new PlayerIterator();
    private Deck deck = new Deck(new ArrayList<>());
    private List<String> events = new ArrayList<>();
    private int maxEventBuffer =100;
    private boolean finishGame = false;

    public Game(Board board, User user) {
        this.board = board;
        this.userOwner = user;
        this.playerIterator = new PlayerIterator(Collections.emptyList());

    }

    public Game(Board board, User user, List<Player> playerIterator, List<Card> cards) {
        this.board = board;
        this.userOwner = user;
        deck = new Deck(cards);
        this.playerIterator = new PlayerIterator(playerIterator);

    }

    public PlayerIterator getPlayersIterator() {
        return playerIterator;
    }

    public void setPlayersIterator(PlayerIterator players) {
        this.playerIterator = players;
    }


    public void addEvent(String event){


        events.add(event);
        if(maxEventBuffer < events.size()){
            events.remove(0);
        }

    }

    public void finishGame() {
        finishGame=true;
    }

    public void resumeGame() {

        new UserInteraction().message("Se resumió el nuevo juego!!");
        if(events.isEmpty()){ //un forma de ver que la partida no empezó
            startGame();
        }

        continuePlaying();
    }

    private void continuePlaying() {
        while(true){
            new UserInteraction().sleep(500);
            Player playerPlaying= playerIterator.next();
            addEvent("Turno de : "+ playerPlaying.getName());
            if (!board.isPlayerInJail(playerPlaying)){
                getDice().throwDice(this,playerPlaying);
                new UserInteraction().sleep(500);
                getBoard().moveSteps(this, playerPlaying,getDice().getTotalValue());
            }
            new UserInteraction().sleep(500);
            UiService.getInstance().printGameState(this);
            playerPlaying.play(this);

            if(finishGame){
                addEvent("Finalizando juego");
                break;
            }
            if(checkWinner()){
                addEvent("Ganó jugador "+ winner.getName());
                break;
            }


            GameService.getInstance().update(this);
            UiService.getInstance().printGameState(this);
        }
    }

    private boolean checkWinner() {
        if(winner!=null){
            return true;
        }
        List<Player> playersNotBankrupted = new ArrayList<>();

        for(PlayerOrder playerOrder: playerIterator.getPlayers()){
            if (!playerOrder.getPlayer().getBankrupted()){
                playersNotBankrupted.add(playerOrder.getPlayer());
            }
        }

        if (playersNotBankrupted.size()<=1){
            winner = playersNotBankrupted.get(0);
            return true;
        }else{
            return false;
        }
    }


    private void startGame(){
        events.add("Se inicia el juego!");

        getPlayersIterator().initializeTurns(this);

        events.add("Mezclando cartas...");
        new UserInteraction().sleep(500);
        deck.shuffle();
        for (PlayerOrder playerOrder: playerIterator.getPlayers()){
            board.getSquares().get(0).addPlayer(playerOrder.getPlayer());
        }

    }
}


