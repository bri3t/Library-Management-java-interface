package logica;

import basedades.ConnexioBD;
import dao.BaldaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Balda;

/**
 *
 * @author arnau
 */
public class BaldaDAOImpl implements BaldaDAO {

    private static final String OBTENIR_TOTES_LES_BALDES = "SELECT * FROM baldes";
    private static final String OBTENIR_BALDA_PER_ID = "SELECT * FROM baldes WHERE idBalda = ?";
    private static final String AFEGIR_BALDA = "INSERT INTO baldes (nom, idPrestatge) VALUES (?, ?)";
    private static final String ACTUALITZAR_BALDA = "UPDATE baldes SET nom = ?, idPrestatge = ? WHERE idBalda = ?";
    private static final String ESBORRAR_BALDA = "DELETE FROM baldes WHERE idBalda = ?";
    private final String OBTENIR_TOTS_TIPUS = "SELECT * FROM baldes";
    private final String OBTENIR_NOMS_COLUMNES = "SHOW COLUMNS FROM baldes FROM m7uf1act11";
    private final String OBTENIR_ID_PER_NOM = "SELECT idBalda FROM baldes WHERE nom = ?";
    private static final String COMPROVAR_BALDA = "SELECT count(*) quantitat FROM baldes WHERE nom = ?";

    private Connection conn;

    public BaldaDAOImpl() throws SQLException {
        this.conn = ConnexioBD.obtenirConnexio();
    }

    @Override
    public Balda obtenirBaldaPerId(int id) {
        Balda balda = new Balda();
        try {
            PreparedStatement ps = conn.prepareStatement(OBTENIR_BALDA_PER_ID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                balda = mapResultSetToBalda(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return balda;
    }

    @Override
    public List<Balda> obtenirTotesLesBaldes() {

        List<Balda> baldes = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(OBTENIR_TOTES_LES_BALDES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                baldes.add(mapResultSetToBalda(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return baldes;
    }

    @Override
    public boolean afegir(Balda balda) {
        try {
            PreparedStatement ps = conn.prepareStatement(AFEGIR_BALDA);
            ps.setString(1, balda.getNom());
            ps.setInt(2, balda.getIdPrestatge());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualitzar(Balda balda) {
        try {
            PreparedStatement ps = conn.prepareStatement(ACTUALITZAR_BALDA);
            ps.setString(1, balda.getNom());
            ps.setInt(2, balda.getIdPrestatge());
            ps.setInt(3, balda.getIdBalda());
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
            PreparedStatement ps = conn.prepareStatement(ESBORRAR_BALDA);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private Balda mapResultSetToBalda(ResultSet rs) {
        Balda balda = new Balda();

        try {
            balda.setIdBalda(rs.getInt("idBalda"));
            balda.setNom(rs.getString("nom"));
            balda.setIdPrestatge(rs.getInt("idPrestatge"));
            // Mapejar altres atributs segons la teva estructura de taula
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return balda;
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
                id = (rs.getInt("idBalda"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    @Override
    public boolean comprovarBalda(String nombalda) {
        int quantitat = 0;

        try ( PreparedStatement ps = conn.prepareStatement(COMPROVAR_BALDA)) {
            ps.setString(1, nombalda);
            ResultSet rs = ps.executeQuery();
            rs.next();
            quantitat = rs.getInt("quantitat");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantitat > 0;
    }
}
