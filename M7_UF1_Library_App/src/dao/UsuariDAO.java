
package dao;

/**
 *
 * @author arnau
 */
import java.sql.SQLException;
import java.util.List;
import model.Usuari;

public interface UsuariDAO {
    List<Usuari> obtenirTots();
    Usuari obtenirPerId(int id);
    boolean afegir(Usuari usuari);
    boolean actualitzar(Usuari usuari, String nomAntic, String contrasenya);
    boolean esborrar(int id);
    boolean comprovarContrasenya(String nomAntic, String contrasenya)  throws SQLException;
    String obtenirContraseñaPerId(int idUsari);
}
