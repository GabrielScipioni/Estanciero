package ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.PlayMenu;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.GameOption;
import ar.edu.utn.frc.tup.lciii.Model.Menu.Option.Option;
import ar.edu.utn.frc.tup.lciii.Model.Menu.PlayerMenu.ExitOption;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Property.*;
import ar.edu.utn.frc.tup.lciii.UserInteraction;

import java.util.ArrayList;

public class MakeMoneyOfPropertiesMenu extends GameOption {
    private final Player player ;
    private Integer amountToPay;

    public MakeMoneyOfPropertiesMenu(Game game, Player player, Integer amountToPay) {
        super(1, "", "Debe conseguir " + amountToPay+" en el balance.", false, game);
        this.player=player;
        this.amountToPay=amountToPay;


    }
    @Override
    public void execute() {

        if(player.getProperties().isEmpty()){
            new UserInteraction().message("No tiene propiedades para gestionar");

            return;
        }
        updateSubOptions();

        super.drawSubOptions();

    }
    @Override
    public void updateSubOptions(){
        ArrayList<Option> subOptions = new ArrayList<>();
        for (int i = 0; i<player.getProperties().size(); i++){
            subOptions.add(new MakeMoneyFromOneProperyOption(i+1,super.getGame(),player, player.getProperties().get(i)));
        }
        subOptions.add(new FinishMakeMoneyOption(player.getProperties().size()+1, getGame(), player,amountToPay));
        super.setSubOptions(subOptions);
    }
}

class MakeMoneyFromOneProperyOption extends GameOption {
    private final Player player ;
    private final Property property;

    public MakeMoneyFromOneProperyOption(Integer optionNumber, Game game, Player player, Property property) {
        super(optionNumber, property.getName(), property.getName(), false, game);
        this.player=player;
        this.property = property;

        ArrayList<Option> subOptions = new ArrayList<>();
        subOptions.add((new SellPropertyUpgrades(1, game, player, property)));
        subOptions.add(new MortgageProperty(2, game, player, property));
        subOptions.add(new SellProperty(3, game, player, property));
        subOptions.add(new StartAuction(4, game, player, property));
        subOptions.add(new ExitOption(5));
        updateTitle();

        super.setSubOptions(subOptions);
    }
    @Override
    public void execute() {
        updateTitle();
        super.drawSubOptions();


    }

    private void updateTitle() {
        String message = property.getName()+ " | Precio: "+property.getPrice()+ " | Hipotecada: "+ property.isMortgaged();
        if(property instanceof TerrainProperty){
            message+= (" | UpgradeLevel: "+ ((TerrainProperty)property).getUpgradeLevel());
        }

        super.setOptionName(message);
        super.setTitle(message);
    }
}

class FinishMakeMoneyOption extends GameOption {
    Player player;
    Integer amount;

    public FinishMakeMoneyOption(Integer optionNumber, Game game,Player player, Integer amount) {
        super(optionNumber, "Finalizar", "Finalizar", false, game);
        this.player = player;
        this.amount=amount;

    }
    @Override
    public void execute() {
        String question = "";
        if(player.getBalance()<amount){
            question += "NO tiene el dinero suficiente, se lo declarará en bancarota.";
        }
        question+="\nSeguro que desea finalizar la generación de dinero?";
        boolean finishGame = new UserInteraction().askYesNo(question);
        if(finishGame){
            super.setExitOption(true);
        }

    }

}

//import ar.edu.utn.frc.tup.lciii.Model.Board.Board;
//import ar.edu.utn.frc.tup.lciii.Model.Card.Card;
//import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
//import ar.edu.utn.frc.tup.lciii.Model.Event.*;
//        import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
//import ar.edu.utn.frc.tup.lciii.Model.Player.ConservativeBot;
//import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
//import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
//import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
//import ar.edu.utn.frc.tup.lciii.Model.Property.*;
//        import ar.edu.utn.frc.tup.lciii.Model.Square.NormalSquare;
//import ar.edu.utn.frc.tup.lciii.Model.Square.PropertySquare;
//import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
//import ar.edu.utn.frc.tup.lciii.Model.User;
//import ar.edu.utn.frc.tup.lciii.Services.ui.UiService;
//
//import java.util.ArrayList;
//import java.util.List;



