package kata5_p1;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author jorge
 */
public class Kata5_P1 {
      private static final String email = "email.txt";
    private static String url = "jdbc:sqlite:KATA5.db";
    
    public static void main(String[] args) {
        connect();
        getEmails();
        selectAll("PEOPLE");
        createNewTable();
        selectAll("EMAIL");
    }
    
    public static Connection connect(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conexión a SQLite establecida");
        } catch (SQLException ex) {
            Logger.getLogger(Kata5_P1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    private static void getEmails(){
        try {
            List<String> emails = MailListReader.read(email);
            for (String email : emails) {
                insert(email);
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private static void insert(String email){
        String sql = "INSERT INTO EMAIL(direccion) VALUES(?)";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void selectAll(String from){
        String sql = "SELECT * FROM " + from;
        
        try (   Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql)){
            System.out.println("");
            
            //Recorrer registros
            while (rs.next()){
                System.out.println(rs.getInt("id") + "\t" + 
                        rs.getString("Nombre") + "\t" + 
                        rs.getString("Apellidos") + "\t" +
                        rs.getString("Departamento") + "\t");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("");
    }
    
    public static void createNewTable(){
        String sql = "CREATE TABLE IF NOT EXISTS EMAIL (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT, \n"
                + " direccion text NOT NULL);";
        
        try (Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement()) {
            //Crear tabla
            statement.execute(sql);
            System.out.println("Tabla creada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}