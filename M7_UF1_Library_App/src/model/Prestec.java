package model;


/**
 *
 * @author arnau
 */

import java.sql.Date;

public class Prestec {

    private int idPrestec, idLlibre, idPersona;
    private Date dataPrestec, dataDevolucio;

    public Prestec() {
    }

    public int getIdPrestec() {
        return idPrestec;
    }

    public void setIdPrestec(int idPrestec) {
        this.idPrestec = idPrestec;
    }

    public int getIdLlibre() {
        return idLlibre;
    }

    public void setIdLlibre(int idLlibre) {
        this.idLlibre = idLlibre;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Date getDataPrestec() {
        return dataPrestec;
    }

    public void setDataPrestec(Date dataPrestec) {
        this.dataPrestec = dataPrestec;
    }

    public Date getDataDevolucio() {
        return dataDevolucio;
    }

    public void setDataDevolucio(Date dataDevolucio) {
        this.dataDevolucio = dataDevolucio;
    }


}