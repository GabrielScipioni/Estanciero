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
public class BoardEntity {
    private Long boardId;
    private Long gamesId;
    private Long squareId;
    private Long squareOwner;
    private Boolean mortgage;
    private Integer upgradeLevel;
}