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
public class ZoneEntity {
    private Long zoneId;
    private String description;
}