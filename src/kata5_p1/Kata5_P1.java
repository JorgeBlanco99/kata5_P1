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

    public static void main(String[] args) {
        Connection conn = null;
        String url = "jdbc:sqlite:KATA5.db";
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conexión a SQLite establecida");
        } catch (SQLException ex) {
            Logger.getLogger(Kata5_P1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "SELECT * FROM PEOPLE";
        
        try (  Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql)){
            
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
        
    }

}
