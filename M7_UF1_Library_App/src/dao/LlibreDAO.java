package dao;

/**
 *
 * @author arnau
 */
import java.util.List;
import model.Llibre;

public interface LlibreDAO {

    List<Llibre> obtenirTots();

    Llibre obtenirPerId(int id);

    boolean afegir(Llibre llibre);

    boolean actualitzar(Llibre llibre);

    boolean esborrar(int id);

    public String[] obtenirTotsTipus();

    public String[] obtenirNomsColumnes();
    
    boolean comprovarLlibre(int idCodiLlibre);
    

}
