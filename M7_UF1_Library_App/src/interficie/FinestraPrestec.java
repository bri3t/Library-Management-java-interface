package interficie;

import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import logica.PersonalDAOImpl;
import logica.PrestecDAOImpl;
import model.Persona;
import model.Prestec;

/**
 *
 * @author arnau
 */
public class FinestraPrestec extends JDialog {

    public static void main(String[] args) throws SQLException {
        new FinestraPrestec();
    }

    // panels
    JPanel panelSuperior, panelCentral, panelInferior;

    // panel superior
    JLabel lbNom, lbNomEscrit,
            lbCongom, lbCognomEscrit,
            lbDataInici, lbDataIniciEscrit,
            lbDataRetorn, lbDataRetornEscrit,
            lbIds;
    JCalendar calendar = new JCalendar();

    // panel central
    // taula
    JScrollPane scrollPane;
    JTable table;
    DefaultTableModel model;
    Border bordeNegroTabla = BorderFactory.createLineBorder(Color.BLACK, 1);
    Object[][] data;
    String[] columnesTaula = {"Nom persona", "N. carnet"};
    JTextField tfBuscador;
    JLabel lbBuscador;

    //panel inferior
    JButton btnPrestec;

    // dimensions
    Dimension dimensioTaula = new Dimension(200, 100);
    GridBagConstraints gbc;

    // logica
    PrestecDAOImpl prestecdi = new PrestecDAOImpl();
    PersonalDAOImpl personaldi = new PersonalDAOImpl();

    // altres
    int idLlibreAFerPrestec;
    List<Persona> llistaPersonesActuals;

    Prestec p = new Prestec();

    public FinestraPrestec(Frame owner, int idLlibre) throws SQLException {
        super(owner, true);
        idLlibreAFerPrestec = idLlibre;
        montar();
        iniciarVista();
    }

    private FinestraPrestec() throws SQLException {
        montar();
        iniciarVista();
    }

    private void montarPanelSuperior() throws SQLException {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        Dimension d = new Dimension(205, 22);
        panelSuperior = new JPanel(new GridBagLayout());

        lbNom = new JLabel("Nom: ");
        lbNomEscrit = new JLabel("......");

        lbCongom = new JLabel("Cognom: ");
        lbCognomEscrit = new JLabel("......");

        lbDataInici = new JLabel("Data inici prestec: ");
        lbDataIniciEscrit = new JLabel(calcularData(true));

        lbDataRetorn = new JLabel("Data retorn prestec: ");
        lbDataRetornEscrit = new JLabel(calcularData(false));

        lbIds = new JLabel("Persona: ");

        btnPrestec = new JButton("Realitzar prestec");
        btnPrestec.setPreferredSize(d);
        btnPrestec.setPreferredSize(
                new Dimension(150, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelSuperior.add(lbNom, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelSuperior.add(lbNomEscrit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelSuperior.add(lbCongom, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelSuperior.add(lbCognomEscrit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelSuperior.add(lbDataInici, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelSuperior.add(lbDataIniciEscrit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelSuperior.add(lbDataRetorn, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelSuperior.add(lbDataRetornEscrit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelSuperior.add(lbIds, gbc);

        add(panelSuperior, BorderLayout.NORTH);
    }

    private String calcularData(boolean esAvui) {
        Calendar calendar = Calendar.getInstance();
        if (!esAvui) {
            calendar.add(Calendar.DATE, prestecdi.obtenirDiesPrestec());
        } else {
            calendar.add(Calendar.DATE, 0);
        }

        Date d = calendar.getTime();
        java.sql.Date dataSql = new java.sql.Date(d.getTime());
        if (!esAvui) {
            p.setDataDevolucio(dataSql);
        }else{
            p.setDataPrestec(dataSql);
        }
        
        
        return String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(d));
    }

    private void filtrarIdPersona() {
        if (tfBuscador.getText().length() > 0) {
            List<Persona> llista;
            llista = personaldi.obtenirIdPerNumCarnet((tfBuscador.getText()));
            actualitzarTaula(llista);
        } else {
            iniciarTaulaGeneral();
        }
    }

    private void actualitzarTaula(List<Persona> llistaPersones) {
        llistaPersonesActuals = llistaPersones;
        data = new Object[llistaPersones.size()][columnesTaula.length];
        for (int i = 0; i < llistaPersones.size(); i++) {
            Persona persona = llistaPersones.get(i);
            data[i][0] = persona.getNom();
            data[i][1] = persona.getNumeroCarnet();
        }

        model.setDataVector(data, columnesTaula);
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void iniciarTaula() {
        model = new DefaultTableModel(data, columnesTaula);

        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que todas las celdas no sean editables
            }
        };
        table.getTableHeader().setResizingAllowed(false);


        iniciarTaulaGeneral();
        // Agregar un ListSelectionListener para detectar la selección de filas
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        _emplenarDadesUsuari(table.getValueAt(selectedRow, 1).toString());
//                        tfBuscador.setText(table.getValueAt(selectedRow, 1).toString());
                    }
                }
            }
        });

        iniciarTaulaGeneral();

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(bordeNegroTabla);
        scrollPane.setMinimumSize(dimensioTaula);
        scrollPane.setMaximumSize(dimensioTaula);

    }

    private void iniciarTaulaGeneral() {
        java.util.List<Persona> persones = personaldi.obtenirTotsPersonal();
        actualitzarTaula(persones);
    }

    private void montarPanelCentral() throws SQLException {
        panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panelCentral.setBackground(new Color(39, 207, 240));

        lbBuscador = new JLabel("Cercar numero carnet:");

        tfBuscador = new JTextField();
        tfBuscador.setMinimumSize(new Dimension(150, 22));
        tfBuscador.setMaximumSize(new Dimension(150, 22));
        tfBuscador.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                table.clearSelection();
            }
        });

        tfBuscador.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarIdPersona();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarIdPersona();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarIdPersona();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH; // Alineamos en la parte superior
        panelCentral.add(lbBuscador, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTH; // Alineamos en la parte superior
        panelCentral.add(tfBuscador, gbc);
        iniciarTaula();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH; // Alineamos en la parte superior
        panelCentral.add(scrollPane, gbc);
        add(panelCentral, BorderLayout.CENTER);

    }

    private void montarPanelInferior() {
        panelInferior = new JPanel();
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelInferior.setBackground(new Color(39, 20, 240));

        btnPrestec = new JButton("REALITZAR PRESTEC");
        panelInferior.add(btnPrestec);

        btnPrestec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    p.setIdLlibre(idLlibreAFerPrestec);
                    p.setIdPersona(personaldi.obtenirIdPerNumCarnet(table.getValueAt(selectedRow, 1).toString()).get(0).getIdPersonal());
                    if (prestecdi.realitzarPrestec(p)) {
                        JOptionPane.showMessageDialog(null, "Prestec realitzat correctament", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al realitzar prestec", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un id d'una persona", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panelInferior, BorderLayout.SOUTH);

    }

    private void _emplenarDadesUsuari(String numCarnet) {
        lbNomEscrit.setText(personaldi.obtenirPersonaPerNumCarnet(numCarnet).getNom());
        lbCognomEscrit.setText(personaldi.obtenirPersonaPerNumCarnet(numCarnet).getCognom());
    }

    private void montar() throws SQLException {
        setLayout(new BorderLayout());
        montarPanelSuperior();
        montarPanelCentral();
        montarPanelInferior();
    }

    private void iniciarVista() {
        setSize(500, 450);
        setLocationRelativeTo(this);
        setTitle("Prestec");
        setResizable(false);
        setVisible(true);
    }

}
