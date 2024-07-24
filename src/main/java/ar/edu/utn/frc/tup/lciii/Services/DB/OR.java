package ar.edu.utn.frc.tup.lciii.Services.DB;

import ar.edu.utn.frc.tup.lciii.Conection.ConnexionDB;

import java.util.List;

/**
 * Only Read
 * @param <T> Entity que se pretende trabajar
 */
public interface OR <T> {

    ConnexionDB connexionDB = ConnexionDB.getInstance();

    T getById(Long id);
    List<T> getAll();
}
