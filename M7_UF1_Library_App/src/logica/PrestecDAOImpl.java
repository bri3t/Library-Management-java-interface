package logica;

import basedades.ConnexioBD;
import dao.PrestecDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import model.Llibre;

/**
 *
 * @author arnau
 */
public class PrestecDAOImpl implements PrestecDAO {

    Connection conn;

    private static final String FILTRAR_TAULES = "SELECT * FROM llibre WHERE titol LIKE ? OR autor LIKE ? OR isbn LIKE ?";
    private static final String REALITZAR_PRESTEC = "INSERT INTO prestec (idLlibre, idPersona, dataPrestec, dataDevolucio) VALUES (?,?,?,?)";
    private static final String OBTENIR_NUM_DIES = "SELECT tempsPrestec FROM configuracio";
    private static final String FILTRAR_NO_PRESTATS = "SELECT * FROM llibre WHERE idLlibre NOT IN ("
            + "SELECT idLlibre FROM prestec)";
    private static final String FILTRAR_PRESTATS = "SELECT * FROM llibre WHERE idLlibre IN ("
            + "SELECT idLlibre FROM prestec)";

    public PrestecDAOImpl() throws SQLException {
        conn = ConnexioBD.obtenirConnexio();
    }

    @Override
    public List<Llibre> filtrarTaula(String paraula) {
        List<Llibre> llibres = new ArrayList<>();
        try ( PreparedStatement ps = conn.prepareStatement(FILTRAR_TAULES);) {
            String searchTerm = "%" + paraula + "%";  // Reemplaza con tu término de búsqueda
            ps.setString(1, searchTerm);
            ps.setString(2, searchTerm);
            ps.setString(3, searchTerm);

            ResultSet rs = ps.executeQuery();
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
    public boolean realitzarPrestec(int idLlibre, int idPersona) {
        // Obtenir la data d'avui
        Calendar calendar = Calendar.getInstance();
        Date avui = new Date(calendar.getTimeInMillis());

        int numDiesASumar = obtenirDiesPrestec();

        // Afegir 15 dies a la data actual
        calendar.add(Calendar.DATE, numDiesASumar);
        Date avuiMesXDies = new Date(calendar.getTimeInMillis());

        try {
            PreparedStatement ps = conn.prepareStatement(REALITZAR_PRESTEC);
            ps.setInt(1, idLlibre);
            ps.setInt(2, idPersona);
            ps.setDate(3, avui);
            ps.setDate(4, avuiMesXDies);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public int obtenirDiesPrestec() {
        int numDies = 0;
        try (
                 PreparedStatement ps = conn.prepareStatement(OBTENIR_NUM_DIES);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                return rs.getInt("tempsPrestec");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numDies;
    }

    @Override
    public List<Llibre> filtrarPrestats(boolean esPrestat) {
        List<Llibre> llibres = new ArrayList<>();

        String filtre = FILTRAR_NO_PRESTATS;
        if (esPrestat)filtre = FILTRAR_PRESTATS;

//        System.out.println(filtre);
        try ( PreparedStatement ps = conn.prepareStatement(filtre);) {
//            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Llibre llibre = mapResultSetToLlibre(rs);
                llibres.add(llibre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar l'excepció segons la teva lògica d'aplicació
        }

//        for (Llibre llibre : llibres) {
//            System.out.println(llibre.getTitol());
//        }

        return llibres;

    }
}
