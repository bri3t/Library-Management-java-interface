package logica;

/**
 *
 * @author arnau
 */
import basedades.ConnexioBD;
import dao.UsuariDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuari;

public class UsuariDAOImpl implements UsuariDAO {

    Connection conn;

    public UsuariDAOImpl() throws SQLException {
        conn = ConnexioBD.obtenirConnexio();
    }

    private static final String OBTENIR_TOTS_ELS_USUARIS = "SELECT * FROM usuaris";
    private static final String OBTENIR_USUARI_PER_ID = "SELECT * FROM usuaris WHERE idUsuari = ?";
    private static final String AFEGIR_USUARI = "INSERT INTO usuaris (nomUsuari, password, idTipusUsuari) VALUES (?, MD5(?), ?)";
    private static final String ESBORRAR_USUARI = "DELETE FROM usuaris WHERE idUsuari = ?";
    private static final String OBTENIR_ID_PER_NOM = "SELECT idusuari FROM usuaris WHERE nomUsuari = ?";
    private static final String COMPROVAR_USUARI = "SELECT count(*) quantitat FROM usuaris WHERE nomUsuari = ?";

    @Override
    public List<Usuari> obtenirTots() {
        List<Usuari> usuaris = new ArrayList<>();
        try (
                 PreparedStatement ps = conn.prepareStatement(OBTENIR_TOTS_ELS_USUARIS);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuari usuari = mapResultSetToUsuari(rs);
                usuaris.add(usuari);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar l'excepció segons la teva lògica d'aplicació
        }
        return usuaris;
    }

    @Override
    public Usuari obtenirPerId(int id) {
        Usuari usuari = null;
        try ( PreparedStatement ps = conn.prepareStatement(OBTENIR_USUARI_PER_ID)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuari = mapResultSetToUsuari(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar l'excepció segons la teva lògica d'aplicació
        }
        return usuari;
    }

    @Override
    public boolean afegir(Usuari usuari) {
        try ( PreparedStatement ps = conn.prepareStatement(AFEGIR_USUARI)) {
            ps.setString(1, usuari.getNom());
            ps.setString(2, usuari.getPassword());
            ps.setInt(3, usuari.getTipusUsuari());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // Manejar l'excepció segons la teva lògica d'aplicació
        }
    }

    @Override
    public boolean actualitzar(Usuari usuari, String nomAntic, String contrasenya) {
        try {
            String sqlActualitzar = "UPDATE usuaris SET nomUsuari = ?, password = MD5(?) , idTipusUsuari = ? WHERE idUsuari = ?";
            if (comprovarContrasenya(nomAntic, contrasenya)) {
                sqlActualitzar = "UPDATE  usuaris SET nomUsuari = ?, password = ?,  idTipusUsuari = ? WHERE idUsuari = ?";
            }

//            System.out.println(sqlActualitzar);
            PreparedStatement ps = conn.prepareStatement(sqlActualitzar);
            ps.setString(1, usuari.getNom());
            ps.setString(2, usuari.getPassword());
            ps.setInt(3, usuari.getTipusUsuari());
            ps.setInt(4, usuari.getIdUsuari());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // Manejar l'excepció segons la teva lògica d'aplicació
        }
    }

    @Override
    public boolean esborrar(int id) {
        try ( PreparedStatement ps = conn.prepareStatement(ESBORRAR_USUARI)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // Manejar l'excepció segons la teva lògica d'aplicació
        }
    }

    private Usuari mapResultSetToUsuari(ResultSet rs) throws SQLException {
        Usuari usuari = new Usuari();
        usuari.setIdUsuari(rs.getInt("idUsuari"));
        usuari.setNom(rs.getString("nomUsuari"));
        usuari.setPassword(rs.getString("password"));
        usuari.setTipusUsuari(rs.getInt("idTipusUsuari"));
        // Mapejar altres atributs segons la teva estructura de taula
        return usuari;
    }

    @Override
    public String obtenirContraseñaPerId(int idUsuari) {

        String querySQL = "SELECT password FROM usuaris WHERE idUsuari = ?";

        try {
            PreparedStatement sentencia = conn.prepareStatement(querySQL);
            sentencia.setInt(1, idUsuari);
            ResultSet results = sentencia.executeQuery();

            while (results.next()) {
                return results.getString("password");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public boolean comprovarContrasenya(String nomAntic, String contrasenya) throws SQLException {

        // Preparar la sentencia SQLl
        String sql = "SELECT CASE WHEN COUNT(*) > 0 THEN 'true' ELSE 'false' END AS existe "
                + "FROM usuaris WHERE password = ? AND nomUsuari = ?";
        PreparedStatement sentencia = conn.prepareStatement(sql);
        sentencia.setString(1, contrasenya);
        sentencia.setString(2, nomAntic);

        ResultSet resultSet = sentencia.executeQuery();
        if (resultSet.next()) {
            return resultSet.getBoolean("existe");
        }
        return false;
    }

    @Override
    public boolean comprovarUsuari(String nomUsuari) {
        int quantitat = 0;

        try ( PreparedStatement ps = conn.prepareStatement(COMPROVAR_USUARI)) {
            ps.setString(1, nomUsuari);
            ResultSet rs = ps.executeQuery();
            rs.next();
            quantitat = rs.getInt("quantitat");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quantitat > 0;
    }

}
