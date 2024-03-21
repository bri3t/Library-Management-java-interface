package interficie;

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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import logica.LlibreDAOImpl;
import logica.PrestecDAOImpl;
import model.Llibre;

/**
 *
 * @author arnau
 */
public class Prestecs extends JDialog {

    // botons
    JButton btnNouPrestec, btnRetorn;
    List<JButton> llistaBotons = new ArrayList<>();

    // altres items
    JTextField tfFiltre;
    JComboBox cbFiltre;

    // llistes de noms
    String[] llistaNomsBotons = {"Nou Prestec", "Retorn"};
    String[] nomsFiltres = {"Tots", "Prestat", "No prestats"};
    String[] columnesTaula = {"Títol", "Autor", "ISBN"};

    // taula
    JScrollPane scrollPane;
    JTable table;
    DefaultTableModel model;
    Border bordeNegroTabla = BorderFactory.createLineBorder(Color.BLACK, 1);
    Object[][] data;

    // dimensions i estilatge
    Dimension dimensionItems = new Dimension(105, 23),
            dimensioTF = new Dimension(200, 23),
            dimensioTaula = new Dimension(700, 300);
    GridBagConstraints gbc;

    // panells
    JPanel panelSuperior, panelInferior;
    Border bordeNegroPanel = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK);
    Border bordeMargenes = BorderFactory.createEmptyBorder(20, 0, 20, 0);
    Border bordeFinal = new CompoundBorder(bordeNegroPanel, bordeMargenes);

    // owner
    Frame owner;

    //altres
    List<Llibre> llibresTaulaActuals;

    public Prestecs(Frame owner) {
        super(owner, true);
        this.owner = owner;
        montar();
        filtreCombo();
        iniciarVista();
    }

    private void filtreCombo() {
        cbFiltre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener el valor seleccionado
                    String selectedValue = (String) cbFiltre.getSelectedItem();
                    switch (selectedValue) {
                        case "Tots":
                            filtrar(4);
                            break;
                        case "No prestats":
                            filtrar(3);
                            break;
                        case "Prestat":
                            filtrar(2);
                            break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Prestecs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void _iniciarElements() {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 10);
        panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(bordeFinal);
        panelSuperior.setBackground(new Color(23, 205, 166)); // Cambia los valores RGB según tu preferencia

        int i = 0;
        for (String nombreBoton : llistaNomsBotons) {
            JButton boto = new JButton(nombreBoton); // Create a new button here
            boto.setPreferredSize(dimensionItems);
            gbc.gridx = i;
            gbc.gridy = 0;
            panelSuperior.add(boto, gbc);
            llistaBotons.add(boto); // Add the button to the list
            i++;
        }

        // Assign buttons to fields
        btnNouPrestec = llistaBotons.get(0);
        btnRetorn = llistaBotons.get(1);

        btnNouPrestec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        if (cbFiltre.getSelectedIndex() != 1) {
                            new FinestraPrestec(owner, llibresTaulaActuals.get(selectedRow).getIdLlibre());
                        } else {
                            JOptionPane.showMessageDialog(null, "No pots realitzar el prestec en un llibre prestat", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cap llibre seleccionat", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        cbFiltre = new JComboBox(nomsFiltres);
        cbFiltre.setPreferredSize(dimensionItems);

        gbc.gridx = i;
        gbc.gridy = 0;
        i++;
        panelSuperior.add(cbFiltre, gbc);

        tfFiltre = new JTextField("Busqueda...");
        tfFiltre.setPreferredSize(dimensioTF);
        tfFiltre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tfFiltre.getText().equals("Busqueda...")) {
                    tfFiltre.setText("");
                }
            }
        });

        tfFiltre.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    filtrar(1);
                } catch (SQLException ex) {
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    filtrar(1);
                } catch (SQLException ex) {
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    filtrar(1);
                } catch (SQLException ex) {
                }
            }
        });

        gbc.gridx = i;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;

        panelSuperior.add(tfFiltre, gbc);

        add(panelSuperior, BorderLayout.NORTH);
    }

    private void filtrar(int num) throws SQLException {
        PrestecDAOImpl pdi = new PrestecDAOImpl();
        List<Llibre> llista;
        switch (num) {
            case 1:
                if (_verificarLongitud()) {
                    llista = pdi.filtrarTaula(tfFiltre.getText());
                    _actualitzarTaula(llista);
                } else {
                    _iniciarTaulaGeneral();
                }
                break;
            case 2:
                llista = pdi.filtrarPrestats(true);
                _actualitzarTaula(llista);
                break;

            case 3:
                llista = pdi.filtrarPrestats(false);
                _actualitzarTaula(llista);
                break;

            case 4:
                _iniciarTaulaGeneral();
                break;

        }
    }

    private boolean _verificarLongitud() {
        return tfFiltre.getText().length() > 2;
    }

    private void montar() {
        setLayout(new BorderLayout());
        _iniciarElements();
        _iniciarTaula();

    }

    private void _actualitzarTaula(List<Llibre> llistaLlibres) {
        llibresTaulaActuals = llistaLlibres;
        data = new Object[llistaLlibres.size()][columnesTaula.length];
        for (int i = 0; i < llistaLlibres.size(); i++) {
            Llibre llibre = llistaLlibres.get(i);
            data[i][0] = llibre.getTitol();
            data[i][1] = llibre.getAutor();
            data[i][2] = llibre.getIsbn();
        }

        model.setDataVector(data, columnesTaula);
        table.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }

    private void _iniciarTaulaGeneral() throws SQLException {
        LlibreDAOImpl udi = new LlibreDAOImpl();
        java.util.List<Llibre> llibres = udi.obtenirTots();
        _actualitzarTaula(llibres);

    }

    private void _iniciarTaula() {
        try {
            panelInferior = new JPanel();
            panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelInferior.setBackground(new Color(39, 207, 240));

            model = new DefaultTableModel(data, columnesTaula);

            table = new JTable(model) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hacer que todas las celdas no sean editables
                }
            };
            _iniciarTaulaGeneral();

            scrollPane = new JScrollPane(table);
            scrollPane.setBorder(bordeNegroTabla);
            scrollPane.setPreferredSize(dimensioTaula);
            panelInferior.add(scrollPane);

            add(panelInferior);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void iniciarVista() {
        setSize(800, 500);
        setLocationRelativeTo(this);
        setResizable(false);
        setTitle("Prestecs llibres");
        setVisible(true);
    }

}
