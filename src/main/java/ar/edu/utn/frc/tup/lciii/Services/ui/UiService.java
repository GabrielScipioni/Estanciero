package ar.edu.utn.frc.tup.lciii.Services.ui;

import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerIterator;
import ar.edu.utn.frc.tup.lciii.Model.Player.PlayerOrder;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public final class UiService {

    public static Integer eventsColumnWith = 95;
    public static Integer amountOfRows = 24;
    public static Integer statsColumnWith = 40;
    private static UiService instance;

    private UiService() {
    }

    public static UiService getInstance(){
        if (instance == null) {
            instance = new UiService();
        }
        return instance;
    }


    public void printGameState(Game game){
        clear();
        printGameStateHeader();

        List<String> events = game.getEvents();
        events = events.subList(Math.max(events.size() - amountOfRows, 0), events.size());

        List<String> stats = generateStats(game);

        for (int i = 0; i < amountOfRows; i++) {

            String event = substringAndEllipsis(getStringRow(i, events),eventsColumnWith);

            String stat = getStringRow(i,stats);

            System.out.printf( ANSI.BLUE+"|"+ANSI.CYAN+"%-" + eventsColumnWith + "s "+ANSI.BLUE+ "|" +"%-" + statsColumnWith + "s"+ANSI.BLUE+" |%n", event, stat);
        }
        System.out.println("|"+"-".repeat(eventsColumnWith) + "-+-" + "-".repeat(statsColumnWith)+"|"+ANSI.RESET);




    }
    private void printGameStateHeader(){
        System.out.println(ANSI.BLUE+"|"+"-".repeat(eventsColumnWith) + "-+-" + "-".repeat(statsColumnWith)+"|");
        System.out.printf("|%-" + eventsColumnWith + "s | %-" + statsColumnWith + "s|%n", "Eventos", "Stats");
        System.out.println("|"+"-".repeat(eventsColumnWith) + "-+-" + "-".repeat(statsColumnWith)+"|"+ANSI.RESET);

    }

    private String getStringRow(int index, List<String> strings){
        String aux;
        if(index < strings.size()){
            aux= strings.get(index);
        } else {
            aux= "";
        }

        return aux;
    }

    private List<String> generateStats(Game game){
        List<String> stats = new ArrayList<>();

        List<PlayerOrder> players = game.getPlayersIterator().getPlayers();
        List<PlayerOrder> orderedPlayers = new ArrayList<>(players);
        if (!orderedPlayers.isEmpty() && orderedPlayers.get(orderedPlayers.size() - 1).getDicePoints() != null) {

            orderedPlayers.sort(Comparator.comparing(PlayerOrder::getDicePoints).reversed());
        }








        for (PlayerOrder player: orderedPlayers){
            String prefix;
            if(player.getPlayer().getBankrupted()){
                prefix=ANSI.RED;
            }else if (player.getPlayer() instanceof HumanPlayer){
                prefix= ANSI.PURPLE;
            }else{
                prefix= ANSI.GREEN;
            }
            int statsWithPlusAnsiCode = (statsColumnWith+ ANSI.ANSILENGHT);

            stats.add( String.format("%1$-"+statsWithPlusAnsiCode+"s",(prefix+ player.getPlayer().getName()+ANSI.RESET)));
            stats.add( String.format("%1$-"+statsWithPlusAnsiCode+"s",(prefix+ "Dinero: "+player.getPlayer().getBalance()+ANSI.RESET)));
            stats.add( String.format("%1$-"+statsWithPlusAnsiCode+"s",(prefix+ "Propiedades: "+player.getPlayer().getProperties().size()+ANSI.RESET)));
            Square squareStanding = game.getBoard().getSquarePlayerStanding(player.getPlayer());
            String squareName = squareStanding!=null ? squareStanding.getName():"-";
            stats.add( String.format("%1$-"+statsWithPlusAnsiCode+"s",(prefix+ "En: "+squareName+ANSI.RESET)));

            stats.add("-".repeat(statsColumnWith));

        }

        return stats;
    }



    public void printBanner(){
        clear();
        System.out.println(ANSI.CYAN);
        System.out.println(" /$$$$$$$$ /$$                             /$$                                   /$$");
        System.out.println("| $$_____/| $$                            | $$                                  |__/");
        System.out.println("| $$      | $$        /$$$$$$   /$$$$$$$ /$$$$$$    /$$$$$$  /$$$$$$$   /$$$$$$$ /$$  /$$$$$$   /$$$$$$   /$$$$$$");
        System.out.println("| $$$$$   | $$       /$$__  $$ /$$_____/|_  $$_/   |____  $$| $$__  $$ /$$_____/| $$ /$$__  $$ /$$__  $$ /$$__  $$");
        System.out.println("| $$__/   | $$      | $$$$$$$$|  $$$$$$   | $$      /$$$$$$$| $$  \\ $$| $$      | $$| $$$$$$$$| $$  \\__/| $$  \\ $$");
        System.out.println("| $$      | $$      | $$_____/ \\____  $$  | $$ /$$ /$$__  $$| $$  | $$| $$      | $$| $$_____/| $$      | $$  | $$");
        System.out.println("| $$$$$$$$| $$      |  $$$$$$$ /$$$$$$$/  |  $$$$/|  $$$$$$$| $$  | $$|  $$$$$$$| $$|  $$$$$$$| $$      |  $$$$$$/");
        System.out.println("|________/|__/       \\_______/|_______/    \\___/   \\_______/|__/  |__/ \\_______/|__/ \\_______/|__/       \\______/" + ANSI.RESET);
        System.out.println("\n".repeat(3));
    }

    private void clear(){
//        try{
//            Runtime.getRuntime().exec("clear");
//        }catch (Exception ignore){}

        System.out.print("\n".repeat(20));


    }

    private String substringAndEllipsis(String text, int length) {
        if (text.length() <= length-3) {
            return text;
        } else {
            return text.substring(0, length-3)+"...";
        }
    }

}
