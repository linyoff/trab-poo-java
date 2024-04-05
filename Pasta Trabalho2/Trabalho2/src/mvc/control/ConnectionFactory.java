/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.control;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Aliny
 */
public class ConnectionFactory {

    public Connection getConnection() {
        try {
            //tratando as propriedades
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123Tomate!");
            properties.setProperty("useSSL", "false");
            properties.setProperty("useTimezone", "true");
            properties.setProperty("serverTimezone", "America/Sao_Paulo");
            properties.setProperty("allowPublicKeyRetrieval","true");
            //buscando url
            String con = "jdbc:mysql://localhost/trabalho2";
            
            return DriverManager.getConnection(con, properties);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
