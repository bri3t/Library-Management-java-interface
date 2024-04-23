package interficie;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import logica.PrestecDAOImpl;

public class ModificarPrestec extends JDialog {

    PrestecDAOImpl pdi = new PrestecDAOImpl();
    int idLlibre;
    JDateChooser dateChooser;

    public ModificarPrestec(int idLlibre) {
        this.idLlibre = idLlibre;
        setUpGUI();
    }

    private void setUpGUI() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(layout);

        // Configuraciones generales para los componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Margen de 10 píxeles en todos los lados
        gbc.weightx = 1;
        gbc.gridwidth = 1;

        // Primera fila con etiquetas
        JLabel startDateLabel = new JLabel("Data inici:");
        java.sql.Date dataPrestec = pdi.obtenirDataPrestecPerIdLlibre(idLlibre);
        JLabel startDate = new JLabel(dateParse(dataPrestec));

        JLabel endDateLabel = new JLabel("Data retorn actual:");
        java.sql.Date dataDevolucio = pdi.obtenirDataDevolucioPerIdLlibre(idLlibre);
        JLabel endDate = new JLabel(dateParse(dataDevolucio));

        // Segunda fila con etiqueta y calendario
        JLabel newEndDateLabel = new JLabel("Nova data retorn:");
        dateChooser = new JDateChooser();
        dateChooser.setDate(dataDevolucio);
        dateChooser.setMinSelectableDate(new java.util.Date(dataDevolucio.getTime()));
        dateChooser.setDateFormatString("dd/MM/yyyy");

        JButton btnConfirmar = new JButton("Confirmar modificació");

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verificarModificacion(dateChooser.getDate())) {
                    if (pdi.modificarDataDevolucio(new java.sql.Date(dateChooser.getDate().getTime()), idLlibre)) {
                        JOptionPane.showMessageDialog(null, "Modificacio realitzada correctament", "Correcte", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La data seleccionada no és correcte", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0
        add(startDateLabel, gbc);

        gbc.gridx = 1; // Columna 1
        gbc.gridy = 0; // Fila 0
        add(startDate, gbc);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 1; // Fila 1
        add(endDateLabel, gbc);

        gbc.gridx = 1; // Columna 1
        gbc.gridy = 1; // Fila 1
        add(endDate, gbc);

        gbc.gridx = 0; // Columna 0
        gbc.gridy = 2; // Fila 1
        add(newEndDateLabel, gbc);

        gbc.gridx = 1; // Columna 1
        gbc.gridy = 2; // Fila 1
        add(dateChooser, gbc);

        gbc.gridx = 0; // Columna 1
        gbc.gridy = 3; // Fila 1
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        add(btnConfirmar, gbc);

        // Configuración del diálogo
        setSize(350, 200);
        setResizable(false);
        setTitle("Modificar prestec");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra el diálogo respecto a su ventana padre
        setModal(true);
        setVisible(true);
    }

    private boolean verificarModificacion(Date date) {
        Date today = new Date();
        return date.after(today);
    }

    private String dateParse(Date date) {
        return String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(date));
    }

}
