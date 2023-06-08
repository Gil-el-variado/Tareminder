import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Interfaz2 extends  JFrame implements ActionListener {

    private JLabel labelmsgcorreo;
    private JLabel labelmsgcontra;

    private JTextField textfieldCorreo,textFieldContra;

    private JButton buttonregresar,buttoniniciarsesion;

    String correoaux,contraseñaux;
    public static int idusuafortask;
    public static boolean siguientepantalla=false;
    public Interfaz2()
    {
        this.setBounds(0, 0, 400, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setLayout(null);
        setTitle("Iniciar Sesion");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(5, 38, 65));
        Interfazinicial();

    }



    private void Interfazinicial()
    {
        labelmsgcorreo = new JLabel("Ingrese su correo:");
        labelmsgcorreo.setBounds(20,70,100,30);
        labelmsgcorreo.setFont(new Font ("Serif",0,13));
        labelmsgcorreo.setForeground(new Color(255,255,255));
        add(labelmsgcorreo);

        textfieldCorreo = new JTextField();
        textfieldCorreo.setBounds(150,70,200,30);
        add(textfieldCorreo);

        labelmsgcontra = new JLabel("Ingrese su contraseña:");
        labelmsgcontra.setBounds(20,110,150,30);
        labelmsgcontra.setFont(new Font ("Serif",0,13));
        labelmsgcontra.setForeground(new Color(255,255,255));
        add(labelmsgcontra);

        textFieldContra = new JTextField();
        textFieldContra.setBounds(150,110,200,30);
        add(textFieldContra);


        buttoniniciarsesion = new JButton("Iniciar Sesion");
        buttoniniciarsesion.setBounds(115, 190,160,30);
        add(buttoniniciarsesion);
        buttoniniciarsesion.addActionListener(this);

        buttonregresar = new JButton("regresar");
        buttonregresar.setBounds(280,500,100,30);
        add(buttonregresar);
        buttonregresar.addActionListener(this);

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttoniniciarsesion) {
            if(textfieldCorreo.getText().isEmpty() || textFieldContra.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Porfavor rellene todos los campos", "Érror", JOptionPane.INFORMATION_MESSAGE);
            }else{
            correoaux = textfieldCorreo.getText();
            contraseñaux = textFieldContra.getText();
            IniciarSesion(correoaux, contraseñaux);
            textfieldCorreo.setText("");
            textFieldContra.setText("");

            if (siguientepantalla == true) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                this.setVisible(false);
                Tareas tareas = new Tareas(idusuafortask,correoaux);
                tareas.setVisible(true);
            }
        }}

        if (e.getSource() == buttonregresar) {
            this.setVisible(false);
            Claseinicio claseInicio = new Claseinicio();
            claseInicio.setVisible(true);
        }
    }


    public static void IniciarSesion(String correoaux, String contraseñaux)
    {
        try {
            // Obtener la conexión a la base de datos
            Connection conexion = ConexionBD.obtenerConexion();


            String consulta = "SELECT id_usuario, correo, contraseña FROM usuario WHERE correo = ? AND contraseña = ?";


            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, correoaux);
            sentencia.setString(2, contraseñaux);

            // Ejecutar la consulta
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {

                idusuafortask = resultado.getInt("id_usuario");
                siguientepantalla=true;


            } else {
                // Si no se encuentra un registro, las credenciales son incorrectas
                JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos");
                siguientepantalla=false;
                conexion.close();

            }

            // Cerrar los recursos
            resultado.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
