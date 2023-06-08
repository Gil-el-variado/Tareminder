import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Claseinicio extends JFrame implements ActionListener{
    private JLabel labelTitule,labeltextinisesi,labeltextregist;
    private JTextField textfieldNombre;
    private JButton buttonIniciarSes;

    private JButton buttonRegistrarse;
    String a;

    public Claseinicio() {
        this.setBounds(0, 0, 800, 700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setLayout(null);
        setTitle("Tareminder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(5, 38, 65));
        Interfazinicial();
    }

    private void Interfazinicial()
    {
        labelTitule = new JLabel("Bienvenido a Tareminder");
        labelTitule.setBounds(0, 10, 800, 100); // Ajusta las coordenadas y el tamaño del JLabel
        labelTitule.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto horizontalmente
        labelTitule.setFont(new Font("Arial Black", Font.PLAIN, 36)); // Aumenta el tamaño de la fuente
        labelTitule.setForeground(new Color(255, 255, 255));
        add(labelTitule);

        labeltextinisesi = new JLabel("Para comenzar a agregar tareas, inicia sesión");
        labeltextinisesi.setBounds(145, 250, 500, 30);
        labeltextinisesi.setHorizontalAlignment(SwingConstants.CENTER);
        labeltextinisesi.setFont(new Font("Serif", 0, 18));
        labeltextinisesi.setForeground(new Color(255, 255, 255));
        add(labeltextinisesi);

        buttonIniciarSes = new JButton("Iniciar Session");
        buttonIniciarSes.setBounds(332, 300, 120, 30); // Ajusta las coordenadas para centrar el botón

        add(buttonIniciarSes);
        buttonIniciarSes.addActionListener(this);

        labeltextregist = new JLabel("Si no tiene una cuenta registrese");
        labeltextregist.setBounds(145, 450, 500, 30);
        labeltextregist.setHorizontalAlignment(SwingConstants.CENTER);
        labeltextregist.setFont(new Font("Serif", 0, 18));
        labeltextregist.setForeground(new Color(255, 255, 255));
        add(labeltextregist);

        buttonRegistrarse = new JButton("Registrarse");
        buttonRegistrarse.setBounds(332, 500, 120, 30); // Ajusta las coordenadas para centrar el botón
        add(buttonRegistrarse);
        buttonRegistrarse.addActionListener(this);

    }




    public void actionPerformed(ActionEvent e){
        if(e.getSource()==buttonIniciarSes){
            this.setVisible(false);
            (new Interfaz2()).setVisible(true);
            a=textfieldNombre.getText();
        }

        if(e.getSource()==buttonRegistrarse){
            this.setVisible(false);
            (new Registrarse()).setVisible(true);

        }



    }




}
