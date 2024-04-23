package logica;

import basedades.ConnexioBD;
import dao.PersonalDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Persona;

/**
 *
 * @author arnau
 */
public class PersonalDAOImpl implements PersonalDAO {

    private static final String OBTENIR_TOTS_PERSONAL = "SELECT * FROM personal";
    private static final String OBTENIR_PERSONA_PER_NUM_CARNET = "SELECT* FROM personal WHERE numeroCarnet LIKE ?";
    private static final String OBTENIR_PERSONA_PER_ID = "SELECT * FROM personal WHERE idPersonal = ?";
    private static final String MARCAR_SANSIONAT_PER_ID = "UPDATE personal SET sancionat = 1 WHERE idPersonal = ?";

    private Connection conn;

    public PersonalDAOImpl() {
        try {
            this.conn = ConnexioBD.obtenirConnexio();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
            persona.setSansionat(rs.getBoolean("sancionat"));
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

    @Override
    public boolean marcatSansionatPerId(int idPersona) {
        try {
            PreparedStatement ps = conn.prepareStatement(MARCAR_SANSIONAT_PER_ID);
            ps.setInt(1, idPersona);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;  // Retorna true si se actualizó al menos una fila
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
