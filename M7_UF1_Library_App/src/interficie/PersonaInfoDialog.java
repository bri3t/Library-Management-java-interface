package interficie;

import javax.swing.*;
import java.awt.*;
import logica.PersonalDAOImpl;
import logica.TipusUsuariDAOImpl;
import model.Persona;

public class PersonaInfoDialog extends JDialog {

    private String nom;
    private String cognom;
    private String tipusUsuari;
    private String numeroCarnet;
    private boolean estaSancionat;
    TipusUsuariDAOImpl tudi = new TipusUsuariDAOImpl();

    public PersonaInfoDialog(Frame owner, Persona persona) {
        super(owner, "Informació de la Persona", true);
        this.nom = persona.getNom();
        this.cognom = persona.getCognom();
        this.tipusUsuari = tudi.obtenirTipusUsuariPerId(persona.getIdTipususuari()).getTipus();
        this.numeroCarnet = String.valueOf(persona.getNumeroCarnet());
        this.estaSancionat = persona.isSansionat();

        initializeComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Informació persona: " + nom, SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 15, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        
        infoPanel.add(new JLabel("Nom:"));
        infoPanel.add(new JLabel(nom));
        infoPanel.add(new JLabel("Cognom:"));
        infoPanel.add(new JLabel(cognom));
        infoPanel.add(new JLabel("Tipus usuari:"));
        infoPanel.add(new JLabel(tipusUsuari));
        infoPanel.add(new JLabel("Numero de carnet:"));
        infoPanel.add(new JLabel(numeroCarnet));
        infoPanel.add(new JLabel("Està sancionat?:"));
        infoPanel.add(new JLabel(estaSancionat ? "Sí" : "No"));

        add(infoPanel, BorderLayout.CENTER);
    }

//    public static void main(String[] args) {
//        // Ejemplo de uso
//        Persona p = new Persona();
//        p.setNom("a");
//        p.setCognom("b");
//        p.setIdTipususuari(2);
//        p.setNumeroCarnet(2345234);
//        p.setSansionat(false);
//        new PersonaInfoDialog(null, p);
//    }
}
