package ar.edu.utn.frc.tup.lciii.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 This class/table gathers the general data of each played game
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SquareEntity {
    private Long squareId;
    private Long squareTypeId;
    private String nameSquare;
    private Long provinceId;
    private Long zoneId;
    private Integer upgradePrice;
    private Integer amount;
    private Long eventId;
    private Long eventWhenPassingId;
    private Integer price;
    private Long cardToPickId;
}