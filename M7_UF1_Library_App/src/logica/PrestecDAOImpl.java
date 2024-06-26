package logica;

import basedades.ConnexioBD;
import dao.PrestecDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Llibre;
import model.Prestec;

/**
 *
 * @author arnau
 */
public class PrestecDAOImpl implements PrestecDAO {

    Connection conn;

    private static final String FILTRAR_TAULES = "SELECT * FROM llibre WHERE titol LIKE ? OR autor LIKE ? OR isbn LIKE ?";
    private static final String FILTRAR_TAULES_AMB_CONDICIO_PRESTAT = "SELECT * FROM llibre WHERE (titol LIKE ? OR autor LIKE ? OR isbn LIKE ?) "
            + "AND idLlibre IN (SELECT idLlibre FROM prestec)";

    private static final String FILTRAR_TAULES_AMB_CONDICIO_NO_PRESTAT = "SELECT * FROM llibre WHERE (titol LIKE ? OR autor LIKE ? OR isbn LIKE ?) "
            + "AND idLlibre NOT IN (SELECT idLlibre FROM prestec)";

    private static final String FILTRAR_NO_PRESTATS = "SELECT * FROM llibre WHERE idLlibre NOT IN ("
            + "SELECT idLlibre FROM prestec)";
    private static final String REALITZAR_PRESTEC = "INSERT INTO prestec (idLlibre, idPersona, dataPrestec, dataDevolucio) VALUES (?,?,?,?)";
    private static final String FILTRAR_PRESTATS = "SELECT * FROM llibre WHERE idLlibre IN ("
            + "SELECT idLlibre FROM prestec)";
    private static final String RETORNAR_LLIBRE = "DELETE FROM prestec WHERE idLlibre = ?";
    private static final String COMPROVAR_SI_ES_PRESTAT = "SELECT COUNT(idLlibre) FROM prestec WHERE idLlibre = ?";
    private static final String OBTENIR_NUM_DIES = "SELECT tempsPrestec FROM configuracio";
    private static final String OBTENIR_DATA_DEVOLUCIO_PER_ID_LLIBRE = "SELECT dataDevolucio FROM prestec WHERE idLlibre = ?";
    private static final String OBTENIR_DATA_PRESTEC_PER_ID_LLIBRE = "SELECT dataPrestec FROM prestec WHERE idLlibre = ?";
    private static final String OBTENIR_IDPERSONA_PER_IDLLIBRE = "SELECT idPersona FROM prestec WHERE idLlibre = ?";
    private final String MODIFICAR_DATA_DEVOLUCIO = "UPDATE prestec SET dataDevolucio = ? where idLlibre = ?";

    public PrestecDAOImpl() {
        try {
            conn = ConnexioBD.obtenirConnexio();
        } catch (SQLException ex) {
            Logger.getLogger(PrestecDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    @Override
    public List<Llibre> filtrarTaulaAmbCondicio(String paraula, boolean esPrestat) {
        List<Llibre> llibres = new ArrayList<>();

        String filtre = esPrestat ? FILTRAR_TAULES_AMB_CONDICIO_PRESTAT : FILTRAR_TAULES_AMB_CONDICIO_NO_PRESTAT;

        try ( PreparedStatement ps = conn.prepareStatement(filtre);) {
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
    public boolean realitzarPrestec(Prestec p) {
        try {
            PreparedStatement ps = conn.prepareStatement(REALITZAR_PRESTEC);
            ps.setInt(1, p.getIdLlibre());
            ps.setInt(2, p.getIdPersona());
            ps.setDate(3, p.getDataPrestec());
            ps.setDate(4, p.getDataDevolucio());
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
        if (esPrestat) {
            filtre = FILTRAR_PRESTATS;
        }

        try ( PreparedStatement ps = conn.prepareStatement(filtre);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Llibre llibre = mapResultSetToLlibre(rs);
                llibres.add(llibre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        for (Llibre llibre : llibres) {
//            System.out.println(llibre.getTitol());
//        }
        return llibres;

    }

    @Override
    public boolean comprovarSiEsprestat(int idLlibre) {

        int num = 1;
        try {
            PreparedStatement ps = conn.prepareStatement(COMPROVAR_SI_ES_PRESTAT);
            ps.setInt(1, idLlibre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }

        } catch (SQLException ex) {
        }

        return num > 0;

    }

    @Override
    public boolean realitzarRetorn(int idLlibre) {
        try {
            PreparedStatement ps = conn.prepareStatement(RETORNAR_LLIBRE);
            ps.setInt(1, idLlibre);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    @Override
    public Date obtenirDataDevolucioPerIdLlibre(int idLlibre) {
        return obtenirData(idLlibre, OBTENIR_DATA_DEVOLUCIO_PER_ID_LLIBRE);
    }

    @Override
    public Date obtenirDataPrestecPerIdLlibre(int idLlibre) {
        return obtenirData(idLlibre, OBTENIR_DATA_PRESTEC_PER_ID_LLIBRE);
    }

    private Date obtenirData(int idLlibre, String query) {
        Date data = null;

        String dataARecollir = query.equals(OBTENIR_DATA_PRESTEC_PER_ID_LLIBRE) ? "dataPrestec" : "dataDevolucio";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idLlibre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data = rs.getDate(dataARecollir);
            }

        } catch (SQLException ex) {
        }

        return data;
    }

    @Override
    public int obtenirIdPersonalPerIdLlibre(int idLibro) {
        try ( PreparedStatement ps = conn.prepareStatement(OBTENIR_IDPERSONA_PER_IDLLIBRE)) {
            ps.setInt(1, idLibro);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idPersona");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Retorna -1 o algún código que indique que no se encontró el dato
    }

    @Override
    public boolean modificarDataDevolucio(Date data, int idLlibre) {
        try {
            System.out.println(data);
            PreparedStatement ps = conn.prepareStatement(MODIFICAR_DATA_DEVOLUCIO);
            ps.setDate(1, data);
            ps.setInt(2, idLlibre);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
