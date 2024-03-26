package dao;

import java.util.List;
import model.Prestatge;

/**
 *
 * @author arnau
 */
public interface PrestatgeDAO {

    Prestatge obtenirPrestatgePerId(int id);

    List<Prestatge> obtenirTotsElsPrestatges();

    boolean afegir(Prestatge prestatge);

    boolean actualitzar(Prestatge prestatge);

    boolean esborrar(int id);

    public String[] obtenirNomsColumnes();
    
    public int obtenirIdPerNom(String nom);
    
    boolean comprovarPrestatge(String nomPrestatge);

}
