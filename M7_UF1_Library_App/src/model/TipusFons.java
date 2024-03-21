
package model;

/**
 *
 * @author arnau
 */
public class TipusFons {
    
    private int idTipusFons;
    private String tipus;

    public TipusFons() {
    }

    public TipusFons(String tipus) {
        this.tipus = tipus;
    }

    public int getIdTipusFons() {
        return idTipusFons;
    }

    public void setIdTipusFons(int idTipusFons) {
        this.idTipusFons = idTipusFons;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
    
    
    
}
