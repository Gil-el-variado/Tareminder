import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;


public class Registrarse extends JFrame implements ActionListener
{

    private JLabel labelAclaracion;
    private JLabel labelmsgcorreo;
    private JLabel labelmsgcontra;

    private JLabel labelnombre;

    private JTextField textfieldCorreo,textFieldContra,textFieldNombre;

    private JButton buttonregresar,buttonregistrarse;

    String correo,contraseña,nombre;
    int idUsuauario,numAleatorio;
    public static boolean correoExistente = false;
    public Registrarse()
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

        labelAclaracion = new JLabel("Nota: si quiere recibir la notificacion a su correo" +
                " su correo debe ser real");
        labelAclaracion.setBounds(8,30,400,30);
        labelAclaracion.setFont(new Font ("Serif",0,13));
        labelAclaracion.setForeground(new Color(255,255,255));
        add(labelAclaracion);

        labelnombre = new JLabel("Ingrese su nombre:");
        labelnombre.setBounds(20,100,150,30);
        labelnombre.setFont(new Font ("Serif",0,13));
        labelnombre.setForeground(new Color(255,255,255));
        add(labelnombre);

        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(150,100,200,30);
        add(textFieldNombre);

        labelmsgcorreo = new JLabel("Ingrese su correo:");
        labelmsgcorreo.setBounds(20,140,100,30);
        labelmsgcorreo.setFont(new Font ("Serif",0,13));
        labelmsgcorreo.setForeground(new Color(255,255,255));
        add(labelmsgcorreo);

        textfieldCorreo = new JTextField();
        textfieldCorreo.setBounds(150,140,200,30);
        add(textfieldCorreo);

        labelmsgcontra = new JLabel("Cree su contraseña:");
        labelmsgcontra.setBounds(20,180,150,30);
        labelmsgcontra.setFont(new Font ("Serif",0,13));
        labelmsgcontra.setForeground(new Color(255,255,255));
        add(labelmsgcontra);

        textFieldContra = new JTextField();
        textFieldContra.setBounds(150,180,200,30);
        add(textFieldContra);


        buttonregistrarse = new JButton("Crear cuenta");
        buttonregistrarse.setBounds(115, 270,160,30);
        add(buttonregistrarse);
        buttonregistrarse.addActionListener(this);

        buttonregresar = new JButton("regresar");
        buttonregresar.setBounds(280,500,100,30);
        add(buttonregresar);
        buttonregresar.addActionListener(this);

    }


    public void actionPerformed(ActionEvent e){

        if(e.getSource()==buttonregistrarse)
        {
            if(textFieldNombre.getText().isEmpty() || textfieldCorreo.getText().isEmpty()|| textFieldContra.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Porfavor rellene todos los campos", "Érror", JOptionPane.INFORMATION_MESSAGE);
            }else{
            idUsuauario=NumeroRandom();
            nombre=textFieldNombre.getText();
            correo=textfieldCorreo.getText();
            contraseña=textFieldContra.getText();
            verificarCorreoExistente(correo);
            if (correoExistente==true)
            {
                JOptionPane.showMessageDialog(null, "El correo ya está registrado");
                textFieldNombre.setText("");
                textfieldCorreo.setText("");
                textFieldContra.setText("");
                correoExistente=false;

            }else {
            CrearUsuario(idUsuauario,nombre,correo,contraseña);
            textFieldNombre.setText("");
            textfieldCorreo.setText("");
            textFieldContra.setText("");
            }}
        }


        if(e.getSource()==buttonregresar)
        {
            this.setVisible(false);
            (new Claseinicio()).setVisible(true);
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

    public static void CrearUsuario(int idUsuario, String nombre, String correo, String contraseña)
    {
        Connection conexion;
        try {
            conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String consulta = "INSERT INTO usuario (id_usuario, nombre, correo, contraseña) VALUES (?, ?, ?, ?)";
        try {
            // Preparar la consulta con los parámetros
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, idUsuario);
            statement.setString(2, nombre);
            statement.setString(3, correo);
            statement.setString(4, contraseña);

            // Ejecutar el insert
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "El usuario se ha creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo crear el usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Cerrar el statement
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar el insert: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar la conexión a la base de datos
            try {
                conexion.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static void verificarCorreoExistente(String correo) {

        try {
            Connection conexion = ConexionBD.obtenerConexion();
            String consulta = "SELECT correo FROM usuario WHERE correo = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, correo);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                correoExistente = true; // El correo ya existe en la base de datos
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}








