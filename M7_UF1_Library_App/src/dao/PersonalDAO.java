package dao;

import java.util.List;
import model.Persona;

/**
 *
 * @author arnau
 */
public interface PersonalDAO {

    List<Persona> obtenirTotsPersonal();

    List<Persona> obtenirIdPerNumCarnet(String numeroCarnet);

    List<Persona> obtenirIdPerId(int idPersonal);

    Persona obtenirPersonaPerNumCarnet(String numeroCarnet);

    boolean marcatSansionatPerId(int idPersona);

}
