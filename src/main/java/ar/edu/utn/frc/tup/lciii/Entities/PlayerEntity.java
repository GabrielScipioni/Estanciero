package ar.edu.utn.frc.tup.lciii.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 This class/table gathers the general data of each played game
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {
    private Long playerId;
    private Long gameid;
    private Long playerTypeId;
    private Integer balance;
    private Boolean bankrupted;
    private Long squarePosition;
    private Long userId;
}