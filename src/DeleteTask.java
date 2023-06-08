import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTask extends JFrame implements ActionListener {
    private JLabel labelidtarea;
    private JTextField textfieldidtask;
    public static int iduusertarea;
    public static String correo;
    public int idtarea;

    private JButton buttondelete,buttonregresar;

    public DeleteTask(int idusuafortarea, String correousar)
    {
        iduusertarea=idusuafortarea;
        correo=correousar;
        this.setBounds(0, 0, 500, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setLayout(null);
        setTitle("Borrar Tarea");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(5, 38, 65));
        Interfazinicial();

    }

    private void Interfazinicial()
    {
        labelidtarea = new JLabel("Ingresa el id de la tarea a borrar");
        labelidtarea.setBounds(20, 20, 300, 120); // Ajusta las coordenadas y el tamaño del JLabel
        labelidtarea.setFont(new Font("Arial Black", Font.PLAIN, 12)); // Aumenta el tamaño de la fuente
        labelidtarea.setForeground(new Color(255, 255, 255));
        add(labelidtarea);

        textfieldidtask = new JTextField();
        textfieldidtask.setBounds(270,60,200,30);
        add(textfieldidtask);

        buttondelete = new JButton("Borrar tarea");
        buttondelete.setBounds(20,130,130,30);
        add(buttondelete);
        buttondelete.addActionListener(this);

        buttonregresar = new JButton("regresar");
        buttonregresar.setBounds(350,390,100,30);
        add(buttonregresar);
        buttonregresar.addActionListener(this);



    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == buttondelete) {
            if (textfieldidtask.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Porfavor rellene todos los campos", "Érror", JOptionPane.INFORMATION_MESSAGE);
            } else
            {
                idtarea = Integer.parseInt(textfieldidtask.getText());
                BorrarTarea(idtarea);

                textfieldidtask.setText("");

            }
        }

        if(e.getSource()==buttonregresar)
        {
            this.setVisible(false);
            (new Tareas(iduusertarea,correo)).setVisible(true);
        }
    }




    public static void BorrarTarea(int idtarea) {
        Connection conexion;
        try {
            conexion = ConexionBD.obtenerConexion();
            String query = "DELETE FROM tareas WHERE id_tarea = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, idtarea);
            int filasBorradas = statement.executeUpdate();

            if (filasBorradas > 0) {
                JOptionPane.showMessageDialog(null, "La tarea ha sido borrada exitosamente.", "Borrar Tarea", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna tarea con el ID especificado.", "Borrar Tarea", JOptionPane.WARNING_MESSAGE);
            }

            statement.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }





    }




