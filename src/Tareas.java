import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tareas extends JFrame implements ActionListener
{
    private JButton buttonregresar,buttonhome,buttoncreartask,buttonvertasks,buttonDeletetask;

    private JLabel labelTituloTaks;

    public static int idusuafortarea;
    public static String correousar;
    VerTask llamar = new VerTask();

    public Tareas(int idusuafortask, String correoaux)
    {
        System.out.println(idusuafortask);
        idusuafortarea=idusuafortask;
        correousar=correoaux;
        this.setBounds(0, 0, 400, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setLayout(null);
        setTitle("Tareas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(5, 38, 65));
        Interfazinicial();

    }

    private void Interfazinicial() {

        labelTituloTaks = new JLabel("Recordatorios");
        labelTituloTaks.setBounds(76, 10, 300, 120); // Ajusta las coordenadas y el tamaño del JLabel
        labelTituloTaks.setFont(new Font("Arial Black", Font.PLAIN, 32)); // Aumenta el tamaño de la fuente
        labelTituloTaks.setForeground(new Color(255, 255, 255));
        add(labelTituloTaks);

        buttoncreartask = new JButton("Añadir Tarea");
        buttoncreartask.setBounds(50,180,130,30);
        add(buttoncreartask);
        buttoncreartask.addActionListener(this);

        buttonDeletetask = new JButton("Borrar Tarea");
        buttonDeletetask.setBounds(215,180,130,30);
        add(buttonDeletetask);
        buttonDeletetask.addActionListener(this);

        buttonvertasks = new JButton("Ver tareas");
        buttonvertasks.setBounds(125,240,130,30);
        add(buttonvertasks);
        buttonvertasks.addActionListener(this);

        buttonhome = new JButton("Home");
        buttonhome.setBounds(150,500,100,30);
        add(buttonhome);
        buttonhome.addActionListener(this);

        buttonregresar = new JButton("regresar");
        buttonregresar.setBounds(280,500,100,30);
        add(buttonregresar);
        buttonregresar.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==buttoncreartask)
        {
            this.setVisible(false);
            AñadirTarea task = new AñadirTarea(idusuafortarea,correousar);
            task.setVisible(true);
        }

        if(e.getSource()==buttonDeletetask)
        {
            this.setVisible(false);
            DeleteTask taskdel = new DeleteTask(idusuafortarea,correousar);
            taskdel.setVisible(true);

        }


        if(e.getSource()==buttonvertasks)
        {
            llamar.mostrarTareas(idusuafortarea);
        }


        if(e.getSource()==buttonregresar)
        {
           System.out.println("imprimir");
            this.setVisible(false);
            (new Interfaz2()).setVisible(true);
        }

        if(e.getSource()==buttonhome)
        {
            System.out.println("imprimir");
            this.setVisible(false);
            (new Claseinicio()).setVisible(true);
        }

    }
}
