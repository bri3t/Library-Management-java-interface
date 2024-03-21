package logica;

import basedades.ConnexioBD;
import dao.TipusFonsDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.TipusFons;

/**
 *
 * @author arnau
 */
public class TipusFonsDAOImpl implements TipusFonsDAO {

    private static final String OBTENIR_TOTS_ELS_TIPUSFONS = "SELECT * FROM tipusfons";
    private static final String OBTENIR_TIPUSFONS_PER_ID = "SELECT * FROM tipusfons WHERE idTipusFons = ?";
    private static final String AFEGIR_TIPUSFONS = "INSERT INTO tipusfons (tipus) VALUES (?)";
    private static final String ACTUALITZAR_TIPUSFONS = "UPDATE tipusfons SET tipus = ? WHERE idTipusFons = ?";
    private static final String ESBORRAR_TIPUSFONS = "DELETE FROM tipusfons WHERE idTipusFons = ?";
    private final String OBTENIR_NOMS_COLUMNES = "SHOW COLUMNS FROM tipusfons FROM m7uf1act11";
    private final String OBTENIR_ID_PER_NOM = "SELECT idTipusFons FROM tipusfons WHERE tipus = ?";

    private Connection con;

    public TipusFonsDAOImpl() throws SQLException {
        this.con = ConnexioBD.obtenirConnexio();
    }

    @Override
    public String[] obtenirNomsColumnes() {
        List<String> llistaTipus = new ArrayList<>();
        try ( PreparedStatement ps = con.prepareStatement(OBTENIR_NOMS_COLUMNES)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                llistaTipus.add(rs.getString("Field"));

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
    public TipusFons obtenirTipusFonssPerId(int id) {
        TipusFons tipusFons = new TipusFons();

        try {
            PreparedStatement ps = con.prepareStatement(OBTENIR_TIPUSFONS_PER_ID);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tipusFons = mapResultSetToTipusFons(rs);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tipusFons;
    }

    @Override
    public String[] obtenirTotsTipusFons() {
        List<String> llistaTipus = new ArrayList<>();
        try ( PreparedStatement ps = con.prepareStatement(OBTENIR_TOTS_ELS_TIPUSFONS)) {
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
    public List<TipusFons> obtenirTotesLesFons() {

        List<TipusFons> baldes = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(OBTENIR_TOTS_ELS_TIPUSFONS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                baldes.add(mapResultSetToTipusFons(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return baldes;
    }

    @Override
    public boolean afegir(TipusFons tipusFons) {
        try {
            PreparedStatement ps = con.prepareStatement(AFEGIR_TIPUSFONS);
            ps.setString(1, tipusFons.getTipus());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualitzar(TipusFons tipusFons) {
        try {
            PreparedStatement ps = con.prepareStatement(ACTUALITZAR_TIPUSFONS);
            ps.setString(1, tipusFons.getTipus());
            ps.setInt(2, tipusFons.getIdTipusFons());
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
            PreparedStatement ps = con.prepareStatement(ESBORRAR_TIPUSFONS);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private TipusFons mapResultSetToTipusFons(ResultSet rs) {
        TipusFons tipusFons = new TipusFons();

        try {
            tipusFons.setIdTipusFons(rs.getInt("idTipusFons"));
            tipusFons.setTipus(rs.getString("tipus"));
            // Mapejar altres atributs segons la teva estructura de taula
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

//        System.out.println(prestatge.getNom());
        return tipusFons;
    }

    @Override
    public int obtenirIdPerNom(String nom) {

        int id = 0;

        try {
            PreparedStatement ps = con.prepareStatement(OBTENIR_ID_PER_NOM);

            ps.setString(1, nom);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = (rs.getInt("idTipusFons"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }
}
