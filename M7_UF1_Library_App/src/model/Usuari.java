
package model;

/**
 *
 * @author arnau
 */
public class Usuari {
    private int idUsuari;
    private String nom;
    private String password;
    private int tipusUsuari;
    private boolean sansionat;
    

    public Usuari() {
    }

    public Usuari(String nom, String password, int tipusUsuari) {
        this.nom = nom;
        this.password = password;
        this.tipusUsuari = tipusUsuari;
        this.sansionat = false;
    }

    public boolean isSansionat() {
        return sansionat;
    }

    public void setSansionat(boolean sansionat) {
        this.sansionat = sansionat;
    }



    public int getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipusUsuari() {
        return tipusUsuari;
    }

    public void setTipusUsuari(int tipusUsuari) {
        this.tipusUsuari = tipusUsuari;
    }
    
    

   

 
}
