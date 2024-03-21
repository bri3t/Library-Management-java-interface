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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.PrestatgeDAOImpl;
import logica.BaldaDAOImpl;
import model.Balda;

/**
 *
 * @author arnau
 */
public class AltaBalda extends JDialog {

    JLabel lbNom, lbPrestatge;
    JTextField tfNom;
    JComboBox<String> cbPrestatges;

    JButton btnAlta;

    Balda baldaModificar;
    PrestatgeDAOImpl pdi;

    public AltaBalda(Frame owner, Balda balda, DefaultTableModel model, int selectedRow) {
        super(owner, true);
        try {
            this.baldaModificar = balda;
            montar("MODIFICAR");
            tfNom.setText(String.valueOf(balda.getNom()));
            cbPrestatges.setSelectedItem(pdi.obtenirPrestatgePerId(balda.getIdPrestatge()).getNom());
            onClickInserirModificar(2, model, selectedRow);
            setTitle("Modificar Llibre");
            iniciarVista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public AltaBalda(Frame owner) throws SQLException {
        super(owner, true);
        montar("REGISTRAR");
        onClickInserirModificar(1, null, 0);
        iniciarVista();
    }

    private void _buidarCaselles() {
        tfNom.setText("");
    }

    private void montar(String text) throws SQLException {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        int tamany = 20;
        Dimension d = new Dimension(205, 22);

        lbNom = new JLabel("Nom: ");
        tfNom = new JTextField(tamany);

        lbPrestatge = new JLabel("Prestatge: ");

        pdi = new PrestatgeDAOImpl();
        String[] elementos = pdi.obtenirTotsTipus();
        cbPrestatges = new JComboBox<>(elementos);
        cbPrestatges.setPreferredSize(d);
        btnAlta = new JButton(text);
        btnAlta.setPreferredSize(d);
        btnAlta.setPreferredSize(
                new Dimension(150, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbNom, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfNom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbPrestatge, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(cbPrestatges, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAlta, gbc);
    }

    private void iniciarVista() {
        setSize(350, 250);
        setLocationRelativeTo(this);
        setTitle("Alta Balda");
        setResizable(false);
        setVisible(true);
    }

    private void onClickInserirModificar(int num, DefaultTableModel model, int selectedRow) {

        btnAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nom = tfNom.getText();
                    String prestatge = (String) cbPrestatges.getSelectedItem();

                    BaldaDAOImpl bdi = new BaldaDAOImpl();
                    PrestatgeDAOImpl pdi = new PrestatgeDAOImpl();

                    Balda balda = new Balda();
                    balda.setNom(nom);
                    balda.setIdPrestatge(pdi.obtenirIdPerNom(prestatge));

                    int idPrestatgeperNom = pdi.obtenirIdPerNom(prestatge);

                    if (!tfNom.getText().isEmpty()) {
                        if (num == 1) {
                            if (bdi.afegir(balda)) {
                                JOptionPane.showMessageDialog(null, "Registre correcte", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            balda.setIdBalda(baldaModificar.getIdBalda());

                            if (bdi.actualitzar(balda)) {
                                JOptionPane.showMessageDialog(null, "Modificacio realitzada amb èxit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                                model.setValueAt(String.valueOf(balda.getIdBalda()), selectedRow, 0);
                                model.setValueAt(nom, selectedRow, 1);
                                model.setValueAt(idPrestatgeperNom, selectedRow, 2);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al modificar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            _buidarCaselles();
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }

        }
        );
    }
}
