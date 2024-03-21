package gestiobiblioteca;

import interficie.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 *
 * @author jaleo
 */
public class GestioBiblio extends JFrame {

    private JMenuItem altaLlibreMenuItem, consultaLlibreMenuItem;
    private JMenuItem altaUsuariMenuItem, consultaUsuariMenuItem;
    private JMenuItem altaPrestatgeMenuItem, consultaPrestatgeMenuItem;
    private JMenuItem altaBaldaMenuItem, consultaBaldaMenuItem;
    private JMenuItem altaTipusFonsMenuItem, consultaFonsMenuItem;
    private JMenuItem prestecMenuItem;

    public GestioBiblio() {
        setTitle("Gestió Biblioteca");
        setSize(405, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu llibresMenu = new JMenu("Llibres");
        altaLlibreMenuItem = new JMenuItem("Alta");
        consultaLlibreMenuItem = new JMenuItem("Consulta");
        llibresMenu.add(altaLlibreMenuItem);
        llibresMenu.add(consultaLlibreMenuItem);
        menuBar.add(llibresMenu);

        JMenu usuarisMenu = new JMenu("Usuaris");
        altaUsuariMenuItem = new JMenuItem("Alta");
        consultaUsuariMenuItem = new JMenuItem("Consulta");
        usuarisMenu.add(altaUsuariMenuItem);
        usuarisMenu.add(consultaUsuariMenuItem);
        menuBar.add(usuarisMenu);

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

        JMenu configuracioMenu = new JMenu("Configuracio");
        prestecMenuItem = new JMenuItem("Préstec");
        configuracioMenu.add(prestecMenuItem);
        menuBar.add(configuracioMenu);

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

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestioBiblio().setVisible(true);
            }
        });
    }
}
