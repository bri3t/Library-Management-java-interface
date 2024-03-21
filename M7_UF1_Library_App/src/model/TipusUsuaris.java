
package model;

/**
 *
 * @author arnau
 */
public class TipusUsuaris {
    
    private int idTipusUsuaris, idPrivilegis;
    private String tipus;
    

    public TipusUsuaris() {
    }
    
    public TipusUsuaris(String tipus, int idPrivilegis) {
        this.tipus = tipus;
        this.idPrivilegis = idPrivilegis;
    }
    
    
    public int getIdTipusUsuaris() {
        return idTipusUsuaris;
    }

    public void setIdTipusUsuaris(int idTipusUsuaris) {
        this.idTipusUsuaris = idTipusUsuaris;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public int getIdPrivilegis() {
        return idPrivilegis;
    }

    public void setIdPrivilegis(int idPrivilegis) {
        this.idPrivilegis = idPrivilegis;
    }

 
    
     
     
}
