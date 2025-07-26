package dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection conectar(){
        String url = System.getenv("DATABASE_URL");
        String usuario = System.getenv("DATABASE_USER");
        String senha = System.getenv("DATABASE_PASSWORD");
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url,usuario,senha);
            return conn;
        }catch (SQLException sqle){
            throw new RuntimeException("Erro ao conectar com o banco de dados");
        }catch (Exception e ){
            throw new RuntimeException("Driver do banco de dados não foi encontrado");
        }
    }
}