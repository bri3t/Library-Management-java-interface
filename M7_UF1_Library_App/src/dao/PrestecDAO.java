
package dao;

import java.util.List;
import model.Llibre;
import model.Prestec;

/**
 *
 * @author arnau
 */
public interface PrestecDAO {
    
    List<Llibre> filtrarTaula(String paraula);
    
    boolean realitzarPrestec(Prestec p);
    
    int obtenirDiesPrestec();
    
    List<Llibre> filtrarPrestats(boolean esPrestat);
    
    boolean comprovarSiEsprestat(int idLlibre);
    
    boolean realitzarRetorn(int idLlibre);
    
}
