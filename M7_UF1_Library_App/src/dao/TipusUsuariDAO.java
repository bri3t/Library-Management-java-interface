package dao;


import model.TipusUsuaris;

/**
 *
 * @author arnaus
 */
public interface TipusUsuariDAO {

    public TipusUsuaris obtenirTipusUsuariPerId(int id);

    public String[] obtenirTotsTipus();

    public String[] obtenirNomsColumnes();

    public int obtenirIdPerNom(String nom);
    
    public int obtenirIdPrivilegiPerIdTipus(int id);
    
    int obtenirIdPrivilegoPerIdTipusUsuari(int idTipusUsuari);
    
    int obtenirIdPrivilegiAdministrador();
    
}
