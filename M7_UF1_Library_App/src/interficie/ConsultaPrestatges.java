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
import logica.PrestatgeDAOImpl;
import model.Prestatge;

/**
 *
 * @author arnau
 */
public class ConsultaPrestatges extends JDialog {

    private JPanel panelBotones;
    private JButton btnModificar, btnBaixa;
    private DefaultTableModel model;

    public ConsultaPrestatges(Frame owner) {
        super(owner, "Consulta Prestatges", true);
        montar(owner);
    }

    private void montar(Frame owner) {
        try {
            setLayout(new BorderLayout());

            PrestatgeDAOImpl pdi = new PrestatgeDAOImpl();

            java.util.List<Prestatge> prestatges = pdi.obtenirTotsElsPrestatges();
            String[] columnNames = pdi.obtenirNomsColumnes();

            Object[][] data = new Object[prestatges.size()][columnNames.length];

            for (int i = 0; i < prestatges.size(); i++) {
                Prestatge prestatge = prestatges.get(i);
                data[i][0] = prestatge.getIdPrestatge();
                data[i][1] = prestatge.getNom();
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
                        Prestatge prestatge = new Prestatge();
                        prestatge.setIdPrestatge(Integer.parseInt(table.getValueAt(selectedRow, 0).toString()));
                        prestatge.setNom(table.getValueAt(selectedRow, 1).toString());
                        new AltaPrestatge(owner, prestatge, model, selectedRow);
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

                            int idPrestatge = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                            PrestatgeDAOImpl pdi = new PrestatgeDAOImpl();
                            if (pdi.esborrar(idPrestatge)) {
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
