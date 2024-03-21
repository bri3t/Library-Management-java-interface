package interficie;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.TipusFonsDAOImpl;
import model.TipusFons;

/**
 *
 * @author arnau
 */
public class AltaTipusFons extends JDialog {

    JLabel lbTipus;
    JTextField tfTipus;
    JButton btnAlta;

    TipusFons tipusfonsModificar;

    public AltaTipusFons(Frame owner, TipusFons tipusFons, DefaultTableModel model, int selectedRow) {
        super(owner, true);
        try {
            this.tipusfonsModificar = tipusFons;
            montar("MODIFICAR");
            tfTipus.setText(String.valueOf(tipusFons.getTipus()));
            setTitle("Modificar TipusFons");
            onClickInserirModificar(2, model, selectedRow);
            iniciarVista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public AltaTipusFons(Frame owner) throws SQLException {
        super(owner, true);
        montar("REGISTRAR");
        onClickInserirModificar(1, null, 0);
        iniciarVista();
    }

    private void _buidarCaselles() {
        tfTipus.setText("");
    }

    private void montar(String text) throws SQLException {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        int tamany = 20;

        lbTipus = new JLabel("Tipus: ");
        tfTipus = new JTextField(tamany);

        btnAlta = new JButton(text);

        btnAlta.setPreferredSize(
                new Dimension(150, 30));

        btnAlta.setPreferredSize(
                new Dimension(150, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(lbTipus, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(tfTipus, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAlta, gbc);
    }

    private void iniciarVista() {
        setSize(350, 200);
        setLocationRelativeTo(this);
        setTitle("Alta Tipus fons");
        setResizable(false);
        setVisible(true);
    }

    private void onClickInserirModificar(int num, DefaultTableModel model, int selectedRow) {
        btnAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String tipus = tfTipus.getText();

                    TipusFonsDAOImpl tfi = new TipusFonsDAOImpl();

                    TipusFons tipusFons = new TipusFons();
                    tipusFons.setTipus(tipus);

                    if (num == 1) {
                        if (tfi.afegir(tipusFons)) {
                            JOptionPane.showMessageDialog(null, "Registre correcte", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                            _buidarCaselles();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        tipusFons.setIdTipusFons(tipusfonsModificar.getIdTipusFons());

                        if (tfi.actualitzar(tipusFons)) {
                            JOptionPane.showMessageDialog(null, "Modificacio realitzada amb èxit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                            model.setValueAt(String.valueOf(tipusFons.getIdTipusFons()), selectedRow, 0);
                            model.setValueAt(tipus, selectedRow, 1);
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

//    private void onClickInserir() {
//        btnAlta.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Obtener los datos del usuario
//
//                String nombre = tfIdCodi.getText();
//                String cognoms = tfPassword.getText();
//
////              Crear un objeto de la clase Usuario y guardar los datos en la base de datos
//                Usuari usuario = new Usuari(1, nombre, cognoms);
//                RegistreUsuaris.afegirALlista(usuario);
//
//                if (nombre.isEmpty() || correoElectronico.isEmpty() || contrasena.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Completa tots camps", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                } else if (!correoElectronico.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
//                    JOptionPane.showMessageDialog(null, "Format del mail incorrecte\nExemple: a@a.com", "Error", JOptionPane.ERROR_MESSAGE);
//
//                    return;
//                } else {
//                    try {
//                        if (usuario.comprovarUsuari() && usuario.comprovarMail()) {
//                            JOptionPane.showMessageDialog(null, "El nom d'usuari i el mail ja estan en �s", "Error", JOptionPane.ERROR_MESSAGE);
//                            return;
//                        } else if (usuario.comprovarUsuari()) {
//                            JOptionPane.showMessageDialog(null, "El usuari ja est� en �s", "Error", JOptionPane.ERROR_MESSAGE);
//
//                            return;
//                        } else if (usuario.comprovarMail()) {
//                            JOptionPane.showMessageDialog(null, "El mail ja est� en �s", "Error", JOptionPane.ERROR_MESSAGE);
//
//                            return;
//                        } else {
//                            usuario.insertarEnBaseDeDatos();
//                            JOptionPane.showMessageDialog(null, "AltaUsuaris exit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
//
//                        }
//                    } catch (SQLException ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(null, "Error al inserir a la base de dades", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                    _buidarCaselles();
//
//                }
//            }
//        });
//    }
//    private void onClickModificar(String nomAntic, String mailAntic, DefaultTableModel model, int selectedRow) {
//        btnAlta.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Obtener los datos del usuario
//                String nombre = tfIdCodi.getText();
//                String correoElectronico = tfPassword.getText();
//                String contrasena = new String(tfPasswd.getPassword());
//
//                Usuari usuario = new Usuari(nombre, correoElectronico, contrasena);
//                Map<String, String> llistaNoms = RegistreUsuaris.obtenirLlistaNoms();
//
//                if (nombre.isEmpty() || correoElectronico.isEmpty() || contrasena.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Completa tots camps", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                } else if (!correoElectronico.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
//                    JOptionPane.showMessageDialog(null, "Format del mail incorrecte\nExemple: a@a.com", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                } else if (llistaNoms.containsKey(nombre) && !nombre.equalsIgnoreCase(nomAntic)) {
//                    JOptionPane.showMessageDialog(null, "El usuari ja est� en �s", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                } else if (llistaNoms.containsValue(correoElectronico) && !correoElectronico.equalsIgnoreCase(mailAntic)) {
//                    JOptionPane.showMessageDialog(null, "El mail ja est� en �s", "Error", JOptionPane.ERROR_MESSAGE);
//                } else {
//
//                    try {
//                        usuario.modificarEnBaseDeDatos(nomAntic, contrasena);
//                        JOptionPane.showMessageDialog(null, "Modificacio realitzada amb �xit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
//                        model.setValueAt(nombre, selectedRow, 0);
//                        model.setValueAt(correoElectronico, selectedRow, 1);
//                        model.setValueAt(usuario.recogerContrasena(), selectedRow, 2);
//                        dispose();
//                    } catch (SQLException ex) {
//                        ex.printStackTrace();
//                    } finally {
//                        tfIdCodi.setText("");
//                        tfPassword.setText("");
//                        tfPasswd.setText("");
//                    }
//                }
//
//            }
//        });
//    }
    private String encriptarMD5(String texto) {
        try {
            // Obtener la instancia de MessageDigest con el algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Actualizar el MessageDigest con el byte del texto
            md.update(texto.getBytes());

            // Obtener el hash en bytes
            byte[] bytes = md.digest();

            // Convertir los bytes a formato hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejo de la excepci�n en caso de que el algoritmo no est� disponible
            e.printStackTrace();
            return "0";
        }
    }
}
