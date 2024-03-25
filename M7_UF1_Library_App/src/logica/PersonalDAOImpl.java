package logica;

import basedades.ConnexioBD;
import dao.PersonalDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Persona;

/**
 *
 * @author arnau
 */
public class PersonalDAOImpl implements PersonalDAO {

    private static final String OBTENIR_TOTS_PERSONAL = "SELECT * FROM personal";
    private static final String OBTENIR_PERSONA_PER_NUM_CARNET = "SELECT* FROM personal WHERE numeroCarnet LIKE ?";
    private static final String OBTENIR_PERSONA_PER_ID = "SELECT * FROM personal WHERE idPersonal = ?";

    private Connection conn;

    public PersonalDAOImpl() throws SQLException {
        this.conn = ConnexioBD.obtenirConnexio();
    }

    @Override
    public List<Persona> obtenirTotsPersonal() {
        List<Persona> persones = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(OBTENIR_TOTS_PERSONAL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                persones.add(mapResultSetTopPersona(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return persones;
    }

    private Persona mapResultSetTopPersona(ResultSet rs) {
        Persona persona = new Persona();

        try {
            persona.setIdPersonal(rs.getInt("idPersonal"));
            persona.setNom(rs.getString("nom"));
            persona.setCognom(rs.getString("cognom"));
            persona.setIdTipususuari(rs.getInt("idTipusUsuari"));
            persona.setNumeroCarnet(rs.getInt("numeroCarnet"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return persona;
    }

    @Override
    public List<Persona> obtenirIdPerNumCarnet(String numeroCarnet) {
        List<Persona> llista = new ArrayList<>();
        Persona persona = new Persona();
        
        try {
            PreparedStatement ps = conn.prepareStatement(OBTENIR_PERSONA_PER_NUM_CARNET);
            String searchTerm = numeroCarnet + "%";  // Reemplaza con tu término de búsqueda

            ps.setString(1, searchTerm);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                persona = mapResultSetTopPersona(rs);
                llista.add(persona);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return llista;
    }
    
    @Override
    public Persona obtenirPersonaPerNumCarnet(String numeroCarnet) {
        Persona persona = new Persona();
        try {
            PreparedStatement ps = conn.prepareStatement(OBTENIR_PERSONA_PER_NUM_CARNET);

            ps.setString(1, numeroCarnet);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                persona = mapResultSetTopPersona(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return persona;
    }
    

    @Override
    public List<Persona> obtenirIdPerId(int idPersonal) {
        Persona persona = new Persona();
        List<Persona> llista = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(OBTENIR_PERSONA_PER_ID);

            ps.setInt(1, idPersonal);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                persona = mapResultSetTopPersona(rs);
                llista.add(persona);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return llista;
    }

}
