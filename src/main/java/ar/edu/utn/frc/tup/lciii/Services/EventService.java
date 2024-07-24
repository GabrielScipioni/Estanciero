package ar.edu.utn.frc.tup.lciii.Services;
import ar.edu.utn.frc.tup.lciii.Model.Event.Event;
import ar.edu.utn.frc.tup.lciii.Model.Event.EventType;
import ar.edu.utn.frc.tup.lciii.Model.Event.MoneyStrategy;

public class EventService {
    public Event createEventFromKey(String key){
        //TODO: manejar la logica para que según que key en db de evento, se le asigne su estrategia o implementación concreta
       return new Event(EventType.MONEY, new MoneyStrategy());
    }
}
