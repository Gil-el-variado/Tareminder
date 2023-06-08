import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {


    public void sendemail(String correo, String nomTarea, String prioridad, String fechaFinal) {
        String to = correo; // Cambia según corresponda
        String from = "tareminderapp@gmail.com"; // Cambia según corresponda
        String host = "smtp.gmail.com"; // Cambia según corresponda
        String username = "tareminderapp@gmail.com"; // Cambia según corresponda
        String password = "tbnwxbbwzaylesjh"; // Cambia según corresponda

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Tienes Tareas en revisa Tareminder");
            message.setText("La tarea pendiente nueva que tienes es "+ nomTarea + " la prioridad de la tarea es "+ prioridad + " la fecha de vencimiento es: "+fechaFinal);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}