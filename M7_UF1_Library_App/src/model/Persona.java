
package model;

/**
 *
 * @author arnau
 */
public class Persona {
    
    private int idPersonal, idTipususuari, numeroCarnet;
    private String nom, cognom;

    public Persona() {
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public int getIdTipususuari() {
        return idTipususuari;
    }

    public void setIdTipususuari(int idTipususuari) {
        this.idTipususuari = idTipususuari;
    }

    public int getNumeroCarnet() {
        return numeroCarnet;
    }

    public void setNumeroCarnet(int numeroCarnet) {
        this.numeroCarnet = numeroCarnet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }
    
    
}
