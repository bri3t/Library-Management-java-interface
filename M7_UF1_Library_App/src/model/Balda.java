
package model;

/**
 *
 * @author arnau
 */
public class Balda {
    
    private int idBalda, idPrestatge;
    private String nom;

    public Balda() {
    }

    public Balda(int idBalda, int idPrestatge, String nom) {
        this.idBalda = idBalda;
        this.idPrestatge = idPrestatge;
        this.nom = nom;
    }

    public int getIdBalda() {
        return idBalda;
    }

    public void setIdBalda(int idBalda) {
        this.idBalda = idBalda;
    }

    public int getIdPrestatge() {
        return idPrestatge;
    }

    public void setIdPrestatge(int idPrestatge) {
        this.idPrestatge = idPrestatge;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
}
