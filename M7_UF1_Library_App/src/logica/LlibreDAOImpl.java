package logica;

import basedades.ConnexioBD;
import dao.LlibreDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Llibre;

/**
 *
 * @author arnau
 */
public class LlibreDAOImpl implements LlibreDAO {

    private static final String OBTENIR_TOTS_ELS_LLIBRES = "SELECT * FROM llibre";
    private static final String OBTENIR_LLIBRE_PER_ID = "SELECT * FROM llibre WHERE idLlibre = ?";
    private static final String AFEGIR_LLIBRE = "INSERT INTO llibre (idCodi, idTipusfons, titol, autor, isbn, quantitatDisponible, idPrestatge, idBalda) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String ACTUALITZAR_LLIBRE = "UPDATE llibre SET idCodi = ?, idTipusfons = ?, titol = ?, autor = ? , isbn = ?, quantitatDisponible = ? , idPrestatge= ?, idBalda= ? WHERE idLlibre = ?";
    private static final String ESBORRAR_LLIBRE = "DELETE FROM llibre WHERE idLlibre = ?";
    private final String OBTENIR_NOMS_COLUMNES = "SHOW COLUMNS FROM llibre FROM m7uf1act11";
    private static final String COMPROVAR_IDCODI = "SELECT count(*) quantitat FROM llibre WHERE idCodi = ?";

    private Connection conn;

    public LlibreDAOImpl() throws SQLException {
        this.conn = ConnexioBD.obtenirConnexio();
    }

    @Override
    public List<Llibre> obtenirTots() {
        List<Llibre> llibres = new ArrayList<>();
        try (
                 PreparedStatement ps = conn.prepareStatement(OBTENIR_TOTS_ELS_LLIBRES);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Llibre llibre = mapResultSetToLlibre(rs);
                llibres.add(llibre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar l'excepció segons la teva lògica d'aplicació
        }
        return llibres;
    }

    @Override
    public Llibre obtenirPerId(int id) {
        Llibre llibre = null;
        try ( PreparedStatement ps = conn.prepareStatement(OBTENIR_LLIBRE_PER_ID)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    llibre = mapResultSetToLlibre(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar l'excepció segons la teva lògica d'aplicació
        }
        return llibre;
    }

    @Override
    public boolean afegir(Llibre llibre) {
        try {
            PreparedStatement ps = conn.prepareStatement(AFEGIR_LLIBRE);
            ps.setInt(1, llibre.getIdCodi());
            ps.setInt(2, llibre.getIdTipusFons());
            ps.setString(3, llibre.getTitol());
            ps.setString(4, llibre.getAutor());
            ps.setString(5, llibre.getIsbn());
            ps.setInt(6, llibre.getQuantitatDisponible());
            ps.setInt(7, llibre.getIdPrestatge());
            ps.setInt(8, llibre.getIdBalda());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualitzar(Llibre llibre) {
        try {
            PreparedStatement ps = conn.prepareStatement(ACTUALITZAR_LLIBRE);
            ps.setInt(1, llibre.getIdCodi());
            ps.setInt(2, llibre.getIdTipusFons());
            ps.setString(3, llibre.getTitol());
            ps.setString(4, llibre.getAutor());
            ps.setString(5, llibre.getIsbn());
            ps.setInt(6, llibre.getQuantitatDisponible());
            ps.setInt(7, llibre.getIdPrestatge());
            ps.setInt(8, llibre.getIdBalda());
            ps.setInt(9, llibre.getIdLlibre());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean esborrar(int id) {
        try ( PreparedStatement ps = conn.prepareStatement(ESBORRAR_LLIBRE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // Manejar l'excepció segons la teva lògica d'aplicació
        }
    }

    public String[] obtenirTotsTipus() {

        List<String> llistaTipus = new ArrayList<>();
        try ( PreparedStatement ps = conn.prepareStatement(OBTENIR_TOTS_ELS_LLIBRES)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                llistaTipus.add(rs.getString("tipus"));

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

    private Llibre mapResultSetToLlibre(ResultSet rs) {
        Llibre llibre = new Llibre();

        try {
            llibre.setIdLlibre(rs.getInt("idLlibre"));
            llibre.setIdCodi(rs.getInt("idCodi"));
            llibre.setIdTipusFons(rs.getInt("idTipusfons"));
            llibre.setTitol(rs.getString("titol"));
            llibre.setAutor(rs.getString("autor"));
            llibre.setIsbn(rs.getString("isbn"));
            llibre.setQuantitatDisponible(rs.getInt("quantitatDisponible"));
            llibre.setIdPrestatge(rs.getInt("idPrestatge"));
            llibre.setIdBalda(rs.getInt("idBalda"));
            // Mapejar altres atributs segons la teva estructura de taula
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return llibre;
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
    public boolean comprovarLlibre(int idCodiLlibre) {
        int quantitat = 0;

        try ( PreparedStatement ps = conn.prepareStatement(COMPROVAR_IDCODI)) {
            ps.setInt(1, idCodiLlibre);
            ResultSet rs = ps.executeQuery();
            rs.next();
            quantitat = rs.getInt("quantitat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return quantitat > 0;
    }

 

}
