package auth;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import logica.LoginDAOImpl;
import model.Usuari;
import gestiobiblioteca.GestioBiblio;
import java.awt.Frame;
import logica.TipusUsuariDAOImpl;

/**
 *
 * @author arnau
 */
public class Login extends JFrame {

    final int WFRAME = 400;
    final int HFRAME = 300;

    JPanel panel;
    GridBagConstraints gbc;

    JLabel labelUsuari, labelContrasenya;
    JTextField tfUsuari;
    JPasswordField pfPassword;

    JButton btnLogin;
    

    public Login() {
        iniciarPanel();
        iniciarPantalla();
    }
    
    
    private void _buidarCaselles(){
        tfUsuari.setText("");
        pfPassword.setText("");
    }

    private void iniciarPanel() {
        panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        labelUsuari = new JLabel("Nom Usuari:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(labelUsuari, gbc);

        labelContrasenya = new JLabel("Contrasenya:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(labelContrasenya, gbc);

        tfUsuari = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(tfUsuari, gbc);

        pfPassword = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(pfPassword, gbc);

        btnLogin = new JButton("Iniciar sessio");
        btnLogin.setPreferredSize(new Dimension(150, 25));
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Usuari usuari = new Usuari();

                    LoginDAOImpl ldi = new LoginDAOImpl();
                    TipusUsuariDAOImpl tudi = new TipusUsuariDAOImpl();

                    String user = tfUsuari.getText();
                    String contrasena = new String(pfPassword.getPassword());

                    usuari = ldi.verificarLogin(user, contrasena);

                    if (usuari != null) {

                        int idPrivilegiUsuari = tudi.obtenirIdPrivilegoPerIdTipusUsuari(usuari.getTipusUsuari());
                        int idPrivilegiAdministrador = tudi.obtenirIdPrivilegiAdministrador();

                        boolean esAdmin = idPrivilegiUsuari == idPrivilegiAdministrador;

                        dispose();
                        new GestioBiblio(esAdmin, usuari, (Frame) getOwner()).setVisible(true);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Dades d'usuari incorrectes", "Error", JOptionPane.ERROR_MESSAGE);
                        _buidarCaselles();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        add(panel);
    }

    private void iniciarPantalla() {
        setTitle("Login");
        setMinimumSize(new Dimension(WFRAME, HFRAME));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }

}
