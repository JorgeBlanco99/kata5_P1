package kata5_p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author jorge
 */
public class Kata5_P1 {
    private static String url = "jdbc:sqlite:KATA5.db";
    private static Connection conn = null;
    
    public static void main(String[] args) {
        connect();
        selectAll("PEOPLE");
        createNewTable();
        selectAll("EMAIL");
    }
    
    public static Connection connect(){
        
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conexión a SQLite establecida");
        } catch (SQLException ex) {
            Logger.getLogger(Kata5_P1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public static void selectAll(String from){
        String sql = "SELECT * FROM " + from;
        
        try (   //conn = connect();
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
            Logger.getLogger(Kata5_P1.class.getName()).log(Level.SEVERE, null, ex);
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
