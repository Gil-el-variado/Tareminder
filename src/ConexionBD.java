import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static Connection conexion;
    private static final String URL = "jdbc:postgresql://localhost:5432/AppNotis";
    private static final String USUARIO = "postgres";
    private static final String CONTRASENA = "a";

    private ConexionBD() {}

    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        }
        return conexion;
    }
}

