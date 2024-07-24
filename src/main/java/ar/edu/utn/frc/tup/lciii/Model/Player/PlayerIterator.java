package ar.edu.utn.frc.tup.lciii.Model.Player;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Services.ui.UiService;
import ar.edu.utn.frc.tup.lciii.UserInteraction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * modelara los turnos de cada jugador iteranndo para que jueguen entre ellos
 */
@Setter
@NoArgsConstructor
@Getter
public class PlayerIterator implements Serializable{

    private List<PlayerOrder> players = new ArrayList<>();

    public PlayerIterator(List<Player> players) {
        if (players!=null){
            for(Player player:players){
                this.players.add(new PlayerOrder(player));
            }

        }

    }




    public Player next() {
        //encontrar el primer player que no este en bancarrota. Se lo saca del array y se lo pone al final, devolviéndolo. Si no hay, es null
        PlayerOrder playerOrder=null;

        if(players.isEmpty()){
            return null;
        }
        for (PlayerOrder playerIt : players){
            if(!playerIt.getPlayer().getBankrupted()){
                playerOrder=playerIt;
                break;
            }
        }
//        if(playerOrder==null){
//            return null;
//        }

        players.remove(playerOrder);

        players.add(playerOrder);
        return playerOrder.getPlayer();
    }

    public List<Player> getPlayersListTest(){
        List<Player> players= new ArrayList<>();
        if(this.players!=null){
            for(PlayerOrder po: this.players){
                players.add(po.getPlayer());
            }
        }
        return players;


    }

    private void addEventsForPlayersTurns(Game game){
        game.addEvent("Los resultados son: ");
        for(int i = 0; i< players.size();i++){
            game.addEvent(i+1+". "+ players.get(i).getPlayer().getName()+" con "+ players.get(i).getDicePoints());
        }
        UiService.getInstance().printGameState(game);
        new UserInteraction().sleep(500);

    }

    public void initializeTurns(Game game){
        //cada player "tira" dados, si el numero ya salio para otro player, entonces sigue tirando hasta que no se repita. Y al final se ordena
        game.addEvent("Lanzando turnos.");
        if (players.size()<2){
            return;
        }

        for (PlayerOrder player : players) {
            UiService.getInstance().printGameState(game);


            game.getDice().throwDice(game, player.getPlayer());

            int diceTotalValue = game.getDice().getTotalValue();
            game.addEvent("El jugador "+ player.getPlayer().getName()+ " sacó "+ diceTotalValue);
            while (!differentValue(diceTotalValue)) {
                game.addEvent("Valor repetido. Tirando nuevamente");
                game.getDice().throwDice(game, player.getPlayer());


                diceTotalValue=game.getDice().getTotalValue();

                UiService.getInstance().printGameState(game);
                new UserInteraction().sleep(500);
            }
            player.setDicePoints(diceTotalValue);
        }

        sortByHigestPoints();
        addEventsForPlayersTurns(game);

    }

    private boolean differentValue(Integer value){
        for(PlayerOrder po : players){
            if(Objects.equals(po.getDicePoints(),value) ){
                return false;
            }
        }
        return true;
    }

    private void sortByHigestPoints(){
        for (int i = 0; i<players.size(); i++){
            Integer maxIndex = i;
            for (int j = i+1; j <players.size(); j++){
                if(players.get(maxIndex).getDicePoints()< players.get(j).getDicePoints()){
                    maxIndex = j;
                }
            }

            Collections.swap(players,i,maxIndex);
        }
    }




}

