package interficie;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.TipusUsuariDAOImpl;
import logica.UsuariDAOImpl;
import model.Usuari;

/**
 *
 * @author arnau
 */
public class ConsultaUsuaris extends JDialog {

    private JPanel panelBotones;
    private JButton btnModificar, btnBaixa;
    private DefaultTableModel model;

    public ConsultaUsuaris(Frame owner) {
        super(owner, "Consulta Usuaris", true);
        montar(owner);
    }

    private void montar(Frame owner) {
        try {
            setLayout(new BorderLayout());

            UsuariDAOImpl udi = new UsuariDAOImpl();
            TipusUsuariDAOImpl tui = new TipusUsuariDAOImpl();

            java.util.List<Usuari> usuaris = udi.obtenirTots();
            String[] columnNames = tui.obtenirNomsColumnes();

            Object[][] data = new Object[usuaris.size()][columnNames.length];

            for (int i = 0; i < usuaris.size(); i++) {
                Usuari usuari = usuaris.get(i);
                data[i][0] = usuari.getIdUsuari();
                data[i][1] = usuari.getNom();
                data[i][2] = usuari.getPassword();
                data[i][3] = usuari.getTipusUsuari();
            }

            model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hacer que todas las celdas no sean editables
                }
            };
            JScrollPane scrollPane = new JScrollPane(table);

            add(scrollPane, BorderLayout.CENTER);
            table.getTableHeader().setReorderingAllowed(false);

            //panel i botones
            panelBotones = new JPanel(new BorderLayout());
            Dimension d = new Dimension(150, 30);

            btnModificar = new JButton("Modificar");
            btnModificar.setPreferredSize(d);

            btnModificar.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        Usuari usuari = new Usuari();
                        usuari.setIdUsuari(Integer.parseInt(table.getValueAt(selectedRow, 0).toString()));
                        usuari.setNom(table.getValueAt(selectedRow, 1).toString());
                        usuari.setPassword(table.getValueAt(selectedRow, 2).toString());
                        usuari.setTipusUsuari(Integer.parseInt(table.getValueAt(selectedRow, 3).toString()));
                        new AltaUsuaris(owner, usuari, model, selectedRow);
                    }
                }

            }
            );
            panelBotones.add(btnModificar, BorderLayout.WEST);

            btnBaixa = new JButton("Baixa");
            btnBaixa.setPreferredSize(d);
            btnBaixa.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            int idUsiari = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                            UsuariDAOImpl udi = new UsuariDAOImpl();
                            if (udi.esborrar(idUsiari)) {
                                JOptionPane.showMessageDialog(null, "Eliminat correctament", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                                model.removeRow(selectedRow);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }

            }
            );
            panelBotones.add(btnBaixa, BorderLayout.EAST);

            add(panelBotones, BorderLayout.SOUTH);

            setSize(500, 250);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

}
