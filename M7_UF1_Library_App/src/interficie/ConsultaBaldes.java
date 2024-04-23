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
import logica.BaldaDAOImpl;
import model.Balda;

/**
 *
 * @author arnau
 */
public class ConsultaBaldes extends JDialog {

    private JPanel panelBotones;
    private JButton btnModificar, btnBaixa;
    private DefaultTableModel model;

    public ConsultaBaldes(Frame owner) {
        super(owner, "Consulta Baldes", true);
        montar(owner);
    }

    private void montar(Frame owner) {
        try {
            setLayout(new BorderLayout());

            BaldaDAOImpl pdi = new BaldaDAOImpl();

            java.util.List<Balda> baldes = pdi.obtenirTotesLesBaldes();
            String[] columnNames = pdi.obtenirNomsColumnes();

            Object[][] data = new Object[baldes.size()][columnNames.length];

            for (int i = 0; i < baldes.size(); i++) {
                Balda balda = baldes.get(i);
                data[i][0] = balda.getIdBalda();
                data[i][1] = balda.getNom();
                data[i][2] = balda.getIdPrestatge();
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
                        Balda balda = new Balda();
                        balda.setIdBalda(Integer.parseInt(table.getValueAt(selectedRow, 0).toString()));
                        balda.setNom(table.getValueAt(selectedRow, 1).toString());
                        balda.setIdPrestatge(Integer.parseInt(table.getValueAt(selectedRow, 2).toString()));
                        new AltaBalda(owner, balda, model, selectedRow);
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
                            int idBalda = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                            BaldaDAOImpl bdi = new BaldaDAOImpl();
                            if (bdi.esborrar(idBalda)) {
                                JOptionPane.showMessageDialog(null, "Eliminat correctament", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                                ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                        }
                    }

                }

            }
            );
            panelBotones.add(btnBaixa, BorderLayout.EAST);

            add(panelBotones, BorderLayout.SOUTH);

            setSize(500, 250);
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

}
