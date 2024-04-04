package logica;

import basedades.ConnexioBD;
import dao.LoginDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuari;

/**
 *
 * @author arnau
 */
public class LoginDAOImpl implements LoginDAO {

    TipusUsuariDAOImpl tudi = new TipusUsuariDAOImpl();

    private static final String VERIFICAR_USUARI = "SELECT * FROM usuaris WHERE nomUsuari = ? AND password = MD5(?)";
    Connection conn;

    public LoginDAOImpl() throws SQLException {
        conn = ConnexioBD.obtenirConnexio();

    }

    @Override
    public Usuari verificarLogin(String usr, String passwd) {
        Usuari usuari = null;

        try ( PreparedStatement ps = conn.prepareStatement(VERIFICAR_USUARI)) {
            ps.setString(1, usr);
            ps.setString(2, passwd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuari = mapResultSetToUsuari(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuari;

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

}
