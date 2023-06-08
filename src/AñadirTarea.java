import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class AñadirTarea extends JFrame implements ActionListener
{

    EmailSender enviarcorreo = new EmailSender();
    public static int iduser;
    public static String correo;
    public int idtask,numAleatorio;

    public String nomTarea,comentario,notaTarea,fechaInicio,fechaFinal,estado,prioridad;

    public String Estado ="pendiente";

    private JLabel labelnomtask,labelcoment,labelnottask,labelfchini,labelfchfin,labelprioty;
    private JTextField textfieldnomtask,textFieldcoment,textFieldnottask,textFieldfchini,textFieldfchfin,textFieldprioty;

    private JButton buttonaddtas,buttonregresar;

    public AñadirTarea(int idusuafortarea,String correousar)
    {
        System.out.println(idusuafortarea);
        iduser=idusuafortarea;
        correo=correousar;
        this.setBounds(0, 0, 600, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setLayout(null);
        setTitle("Añadir tarea");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(5, 38, 65));
        Interfazinicial();

    }

    private void Interfazinicial()
    {
        labelnomtask = new JLabel("Ingrese el nombre de la tarea:");
        labelnomtask.setBounds(20,90,190,30);
        labelnomtask.setFont(new Font ("Serif",0,13));
        labelnomtask.setForeground(new Color(255,255,255));
        add(labelnomtask);

        textfieldnomtask = new JTextField();
        textfieldnomtask.setBounds(300,90,200,30);
        add(textfieldnomtask);

        labelcoment = new JLabel("Comentario:");
        labelcoment.setBounds(20,130,100,30);
        labelcoment.setFont(new Font ("Serif",0,13));
        labelcoment.setForeground(new Color(255,255,255));
        add(labelcoment);

        textFieldcoment = new JTextField();
        textFieldcoment.setBounds(300,130,200,30);
        add(textFieldcoment);

        labelnottask = new JLabel("Nota rapida:");
        labelnottask.setBounds(20,170,150,30);
        labelnottask.setFont(new Font ("Serif",0,13));
        labelnottask.setForeground(new Color(255,255,255));
        add(labelnottask);

        textFieldnottask = new JTextField();
        textFieldnottask.setBounds(300,170,200,30);
        add(textFieldnottask);

        labelfchini = new JLabel("Fecha inicio (AAAA-MM-DD):");
        labelfchini.setBounds(20,210,200,30);
        labelfchini.setFont(new Font ("Serif",0,13));
        labelfchini.setForeground(new Color(255,255,255));
        add(labelfchini);

        textFieldfchini = new JTextField();
        textFieldfchini.setBounds(300,210,200,30);
        add(textFieldfchini);

        labelfchfin = new JLabel("Fecha final (AAAA-MM-DD):");
        labelfchfin.setBounds(20,250,200,30);
        labelfchfin.setFont(new Font ("Serif",0,13));
        labelfchfin.setForeground(new Color(255,255,255));
        add(labelfchfin);

        textFieldfchfin = new JTextField();
        textFieldfchfin.setBounds(300,250,200,30);
        add(textFieldfchfin);

        labelprioty = new JLabel("Prioridad (alta/media/baja):");
        labelprioty.setBounds(20,290,150,30);
        labelprioty.setFont(new Font ("Serif",0,13));
        labelprioty.setForeground(new Color(255,255,255));
        add(labelprioty);

        textFieldprioty = new JTextField();
        textFieldprioty.setBounds(300,290,200,30);
        add(textFieldprioty);


        buttonaddtas = new JButton("Añadir Tarea");
        buttonaddtas.setBounds(225, 400,160,30);
        add(buttonaddtas);
        buttonaddtas.addActionListener(this);

        buttonregresar = new JButton("regresar");
        buttonregresar.setBounds(480,710,100,30);
        add(buttonregresar);
        buttonregresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource()==buttonaddtas)
        {
            if(textfieldnomtask.getText().isEmpty() || textFieldcoment.getText().isEmpty()|| textFieldnottask.getText().isEmpty() || textFieldfchini.getText().isEmpty() || textFieldfchfin.getText().isEmpty() || textFieldprioty.getText().isEmpty() )
            {
                JOptionPane.showMessageDialog(null, "Porfavor rellene todos los campos", "Érror", JOptionPane.INFORMATION_MESSAGE);
                textfieldnomtask.setText("");
                textFieldcoment.setText("");
                textFieldnottask.setText("");
                textFieldfchini.setText("");
                textFieldfchfin.setText("");
                textFieldprioty.setText("");
                idtask=0;
            }else{
                iduser=iduser*1;
                idtask=NumeroRandom();
                nomTarea=textfieldnomtask.getText();
                comentario=textFieldcoment.getText();
                notaTarea=textfieldnomtask.getText();
                fechaInicio=textFieldfchini.getText();
                fechaFinal=textFieldfchfin.getText();
                estado=Estado;
                prioridad=textFieldprioty.getText();

                CrearTarea(iduser,idtask,nomTarea,comentario,notaTarea,fechaInicio,fechaFinal,estado,prioridad);
                enviarcorreo.sendemail(correo,nomTarea,prioridad,fechaFinal);

                textfieldnomtask.setText("");
                textFieldcoment.setText("");
                textFieldnottask.setText("");
                textFieldfchini.setText("");
                textFieldfchfin.setText("");
                textFieldprioty.setText("");
                idtask=0;

                }
        }

        if(e.getSource()==buttonregresar)
        {
            this.setVisible(false);
            (new Tareas(iduser,correo)).setVisible(true);
        }

    }



    public static void CrearTarea(int iduser, int idtask, String nomTarea, String comentario, String notaTarea, String fechaInicio, String fechaFinal, String estado, String prioridad)
    {
        Connection conexion;
        try {
            conexion = ConexionBD.obtenerConexion();

            String sql = "INSERT INTO Tareas (Id_usuario, Id_tarea, Nom_tarea, Comentario, Notatarea, fch_inicio, fch_final, estado, prioridad) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexion.prepareStatement(sql);

            // Asignar los valores a los parámetros de la sentencia SQL
            pstmt.setInt(1, iduser);
            pstmt.setInt(2, idtask);
            pstmt.setString(3, nomTarea);
            pstmt.setString(4, comentario);
            pstmt.setString(5, notaTarea);
            pstmt.setDate(6, Date.valueOf(fechaInicio));
            pstmt.setDate(7, Date.valueOf(fechaFinal));
            pstmt.setString(8, estado);
            pstmt.setString(9, prioridad);

            // Ejecutar la sentencia SQL
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "La tarea fue ingresada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al establecer la conexión o al insertar datos en la tabla Tareas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int NumeroRandom ()
    {
        Random rand = new Random();
        numAleatorio = rand.nextInt();
        if (numAleatorio<0)
        {
            numAleatorio=numAleatorio*-1;
        }else
        {
            numAleatorio=numAleatorio;
        }
        return numAleatorio;
    }

}
