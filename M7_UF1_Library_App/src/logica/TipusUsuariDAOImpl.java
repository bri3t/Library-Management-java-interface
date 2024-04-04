package logica;

import basedades.ConnexioBD;
import dao.TipusUsuariDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.TipusUsuaris;
import java.sql.Connection;

/**
 *
 * @author arnau
 */
public class TipusUsuariDAOImpl implements TipusUsuariDAO {

    private final String OBTENIR_TIPUSUSUARI_PER_ID = "SELECT * FROM tipususuari WHERE idTipusUsuari = ?";
    private final String OBTENIR_TOTS_TIPUS = "SELECT * FROM tipususuari";
    private final String OBTENIR_NOMS_COLUMNES = "SHOW COLUMNS FROM usuaris FROM m7uf1act11";
    private final String OBTENIR_ID_PER_NOM = "SELECT idTipusUsuari FROM tipususuari WHERE tipus = ?";
    private final String OBTENIR_ID_PRIVILEGI_PER_TIPUSUSUARI = "SELECT idPrivilegis FROM tipususuari WHERE tipus = ?";
    private static final String OBTENIR_IDPRIVILEGI_PER_IDTIPUSUSUARI = "SELECT idPrivilegis FROM tipusUsuari WHERE idTipusUsuari = ?";
    private static final String OBTENIR_IDPRIVILEGI_ADMINISTRADOR = "SELECT idPrivilegis FROM tipusUsuari WHERE tipus = 'administrador'";

    private Connection con;

    public TipusUsuariDAOImpl() throws SQLException {
        this.con = ConnexioBD.obtenirConnexio();
    }

    @Override
    public TipusUsuaris obtenirTipusUsuariPerId(int id) {

        TipusUsuaris tp = new TipusUsuaris();

        try ( PreparedStatement ps = con.prepareStatement(OBTENIR_TIPUSUSUARI_PER_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tp = mapResultSetToUsuari(rs);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar l'excepció segons la teva lògica d'aplicació
        }

        return tp;
    }

    private TipusUsuaris mapResultSetToUsuari(ResultSet rs) throws SQLException {
        TipusUsuaris tipus = new TipusUsuaris();
        tipus.setIdTipusUsuaris(rs.getInt("idTipusUsuari"));
        tipus.setTipus(rs.getString("tipus"));
        tipus.setIdPrivilegis(rs.getInt("idPrivilegis"));
        // Mapejar altres atributs segons la teva estructura de taula
        return tipus;
    }

    @Override
    public int obtenirIdPerNom(String nom) {

        int id = 0;

        try {
            PreparedStatement ps = con.prepareStatement(OBTENIR_ID_PER_NOM);

            ps.setString(1, nom);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = (rs.getInt("idTipusUsuari"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    @Override
    public String[] obtenirTotsTipus() {

        List<String> llistaTipus = new ArrayList<>();
        try ( PreparedStatement ps = con.prepareStatement(OBTENIR_TOTS_TIPUS)) {
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

    @Override
    public String[] obtenirNomsColumnes() {

        List<String> llistaNoms = new ArrayList<>();

        try ( PreparedStatement ps = con.prepareStatement(OBTENIR_NOMS_COLUMNES)) {

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
    public int obtenirIdPrivilegiPerIdTipus(int id) {

        int idAux = 0;

        try {
            PreparedStatement ps = con.prepareStatement(OBTENIR_ID_PRIVILEGI_PER_TIPUSUSUARI);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idAux = (rs.getInt("idPrivilegis"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return idAux;
    }

    @Override
    public int obtenirIdPrivilegoPerIdTipusUsuari(int idTipusUsuari) {
        int idAux = 0;

        try {
            PreparedStatement ps = con.prepareStatement(OBTENIR_IDPRIVILEGI_PER_IDTIPUSUSUARI);

            ps.setInt(1, idTipusUsuari);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idAux = (rs.getInt("idPrivilegis"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return idAux;
    }

    @Override
    public int obtenirIdPrivilegiAdministrador() {
        int idAux = 0;

        try {
            PreparedStatement ps = con.prepareStatement(OBTENIR_IDPRIVILEGI_ADMINISTRADOR);


            ResultSet rs = ps.executeQuery();
            rs.next();
            idAux = rs.getInt("idPrivilegis");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return idAux;
    }

}
