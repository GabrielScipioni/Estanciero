package ar.edu.utn.frc.tup.lciii.Model.Board;

import ar.edu.utn.frc.tup.lciii.Model.Event.EventType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

import java.util.List;

public class Board {
    private List<Square> squares;


    public List<Square> getSquares() {
        return squares;
    }
    public Board(Board other){
        this.squares = other.squares;
    }
    public Board(){

    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }


    public void sendToJail( Game game,Player player){

        Integer jailIndex = getIndexOfFirstSquareWithStrategyType(EventType.JAIL);

        if (jailIndex==null){
            System.out.println("Error: No jail on board");
            return;
        }

        Square jail = squares.get(jailIndex);
        teleport(game, player,jail);


    }

    public boolean isPlayerInJail(Player player){
        Integer jailIndex = getIndexOfFirstSquareWithStrategyType(EventType.JAIL);

        if (jailIndex==null){
            System.out.println("Error: No jail on board");
          return false;
        }

        Square jail = squares.get(jailIndex);

        return jail.isPlayerInSquare(player);
    }

    public void teleport(Game game, Player player, Square destination){
        Square squareStanding = getSquarePlayerStanding(player);


        if (squareStanding==null){
            System.out.println("Error: Player not in board");
            return;
        }


        if(!squares.contains(destination)){
            System.out.println("Error: destination not in board");
            return;
        }

        squareStanding.takePlayerOut(player);
        destination.addPlayerAndExecute(game,player);

    }

    public void moveSteps(Game game,Player player, Integer steps){
        /*Ver en que square esta parado el player (preguntand a los squares si está ahi)
         buscar en que square caería el player y si pasa por la salida, cobrar
         metiendo la plata al player (atraves del banco) o haciendo alguna ejecución secundaria
         Una vez que lo tiene, le cambia el square al player y se lo ejecuta
         (revsar move to square, se pueden simplificar varios pasos)
         */
        Integer indexOfStandigSquare = getIndexOfSquareStanding(player);
        if (indexOfStandigSquare==null){
           System.out.println("Error: Player not in board");
            return;
        }
        if(steps==0){
            return;
        }
        game.addEvent(player.getName() + " sale del casillero "+ getSquares().get(indexOfStandigSquare).getName());
        getSquares().get(indexOfStandigSquare).takePlayerOut(player);

        int newIndex;
        if (steps>0){
            for (int i = 1; i<steps; i++){
                 newIndex= (indexOfStandigSquare+i) % (getSquares().size());
                squares.get(newIndex).executeWhenPassing(game,player);
            }
            newIndex= (indexOfStandigSquare+steps) % (getSquares().size());
            squares.get(newIndex).addPlayerAndExecute(game,player);
        }else {
            for (int i = -1; i>steps; i--) {
                newIndex = (indexOfStandigSquare + i+getSquares().size()) % (getSquares().size());
                squares.get(newIndex).executeWhenPassing(game, player);
            }
            newIndex= (indexOfStandigSquare+steps+getSquares().size()) % (getSquares().size());
            squares.get(newIndex).addPlayerAndExecute(game,player);
        }




    }


    public void moveToSquare(Game game,Player player, Square destination){
        /* Ver en que square esta parado el player (preguntand a los squares si está ahi)
         si pasa por la salida, cobrar
         metiendo la plata al player (atraves del banco) o haciendo alguna ejecución secundaria
         Una vez que lo tiene, le cambia el square al player y se lo ejecuta
         */

        Integer indexOfStandingSquare = getIndexOfSquareStanding(player);
        if (indexOfStandingSquare==null){
            System.out.println("Error: Player not in board");
            return;
        }
        Square initialStandingSquare = squares.get(indexOfStandingSquare);
        if(!squares.contains(destination)){
            System.out.println("Error: Destination not in board");
            return;
        }
        if(initialStandingSquare.equals(destination)){
            System.out.println("Player already in destination");
            return ;
        }

        initialStandingSquare.takePlayerOut(player);

        int newIndex= (indexOfStandingSquare+1) % (getSquares().size());

        while(!getSquares().get(newIndex).equals(destination)){
            getSquares().get(newIndex).executeWhenPassing(game,player);
            newIndex= (newIndex+1) % (getSquares().size());
        }

        getSquares().get(newIndex).addPlayerAndExecute(game,player);

    }


    private Integer getIndexOfSquareStanding (Player player){
        if (squares==null || squares.isEmpty()){
            return null;
        }
        for(Square square: squares){
            if(square.isPlayerInSquare(player)){
                return squares.indexOf(square);
            }
        }
        return null;
    }

    public Square getSquarePlayerStanding(Player player){
        Integer index = getIndexOfSquareStanding(player);
        if(index!=null){
            return squares.get(index);
        }
        return null;
    }


    private Integer getIndexOfFirstSquareWithStrategyType(EventType type){
        if (squares==null || squares.isEmpty()){
            return null;
        }
        for (Square square: squares){
            if(square instanceof NormalSquare && ((NormalSquare) square).getEvent()!=null && ((NormalSquare) square).getEvent().getType().equals(type)){
                return squares.indexOf(square);
            }
        }
        return null;
    }







}
