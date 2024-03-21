
package dao;

import java.util.List;
import model.Llibre;

/**
 *
 * @author arnau
 */
public interface PrestecDAO {
    
    List<Llibre> filtrarTaula(String paraula);
    
    boolean realitzarPrestec(int idLlibre, int idPersona);
    
    int obtenirDiesPrestec();
    
    List<Llibre> filtrarPrestats(boolean esPrestat);
    
}
