package dao;

import java.util.List;
import model.Balda;

/**
 *
 * @author arnau
 */
public interface BaldaDAO {

    Balda obtenirBaldaPerId(int id);

    List<Balda> obtenirTotesLesBaldes();

    boolean afegir(Balda balda);

    boolean actualitzar(Balda balda);

    boolean esborrar(int id);

    public String[] obtenirTotsTipus();

    public String[] obtenirNomsColumnes();

    public int obtenirIdPerNom(String nom);

}
