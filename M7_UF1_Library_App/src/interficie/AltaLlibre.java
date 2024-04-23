package interficie;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.BaldaDAOImpl;
import logica.PrestatgeDAOImpl;
import logica.TipusFonsDAOImpl;
import logica.LlibreDAOImpl;
import model.Llibre;

/**
 *
 * @author arnau
 */
public class AltaLlibre extends JDialog {

    JLabel lbidCodi, lbIdTipusFons, lbTitol, lbAutor, lbIsbn, lbQuantitatDisponible, lbIdPrestatge, lbidBalda;
    JComboBox<String> cbTipusFons, cbPrestatges, cbBalda;
    JTextField tfIdCodi, tfTitol, tfAutor, tfIsbn, tfQuantitatDisponible;
    JButton btnAlta;
    Llibre llibreModificar;
    TipusFonsDAOImpl tfi;
    PrestatgeDAOImpl pi;
    BaldaDAOImpl bi;

    public AltaLlibre(Frame owner, Llibre llibre, DefaultTableModel model, int selectedRow) {
        super(owner, true);
        try {
            this.llibreModificar = llibre;
            montar("MODIFICAR");
            tfIdCodi.setText(String.valueOf(llibre.getIdCodi()));
            cbTipusFons.setSelectedItem(tfi.obtenirTipusFonssPerId(llibre.getIdTipusFons()).getTipus());
            tfTitol.setText(llibre.getTitol());
            tfAutor.setText(llibre.getAutor());
            tfIsbn.setText(llibre.getIsbn());
            tfQuantitatDisponible.setText(String.valueOf(llibre.getQuantitatDisponible()));
            cbPrestatges.setSelectedItem(pi.obtenirPrestatgePerId(llibre.getIdPrestatge()).getNom());
            cbBalda.setSelectedItem(bi.obtenirBaldaPerId(llibre.getIdBalda()).getNom());
            onClickInserirModificar(2, model, selectedRow);
            setTitle("Modificar Llibre");
            iniciarVista();
        } catch (SQLException ex) {
            Logger.getLogger(AltaLlibre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AltaLlibre(Frame owner) throws SQLException {
        super(owner, true);
        montar("REGISTRAR");
        setTitle("Alta Llibre");
        onClickInserirModificar(1, null, 0);
        setLocationRelativeTo(null);
        iniciarVista();
    }

    private void _buidarCaselles() {
        tfIdCodi.setText("");
        tfTitol.setText("");
        tfAutor.setText("");
        tfAutor.setText("");
        tfIsbn.setText("");
        tfQuantitatDisponible.setText("");
    }

    private void montar(String text) throws SQLException {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        int tamany = 20;
        Dimension d = new Dimension(205, 22);

        lbidCodi = new JLabel("id Codi: ");
        tfIdCodi = new JTextField(tamany);

        lbIdTipusFons = new JLabel("id Tipus fons: ");
        tfi = new TipusFonsDAOImpl();
        String[] elementosTipusFons = tfi.obtenirTotsTipusFons();
        cbTipusFons = new JComboBox<>(elementosTipusFons);
        cbTipusFons.setPreferredSize(d);

        lbTitol = new JLabel("Titol: ");
        tfTitol = new JTextField(tamany);

        lbAutor = new JLabel("Autor: ");
        tfAutor = new JTextField(tamany);

        lbIsbn = new JLabel("ISBN: ");
        tfIsbn = new JTextField(tamany);

        lbQuantitatDisponible = new JLabel("Quantitat disponible: ");
        tfQuantitatDisponible = new JTextField(tamany);

        lbIdPrestatge = new JLabel("id Prestatge: ");
        pi = new PrestatgeDAOImpl();
        String[] elementosPrestatge = pi.obtenirTotsTipus();
        cbPrestatges = new JComboBox<>(elementosPrestatge);
        cbPrestatges.setPreferredSize(d);

        lbidBalda = new JLabel("id Balda: ");
        bi = new BaldaDAOImpl();
        String[] elementosBalda = bi.obtenirTotsTipus();
        cbBalda = new JComboBox<>(elementosBalda);
        cbBalda.setPreferredSize(d);

        btnAlta = new JButton(text);

        btnAlta.setPreferredSize(
                new Dimension(150, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbidCodi, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfIdCodi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbIdTipusFons, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(cbTipusFons, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbTitol, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfTitol, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbAutor, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfAutor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbIsbn, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfIsbn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbQuantitatDisponible, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfQuantitatDisponible, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbQuantitatDisponible, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfQuantitatDisponible, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbIdPrestatge, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(cbPrestatges, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbidBalda, gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(cbBalda, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAlta, gbc);
    }

    private void iniciarVista() {
        setSize(500, 500);
        setLocationRelativeTo(this);
        setResizable(false);
        setVisible(true);
    }

    private boolean verificarNumeros() {
        try {
            Integer.parseInt(tfIdCodi.getText());
            Integer.parseInt(tfQuantitatDisponible.getText());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void onClickInserirModificar(int num, DefaultTableModel model, int selectedRow) {
        btnAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idCodi = tfIdCodi.getText();
                    String tipusFons = (String) cbTipusFons.getSelectedItem();
                    String titol = tfTitol.getText();
                    String autor = tfAutor.getText();
                    String isbn = tfIsbn.getText();
                    String quantitat = tfQuantitatDisponible.getText();
                    String prestatge = (String) cbPrestatges.getSelectedItem();
                    String balda = (String) cbBalda.getSelectedItem();

                    LlibreDAOImpl ldi = new LlibreDAOImpl();
                    TipusFonsDAOImpl tfi = new TipusFonsDAOImpl();
                    PrestatgeDAOImpl pdi = new PrestatgeDAOImpl();
                    BaldaDAOImpl bdi = new BaldaDAOImpl();

                    int idTipusFonsperNom = tfi.obtenirIdPerNom(tipusFons);
                    int idPrestatgeperNom = pdi.obtenirIdPerNom(prestatge);
                    int idBaldaperNom = bdi.obtenirIdPerNom(balda);
                    if (!tfIdCodi.getText().isEmpty()
                            && !tfTitol.getText().isEmpty()
                            && !tfAutor.getText().isEmpty()
                            && !tfIsbn.getText().isEmpty()
                            && !tfQuantitatDisponible.getText().isEmpty()) {
                        if (verificarNumeros()) {

                            Llibre llibre = new Llibre();
                            llibre.setIdCodi(Integer.parseInt(idCodi));
                            llibre.setIdTipusFons(idTipusFonsperNom);
                            llibre.setTitol(titol);
                            llibre.setAutor(autor);
                            llibre.setIsbn(isbn);
                            llibre.setQuantitatDisponible(Integer.parseInt(quantitat));
                            llibre.setIdPrestatge(idPrestatgeperNom);
                            llibre.setIdBalda(idBaldaperNom);
                            if (num == 1) {
                                if (!ldi.comprovarLlibre(llibre.getIdCodi())) {

                                    if (ldi.afegir(llibre)) {
                                        JOptionPane.showMessageDialog(null, "Registre correcte", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error al registrar. Aquest llibre ja existeix", "Error", JOptionPane.ERROR_MESSAGE);
                                }

                            } else {
                                llibre.setIdLlibre(llibreModificar.getIdLlibre());

                                if (ldi.actualitzar(llibre)) {
                                    JOptionPane.showMessageDialog(null, "Modificacio realitzada amb èxit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                                    model.setValueAt(String.valueOf(llibre.getIdLlibre()), selectedRow, 0);
                                    model.setValueAt(idCodi, selectedRow, 1);
                                    model.setValueAt(idTipusFonsperNom, selectedRow, 2);
                                    model.setValueAt(titol, selectedRow, 3);
                                    model.setValueAt(autor, selectedRow, 4);
                                    model.setValueAt(isbn, selectedRow, 5);
                                    model.setValueAt(quantitat, selectedRow, 6);
                                    model.setValueAt(idPrestatgeperNom, selectedRow, 7);
                                    model.setValueAt(idBaldaperNom, selectedRow, 8);
                                    dispose();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error al modificar", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            _buidarCaselles();
                        } else {
                            JOptionPane.showMessageDialog(null, "Format Id Codi o Quantitat inorrecte", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Omple tots els camps", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }

        });
    }

}
