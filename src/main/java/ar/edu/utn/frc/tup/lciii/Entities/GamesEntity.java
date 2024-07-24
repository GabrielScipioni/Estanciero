package ar.edu.utn.frc.tup.lciii.Entities;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import java.util.List;

/**
 This class/table gathers the general data of each played game
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GamesEntity {
    private Long gameId;
    private LocalDateTime dateSetup;
    private Boolean finished;
    private List<String> orderByIdPlayer; //se guardaran los id en el orden del juego de esta forma "5-3-2-6"



    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String getDateSetupAsString() {
        return dateSetup.format(dateFormatter);
    }

    public void setDateSetup(LocalDateTime dateSetup) {
        this.dateSetup = dateSetup;
    }

    public String getOrderByIdPlayerAsString() {
        return String.join("-", this.orderByIdPlayer);
    }

    public void setOrderByIdPlayer(String orderByIdPlayer) {
        this.orderByIdPlayer = Arrays.asList(orderByIdPlayer.split("-"));
    }
}