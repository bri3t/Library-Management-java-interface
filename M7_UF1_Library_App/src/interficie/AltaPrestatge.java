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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.PrestatgeDAOImpl;
import model.Prestatge;

/**
 *
 * @author arnau
 */
public class AltaPrestatge extends JDialog {

    JLabel lbNom;
    JTextField tfNom;
    JButton btnAlta;
    Prestatge prestatgeModificar;

    public AltaPrestatge(Frame owner, Prestatge prestatge, DefaultTableModel model, int selectedRow) {
        super(owner, true);
        try {
            this.prestatgeModificar = prestatge;
            montar("MODIFICAR");
            tfNom.setText(String.valueOf(prestatge.getNom()));
            setTitle("Modificar Prestatge");
            onClickInserirModificar(2, model, selectedRow);
            iniciarVista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public AltaPrestatge(Frame owner) throws SQLException {
        super(owner, true);
        montar("REGISTRAR");
        setTitle("Alta Prestatge");
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

        lbNom = new JLabel("Nom: ");
        tfNom = new JTextField(tamany);

        btnAlta = new JButton(text);

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

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAlta, gbc);
    }

    private void iniciarVista() {
        setSize(350, 200);
        setLocationRelativeTo(this);
        setTitle("Alta Prestatge");
        setResizable(false);
        setVisible(true);
    }

    private void onClickInserirModificar(int num, DefaultTableModel model, int selectedRow) {
        btnAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nom = tfNom.getText();

                    PrestatgeDAOImpl pdi = new PrestatgeDAOImpl();

                    Prestatge prestatge = new Prestatge();
                    prestatge.setNom(nom);

                    if (num == 1) {
                        if (pdi.afegir(prestatge)) {
                            JOptionPane.showMessageDialog(null, "Registre correcte", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                            _buidarCaselles();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        prestatge.setIdPrestatge(prestatgeModificar.getIdPrestatge());

                        if (pdi.actualitzar(prestatge)) {
                            JOptionPane.showMessageDialog(null, "Modificacio realitzada amb èxit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                            model.setValueAt(String.valueOf(prestatge.getIdPrestatge()), selectedRow, 0);
                            model.setValueAt(nom, selectedRow, 1);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al modificar", "Error", JOptionPane.ERROR_MESSAGE);
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
