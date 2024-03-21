
package model;

/**
 *
 * @author arnau
 */
public class Llibre {
    private int idLlibre, idCodi, idTipusFons, quantitatDisponible, idPrestatge, idBalda;
    private String titol, autor, isbn;

    public Llibre() {
    }

    public Llibre(int idCodi, int idTipusFons, int quantitatDisponible, int idPrestatge, int idBalda, String titol, String autor, String isbn) {
        this.idCodi = idCodi;
        this.idTipusFons = idTipusFons;
        this.quantitatDisponible = quantitatDisponible;
        this.idPrestatge = idPrestatge;
        this.idBalda = idBalda;
        this.titol = titol;
        this.autor = autor;
        this.isbn = isbn;
    }

    public int getIdLlibre() {
        return idLlibre;
    }

    public void setIdLlibre(int idLlibre) {
        this.idLlibre = idLlibre;
    }

    public int getIdCodi() {
        return idCodi;
    }

    public void setIdCodi(int idCodi) {
        this.idCodi = idCodi;
    }

    public int getIdTipusFons() {
        return idTipusFons;
    }

    public void setIdTipusFons(int idTipusFons) {
        this.idTipusFons = idTipusFons;
    }

    public int getQuantitatDisponible() {
        return quantitatDisponible;
    }

    public void setQuantitatDisponible(int quantitatDisponible) {
        this.quantitatDisponible = quantitatDisponible;
    }

    public int getIdPrestatge() {
        return idPrestatge;
    }

    public void setIdPrestatge(int idPrestatge) {
        this.idPrestatge = idPrestatge;
    }

    public int getIdBalda() {
        return idBalda;
    }

    public void setIdBalda(int idBalda) {
        this.idBalda = idBalda;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

   


    
}

