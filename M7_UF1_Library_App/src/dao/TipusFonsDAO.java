package dao;

import java.util.List;
import model.TipusFons;

/**
 *
 * @author arnau
 */
public interface TipusFonsDAO {

    TipusFons obtenirTipusFonssPerId(int id);

    String[] obtenirTotsTipusFons();

    boolean afegir(TipusFons tipusFons);

    boolean actualitzar(TipusFons tipusFons);

    boolean esborrar(int id);

    public String[] obtenirNomsColumnes();

    List<TipusFons> obtenirTotesLesFons();

    public int obtenirIdPerNom(String nom);
    
    boolean comprovarTipusFons(String tipus);

}
