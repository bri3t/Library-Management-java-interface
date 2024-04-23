package gestiobiblioteca;

import auth.Login;
import interficie.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import model.Usuari;

/**
 *
 * @author jaleo
 */
public class GestioBiblio extends JDialog {

    private JMenuItem altaLlibreMenuItem, consultaLlibreMenuItem;
    private JMenuItem altaUsuariMenuItem, consultaUsuariMenuItem;
    private JMenuItem altaPrestatgeMenuItem, consultaPrestatgeMenuItem;
    private JMenuItem altaBaldaMenuItem, consultaBaldaMenuItem;
    private JMenuItem altaTipusFonsMenuItem, consultaFonsMenuItem;
    private JMenuItem logout;
    private JMenuItem prestecMenuItem;

    private final Usuari USUARIACTIU;

    public GestioBiblio(boolean esAdmin, Usuari usuari, Frame owner) {
        super(owner);
        USUARIACTIU = usuari;
        setTitle("Gestió Biblioteca");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu llibresMenu = new JMenu("Llibres");
        altaLlibreMenuItem = new JMenuItem("Alta");
        consultaLlibreMenuItem = new JMenuItem("Consulta");
        llibresMenu.add(altaLlibreMenuItem);
        llibresMenu.add(consultaLlibreMenuItem);
        menuBar.add(llibresMenu);

        if (esAdmin) {
            JMenu usuarisMenu = new JMenu("Usuaris");
            altaUsuariMenuItem = new JMenuItem("Alta");
            consultaUsuariMenuItem = new JMenuItem("Consulta");
            usuarisMenu.add(altaUsuariMenuItem);
            usuarisMenu.add(consultaUsuariMenuItem);
            menuBar.add(usuarisMenu);
        }

        JMenu prestatgesmenu = new JMenu("Prestatges");
        altaPrestatgeMenuItem = new JMenuItem("Alta");
        consultaPrestatgeMenuItem = new JMenuItem("Consulta");
        prestatgesmenu.add(altaPrestatgeMenuItem);
        prestatgesmenu.add(consultaPrestatgeMenuItem);
        menuBar.add(prestatgesmenu);

        JMenu baldesMenu = new JMenu("Baldes");
        altaBaldaMenuItem = new JMenuItem("Alta");
        consultaBaldaMenuItem = new JMenuItem("Consulta");
        baldesMenu.add(altaBaldaMenuItem);
        baldesMenu.add(consultaBaldaMenuItem);
        menuBar.add(baldesMenu);

        JMenu tipusFons = new JMenu("Tipus fons");
        altaTipusFonsMenuItem = new JMenuItem("Alta");
        consultaFonsMenuItem = new JMenuItem("Consulta");
        tipusFons.add(altaTipusFonsMenuItem);
        tipusFons.add(consultaFonsMenuItem);
        menuBar.add(tipusFons);

        JMenu prestecsMenu = new JMenu("Prestecs");
        prestecMenuItem = new JMenuItem("Préstec");
        prestecsMenu.add(prestecMenuItem);
        menuBar.add(prestecsMenu);

        JMenu logoutMenu = new JMenu("Log out");
        logout = new JMenuItem("Log out");
        logoutMenu.add(logout);
        menuBar.add(logoutMenu);

        // item Llibre
        altaLlibreMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AltaLlibre((Frame) getOwner());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        consultaLlibreMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaLlibres((Frame) getOwner());
            }
        });

        if (esAdmin) {
            //item usuari
            altaUsuariMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new AltaUsuaris((Frame) getOwner());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            consultaUsuariMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ConsultaUsuaris((Frame) getOwner());
                }
            });

        }

        // item Prestatge
        altaPrestatgeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AltaPrestatge((Frame) getOwner());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        consultaPrestatgeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaPrestatges((Frame) getOwner());
            }
        });

        // item Baldes
        altaBaldaMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AltaBalda((Frame) getOwner());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        consultaBaldaMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaBaldes((Frame) getOwner());
            }
        });

        // item Tipus 
        altaTipusFonsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AltaTipusFons((Frame) getOwner());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        consultaFonsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaTipusFons((Frame) getOwner());
            }
        });

        // item Prestec
        prestecMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Prestecs((Frame) getOwner());
            }
        });

        // item logout
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });

    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new GestioBiblio(true, new Usuari(), new Frame()).setVisible(true);
//            }
//        });
//    }
}