//Integer [] rentByPrice = new Integer[5];
//rentByPrice[0] = 400;
//rentByPrice[1] = 2000;
//rentByPrice[2] = 5750;
//rentByPrice[3] = 17000;
//rentByPrice[4] = 21000;
//Property terrainProperty = new TerrainProperty(false, "Salta zona norte", 1000, Zone.NORTE, Province.SALTA,rentByPrice,500,0);
//Property railWayProperty = new RailwayProperty(false, "Tren", 1000);
//PropertySquare propertySquare = new PropertySquare(terrainProperty);
//PropertySquare railwayPropertySquare = new PropertySquare(railWayProperty);
//NormalSquare jailSquare = new NormalSquare(new Event(EventType.JAIL, new JailStrategy()), "Comisaria", null,null );
//NormalSquare exitSquare = new NormalSquare(new Event(EventType.MONEY, new MoneyStrategy()),"Salida",null,500);
//        exitSquare.setEventWhenPassing(new Event(EventType.MONEY, new MoneyStrategy()));
//        exitSquare.setAmount(5000);
//        exitSquare.setEvent(new Event(EventType.MONEY, new MoneyStrategy()));
//NormalSquare parkingSquare= new NormalSquare(null, "Parking", null,null );
//
//
//
//
//Player humanPlayer = new HumanPlayer(new ArrayList<>(List.of(railWayProperty)) ,false);
//        humanPlayer.setName("Pepe");
//        humanPlayer.setBalance(100);
//
//Player botPlayer = new ConservativeBot(new ArrayList<>(List.of(railWayProperty)),false);
//        botPlayer.setName("BotJuan");
//        botPlayer.setBalance(200);
//
//Player botPlayer1 = new ConservativeBot(List.of(new CompanyProperty(false, "railway", 100)),true);
//        botPlayer1.setName("BotJuan");
//        botPlayer1.setBalance(200);
//
//Player botPlayer3 = new ConservativeBot(new ArrayList<>(),false);
//        botPlayer3.setName("BotJuan");
//        botPlayer3.setBalance(200);
//
//
//        propertySquare.addPlayer(humanPlayer);
//List<Square> squares = new ArrayList<>();
//
//        squares.add(propertySquare);
//        squares.add(parkingSquare);
//        squares.add(railwayPropertySquare);
//        squares.add(jailSquare);
//        squares.add(exitSquare);
//
//
//
//
//
//Board board = new Board();
//        board.setSquares(squares);
//
////new MainMenu().execute();
//Game game = new Game(board, new User("test", "test", "test"));
//
//
//
//        humanPlayer.getCards().add(new Card(CardType.LUCK, new Event(EventType.GET_OUT_OF_JAIL, new GetOutOfJailStrategy()),"get out of jail card",null,null,false));
//
//        game.setPlayersIterator(new PlayerIterator(List.of(humanPlayer, botPlayer, botPlayer1, botPlayer3)));
//
//        humanPlayer.setGame(game);
//
//        game.addEvent("Player1 hace algo");
//        game.addEvent("Player2 hace algo");
//        game.addEvent("Player3 hace algo");
//        game.addEvent("Player4 hace algo");
//        game.addEvent("Player5 hace algo");
//        game.addEvent("Player6 hace algo");
//        game.addEvent("Player7 hace algo");
//        game.addEvent("Player8 hace algo");
//        game.addEvent("Player9 hace algo");
//        game.addEvent("Player10 hace algo");
//        game.addEvent("Player11 hace algo");
//        game.addEvent("Player12 hace algo");
//        game.addEvent("Player13 hace algo");
//        game.addEvent("Player14 hace algo");
//        game.addEvent("Player15 hace algoasdasd asdasdasd asdasda asdasdasdasda asdasdasdasdasdasdasdasdasdad asdasdasdasdasd asdasdddddddddddaaaaaassss asdasdad asdasdasdasd asdasdasd");
//
//
//
//
//        humanPlayer.findWayToMakeMoney(1000);
//
//        UiService.getInstance().printGameState(game);
