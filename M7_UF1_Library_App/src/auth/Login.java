package auth;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

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

    JButton btn;

    public Login() {
        iniciarPanel();
        iniciarPantalla();
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

        btn = new JButton("Iniciar sessio");
        btn.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btn, gbc);

        add(panel);
    }

    private void iniciarPantalla() {
        setTitle("Login");
        setMinimumSize(new Dimension(WFRAME, HFRAME));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.ORANGE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }

}
