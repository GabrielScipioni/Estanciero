package ar.edu.utn.frc.tup.lciii.Model.Event;

import ar.edu.utn.frc.tup.lciii.Model.Card.CardType;
import ar.edu.utn.frc.tup.lciii.Model.Game.Game;
import ar.edu.utn.frc.tup.lciii.Model.Player.BotPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.HumanPlayer;
import ar.edu.utn.frc.tup.lciii.Model.Player.Player;
import ar.edu.utn.frc.tup.lciii.Model.Square.Square;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetOutOfJailStrategy.class, name = "GetOutOfJailStrategy"),
        @JsonSubTypes.Type(value = GoToJailStrategy.class, name = "GoToJailStrategy"),
        @JsonSubTypes.Type(value = JailStrategy.class, name = "JailStrategy"),
        @JsonSubTypes.Type(value = MoneyStrategy.class, name = "MoneyStrategy"),
        @JsonSubTypes.Type(value = MoveStepsStrategy.class, name = "MoveStepsStrategy"),
        @JsonSubTypes.Type(value = MoveToSquareStrategy.class, name = "MoveToSquareStrategy"),
        @JsonSubTypes.Type(value = PayOrPickupStrategy.class, name = "PayOrPickupStrategy"),
        @JsonSubTypes.Type(value = PayPerUpgradeStrategy.class, name = "PayPerUpgradeStrategy"),
        @JsonSubTypes.Type(value = PickUpCardTypeStrategy.class, name = "PickUpCardTypeStrategy"),
        @JsonSubTypes.Type(value = TeleportStrategy.class, name = "TeleportStrategy"),
        @JsonSubTypes.Type(value = ChargeAllPlayersStrategy.class, name = "ChargeAllPlayersStrategy")
})
public interface EventStrategy {
    void execute(Game game, Player player, Square square, Integer amount, CardType cardType);
}
