package logica;

import basedades.ConnexioBD;
import dao.PrestatgeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Prestatge;

/**
 *
 * @author arnau
 */
public class PrestatgeDAOImpl implements PrestatgeDAO {

    Connection conn;

    public PrestatgeDAOImpl() throws SQLException {
        conn = ConnexioBD.obtenirConnexio();
    }

    private static final String OBTENIR_TOTS_ELS_PRESTATGES = "SELECT * FROM prestatges";
    private static final String OBTENIR_PRESTATGE_PER_ID = "SELECT * FROM prestatges WHERE idPrestatge = ?";
    private static final String AFEGIR_PRESTATGE = "INSERT INTO prestatges (nom) VALUES (?)";
    private static final String ACTUALITZAR_PRESTATGE = "UPDATE prestatges SET nom = ? WHERE idPrestatge = ?";
    private static final String ESBORRAR_PRESTATGE = "DELETE FROM prestatges WHERE idPrestatge = ?";
    private final String OBTENIR_TOTS_TIPUS = "SELECT * FROM prestatges";
    private final String OBTENIR_ID_PER_NOM = "SELECT idPrestatge FROM prestatges WHERE nom = ?";
    private final String OBTENIR_NOMS_COLUMNES = "SHOW COLUMNS FROM prestatges FROM m7uf1act11";
    private static final String COMPROVAR_PRESTATGE = "SELECT count(*) quantitat FROM prestatges WHERE nom = ?";

    
    @Override
    public Prestatge obtenirPrestatgePerId(int id) {

        Prestatge prestatge = new Prestatge();

        try {
            PreparedStatement ps = conn.prepareStatement(OBTENIR_PRESTATGE_PER_ID);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prestatge = mapResultSetToPrestatge(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return prestatge;
    }

    @Override
    public List<Prestatge> obtenirTotsElsPrestatges() {

        List<Prestatge> prestatges = new ArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement(OBTENIR_TOTS_ELS_PRESTATGES);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prestatges.add(mapResultSetToPrestatge(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return prestatges;
    }

    @Override
    public boolean afegir(Prestatge prestatge) {

        try {
            PreparedStatement ps = conn.prepareStatement(AFEGIR_PRESTATGE);
            ps.setString(1, prestatge.getNom());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualitzar(Prestatge prestatge) {
        try {
            PreparedStatement ps = conn.prepareStatement(ACTUALITZAR_PRESTATGE);
            ps.setString(1, prestatge.getNom());
            ps.setInt(2, prestatge.getIdPrestatge());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean esborrar(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement(ESBORRAR_PRESTATGE);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private Prestatge mapResultSetToPrestatge(ResultSet rs) {
        Prestatge prestatge = new Prestatge();

        try {
            prestatge.setIdPrestatge(rs.getInt("idPrestatge"));
            prestatge.setNom(rs.getString("nom"));
            // Mapejar altres atributs segons la teva estructura de taula
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

//        System.out.println(prestatge.getNom());
        return prestatge;
    }

    public String[] obtenirTotsTipus() {
        List<String> llistaTipus = new ArrayList<>();
        try ( PreparedStatement ps = conn.prepareStatement(OBTENIR_TOTS_TIPUS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                llistaTipus.add(rs.getString("nom"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar l'excepció segons la teva lògica d'aplicació
        }

        String[] llista = new String[llistaTipus.size()];
        for (int i = 0; i < llistaTipus.size(); i++) {
            llista[i] = llistaTipus.get(i);
        }

        return llista;
    }

    @Override
    public String[] obtenirNomsColumnes() {

        List<String> llistaNoms = new ArrayList<>();

        try ( PreparedStatement ps = conn.prepareStatement(OBTENIR_NOMS_COLUMNES)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                llistaNoms.add(rs.getString("Field"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] llista = new String[llistaNoms.size()];

        for (int i = 0; i < llistaNoms.size(); i++) {
            llista[i] = llistaNoms.get(i);
        }

        return llista;
    }

    @Override
    public int obtenirIdPerNom(String nom) {

        int id = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(OBTENIR_ID_PER_NOM);

            ps.setString(1, nom);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = (rs.getInt("idPrestatge"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    @Override
    public boolean comprovarPrestatge(String nomPrestatge) {
        int quantitat = 0;

        try ( PreparedStatement ps = conn.prepareStatement(COMPROVAR_PRESTATGE)) {
            ps.setString(1, nomPrestatge);
            ResultSet rs = ps.executeQuery();
            rs.next();
            quantitat = rs.getInt("quantitat");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantitat > 0;
    }

}
