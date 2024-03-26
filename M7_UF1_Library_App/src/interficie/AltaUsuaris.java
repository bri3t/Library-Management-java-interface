package interficie;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.*;
import model.Usuari;

/**
 *
 * @author arnau
 */
public class AltaUsuaris extends JDialog {

    JLabel lbNomUsuari, lbPassword, lbTipusUsuaris;
    JComboBox<String> cbTipusUsuaris;
    JTextField tfNom;
    JPasswordField tfPassword;
    JButton btnAlta;
    private Usuari usuariModificar;
    TipusUsuariDAOImpl tui;

    public AltaUsuaris(Frame owner, Usuari ususari, DefaultTableModel model, int selectedRow) {
        super(owner, true);
        try {
            this.usuariModificar = ususari;
            montar("MODIFICAR");
            tfNom.setText(ususari.getNom());
            tfPassword.setText(ususari.getPassword());
            cbTipusUsuaris.setSelectedItem(tui.obtenirTipusUsuariPerId(ususari.getTipusUsuari()).getTipus());
            onClickInserirModificar(2, model, selectedRow);
            setTitle("Modificar Llibre");
            iniciarVista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public AltaUsuaris(Frame owner) throws SQLException {
        super(owner, true);
        montar("REGISTRAR");
        setTitle("Alta Usuari");
        onClickInserirModificar(1, null, 0);
        iniciarVista();
    }

    private void _buidarCaselles() {
        tfNom.setText("");
        tfPassword.setText("");
    }

    private void montar(String text) throws SQLException {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        int tamany = 25;

        lbNomUsuari = new JLabel("Nom usuari: ");
        tfNom = new JTextField(tamany);

        lbPassword = new JLabel("Password: ");
        tfPassword = new JPasswordField(tamany);

        lbTipusUsuaris = new JLabel("Tipus usuari:");
        tui = new TipusUsuariDAOImpl();
        String[] elementos = tui.obtenirTotsTipus();
        cbTipusUsuaris = new JComboBox<>(elementos);

        btnAlta = new JButton(text);

        btnAlta.setPreferredSize(
                new Dimension(150, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbNomUsuari, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfNom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbPassword, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(lbTipusUsuaris, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(cbTipusUsuaris, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        add(btnAlta, gbc);
    }

    private void iniciarVista() {
        setSize(400, 300);
        setLocationRelativeTo(this);
        setResizable(false);
        setVisible(true);
    }

    private void onClickInserirModificar(int num, DefaultTableModel model, int selectedRow) {
        btnAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nom = tfNom.getText();
                    String contrasena = new String(tfPassword.getPassword());
                    String tipusUsuari = (String) cbTipusUsuaris.getSelectedItem();

                    TipusUsuariDAOImpl tdi = new TipusUsuariDAOImpl();
                    UsuariDAOImpl udi = new UsuariDAOImpl();

                    int idTipusUsuariPerNom = tdi.obtenirIdPerNom(tipusUsuari);

                    Usuari usuari = new Usuari();
                    usuari.setNom(nom);
                    usuari.setPassword(contrasena);
                    System.out.println(contrasena);
                    usuari.setTipusUsuari(idTipusUsuariPerNom);

                    if (num == 1) {
                        if (!udi.comprovarUsuari(nom)) {
                            if (udi.afegir(usuari)) {
                                JOptionPane.showMessageDialog(null, "Registre exit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuari ja existent", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        usuari.setIdUsuari(usuariModificar.getIdUsuari());
                        if (udi.actualitzar(usuari, usuariModificar.getNom(), contrasena)) {
                            JOptionPane.showMessageDialog(null, "Registre exit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(null, "Modificacio realitzada amb èxit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                            model.setValueAt(String.valueOf(usuari.getIdUsuari()), selectedRow, 0);
                            model.setValueAt(nom, selectedRow, 1);
                            model.setValueAt(udi.obtenirContraseñaPerId(usuari.getIdUsuari()), selectedRow, 2);
                            model.setValueAt(idTipusUsuariPerNom, selectedRow, 3);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    _buidarCaselles();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }

        });
    }

}
