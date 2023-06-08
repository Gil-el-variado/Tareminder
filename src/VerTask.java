import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class VerTask {

    private static final String USUARIO = "postgres";
    private static final String CONTRASEÑA = "a";
    private static String url = "jdbc:postgresql://localhost:5432/AppNotis";

    public void mostrarTareas(int idusuafortarea) {
        // Crear la interfaz gráfica
        JFrame frame = new JFrame("Lista de Tareas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(false);
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        frame.getContentPane().setBackground(new Color(5, 38, 65));
        frame.setBackground(new Color(5, 38, 65)); // Cambiar color de fondo a azul
        frame.getContentPane().add(panel);

        // Modelo para la tabla
        String[] columnNames = {"ID_Tarea", "ID_Usuario", "Nombre_Tarea", "Comentario", "Fecha_inicio", "Fecha_Final", "Estado", "Prioridad"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Obtener los datos de la base de datos y agregarlos a la tabla
        try (Connection conn = DriverManager.getConnection(url, USUARIO, CONTRASEÑA);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Tareas WHERE Id_usuario = ?");
        ) {
            pstmt.setInt(1, idusuafortarea);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idTarea = rs.getInt("Id_tarea");
                int idUsuario = rs.getInt("Id_usuario");
                String nombreTarea = rs.getString("Nom_tarea");
                String comentario = rs.getString("Comentario");
                Date fechaInicio = rs.getDate("fch_inicio");
                Date fechaFinal = rs.getDate("fch_final");
                String estado = rs.getString("estado");
                String prioridad = rs.getString("prioridad");

                Object[] row = {idTarea, idUsuario, nombreTarea, comentario, fechaInicio, fechaFinal, estado, prioridad};
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las tareas:\n" + e.getMessage());
        }

        // Personalizar el tamaño de las filas
        table.setRowHeight(40); // Ajusta el tamaño de las filas a 30 píxeles

        // Personalizar el tamaño de la fuente en las celdas
        Font tableFont = table.getFont();
        table.setFont(new Font(tableFont.getName(), Font.PLAIN, 10)); // Ajusta el tamaño de la fuente a 16 puntos

        // Agregar la tabla a un JScrollPane y al panel
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botón Regresar
        JButton backButton = new JButton("Regresar");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Cerrar la ventana actual
                // Aquí puedes llamar al método correspondiente para volver al main (principal)
            }
        });
        panel.add(backButton, BorderLayout.SOUTH);

        // Mostrar la ventana
        frame.setVisible(true);
    }
}
