
package basedades;

/**
 *
 * @author arnau
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexioBD {
    private static final String URL = "jdbc:mysql://localhost:3306/m7uf1act11";
    private static final String USUARI = "root";
    private static final String CONTRASENYA = "admin1234";

    public static Connection obtenirConnexio() throws SQLException {
        return DriverManager.getConnection(URL, USUARI, CONTRASENYA);
    }
}
