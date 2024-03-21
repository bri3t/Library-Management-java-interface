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
import logica.*;
import model.Llibre;

/**
 *
 * @author arnau
 */
public class ConsultaLlibres extends JDialog {

    private JPanel panelBotones;
    private JButton btnModificar, btnBaixa;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JTable table;

    public ConsultaLlibres(Frame owner) {
        super(owner, "Consulta Llibres", true);
        montar(owner);
    }

    private void montar(Frame owner) {
        try {
            setLayout(new BorderLayout());

            LlibreDAOImpl udi = new LlibreDAOImpl();

            java.util.List<Llibre> llibres = udi.obtenirTots();
            String[] columnNames = udi.obtenirNomsColumnes();
            Object[][] data = new Object[llibres.size()][columnNames.length];

            for (int i = 0; i < llibres.size(); i++) {
                Llibre llibre = llibres.get(i);
                data[i][0] = llibre.getIdLlibre();
                data[i][1] = llibre.getIdCodi();
                data[i][2] = llibre.getIdTipusFons();
                data[i][3] = llibre.getTitol();
                data[i][4] = llibre.getAutor();
                data[i][5] = llibre.getIsbn();
                data[i][6] = llibre.getQuantitatDisponible();
                data[i][7] = llibre.getIdPrestatge();
                data[i][8] = llibre.getIdBalda();

            }
            model = new DefaultTableModel(data, columnNames);

            table = new JTable(model) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hacer que todas las celdas no sean editables
                }
            };
            scrollPane = new JScrollPane(table);

            table.getTableHeader()
                    .setReorderingAllowed(false);
            add(scrollPane, BorderLayout.CENTER);

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
                        Llibre llibre = new Llibre();
                        llibre.setIdLlibre(Integer.parseInt(table.getValueAt(selectedRow, 0).toString()));
                        llibre.setIdCodi(Integer.parseInt(table.getValueAt(selectedRow, 1).toString()));
                        llibre.setIdTipusFons(Integer.parseInt(table.getValueAt(selectedRow, 2).toString()));
                        llibre.setTitol((table.getValueAt(selectedRow, 3).toString()));
                        llibre.setAutor((table.getValueAt(selectedRow, 4).toString()));
                        llibre.setIsbn((table.getValueAt(selectedRow, 5).toString()));
                        llibre.setQuantitatDisponible(Integer.parseInt(table.getValueAt(selectedRow, 6).toString()));
                        llibre.setIdPrestatge(Integer.parseInt(table.getValueAt(selectedRow, 7).toString()));
                        llibre.setIdBalda(Integer.parseInt(table.getValueAt(selectedRow, 8).toString()));
                        new AltaLlibre(owner, llibre, model, selectedRow);
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
                            int idLlibre = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                            LlibreDAOImpl ldi = new LlibreDAOImpl();
                            if (ldi.esborrar(idLlibre)) {
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

            setSize(
                    700, 250);
            setLocationRelativeTo(
                    this);
            setResizable(
                    false);
            setVisible(
                    true);

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

}
