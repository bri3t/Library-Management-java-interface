package interficie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
import logica.PersonalDAOImpl;
import logica.PrestecDAOImpl;
import model.Llibre;

/**
 *
 * @author arnau
 */
public class Prestecs extends JDialog {

    // botons
    JButton btnNouPrestec, btnModificar, btnRetorn;
    List<JButton> llistaBotons = new ArrayList<>();

    // altres items
    JTextField tfFiltre;
    JComboBox cbFiltre;

    // llistes de noms
    String[] llistaNomsBotons = {"Nou Prestec", "Modificar", "Retorn"};
    String[] nomsFiltres = {"Tots", "Prestat", "No prestats", "Incidències"};
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
    PrestecDAOImpl prestecdi = new PrestecDAOImpl();
    PersonalDAOImpl personaldi = new PersonalDAOImpl();

    public Prestecs(Frame owner) {

        super(owner, true);
        this.owner = owner;
        montar();
        filtreCombo();
        iniciarVista();
    }

    private void filtreCombo() {
        cbFiltre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener el valor seleccionado
                    tfFiltre.setText("");
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
                        case "Incidències":
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
        btnModificar = llistaBotons.get(1);
        btnRetorn = llistaBotons.get(2);

        btnNouPrestec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    int idLlibre = llibresTaulaActuals.get(selectedRow).getIdLlibre();
                    if (selectedRow != -1) {
                        if (!prestecdi.comprovarSiEsprestat(idLlibre)) {
                            if (cbFiltre.getSelectedIndex() != 1) {
                                new FinestraPrestec(owner, idLlibre);
                                filtrar(1);
                            } else {
                                JOptionPane.showMessageDialog(null, "No pots realitzar el prestec en un llibre prestat", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Aquest llibre ja està prestat", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cap llibre seleccionat", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnRetorn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int idLlibre = llibresTaulaActuals.get(selectedRow).getIdLlibre();
                    if (prestecdi.comprovarSiEsprestat(idLlibre)) {
                        if (prestecdi.realitzarRetorn(idLlibre)) {
                            try {
                                filtrar(1);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Retorn realitzat amb èxit", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Aquest llibre no està prestat", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    new ModificarPrestec( llibresTaulaActuals.get(selectedRow).getIdLlibre());
                }
            }
        });

        cbFiltre = new JComboBox(nomsFiltres);
        cbFiltre.setPreferredSize(dimensionItems);

        gbc.gridx = i;
        gbc.gridy = 0;
        i++;
        panelSuperior.add(cbFiltre, gbc);

        tfFiltre = new JTextField();
        tfFiltre.setPreferredSize(dimensioTF);

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

    private void eliminarLlibreLlistaPerIdIActualitzar(int idLlibre) {

        try {
            for (Llibre llibre : llibresTaulaActuals) {
                if (llibre.getIdLlibre() == idLlibre) {
                    llibresTaulaActuals.remove(llibre);
                }
                break;
            }

            _actualitzarTaula(llibresTaulaActuals);
        } catch (SQLException ex) {
            Logger.getLogger(Prestecs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void filtrar(int num) throws SQLException {
        PrestecDAOImpl pdi = new PrestecDAOImpl();
        List<Llibre> llista;
        switch (num) {
            case 1: // filtrar per la paraula del buscador
                String selectedValue = (String) cbFiltre.getSelectedItem();
                switch (selectedValue) {
                    case "Tots":
                        llista = pdi.filtrarTaula(tfFiltre.getText());
                        _actualitzarTaula(llista);
                        break;
                    case "No prestats":
                        llista = pdi.filtrarTaulaAmbCondicio(tfFiltre.getText(), false);
                        _actualitzarTaula(llista);
                        break;
                    case "Prestat":
                        llista = pdi.filtrarTaulaAmbCondicio(tfFiltre.getText(), true);
                        _actualitzarTaula(llista);
                        break;
                    case "Incidències":
                        llista = pdi.filtrarTaulaAmbCondicio(tfFiltre.getText(), true);
                        _actualitzarTaula(llista);
                        break;
                }
                break;
            case 2: // filtrar llibres prestats
                llista = pdi.filtrarPrestats(true);
                _actualitzarTaula(llista);
                break;

            case 3: // filtrar llibres no prestats
                llista = pdi.filtrarPrestats(false);
                _actualitzarTaula(llista);
                break;

            case 4:
                _iniciarTaulaGeneral();
                break;

        }
    }

    private void montar() {
        setLayout(new BorderLayout());
        _iniciarElements();
        _iniciarTaula();

    }

    private boolean esForaDeTermini(Llibre llibre) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);

        Date d = calendar.getTime();
        java.sql.Date dataSql = new java.sql.Date(d.getTime());
        java.sql.Date fechaDevolucion = prestecdi.obtenirDataDevolucioPerIdLlibre(llibre.getIdLlibre());

        boolean esSansionat = fechaDevolucion != null && fechaDevolucion.before(dataSql);

        if (esSansionat) {
            personaldi.marcatSansionatPerId(prestecdi.obtenirIdPersonalPerIdLlibre(llibre.getIdLlibre()));
        }

        return esSansionat;

    }

    private void _actualitzarTaula(List<Llibre> llistaLlibres) throws SQLException {
        LlibreDAOImpl ldi = new LlibreDAOImpl();

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

        // Renderizador de celdas personalizado
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Verificar si la fila tiene el atributo "sancionado" en true
                int idLlibre = llibresTaulaActuals.get(row).getIdLlibre();

                Llibre llibre = ldi.obtenirPerId(idLlibre);

                // Verifica si es un libro prestado y si esta fuera de terminio, si es el caso, pondra color de fondo en rojo
                c.setBackground((prestecdi.comprovarSiEsprestat(idLlibre) && esForaDeTermini(llibre)) ? Color.RED : Color.WHITE);

                return c;
            }
        };

        // Aplicar el renderizador a todas las columnas
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        renderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

//        for (Llibre llibre : llibresTaulaActuals) {
//            System.out.println(llibre.getTitol());
//        }
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
            table.getTableHeader().setResizingAllowed(false);

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
