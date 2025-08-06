package dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final Dotenv dotenv = Dotenv.load();
    public static Connection conectar(){
        String url = dotenv.get("DATABASE_URL");
        String usuario = dotenv.get("DATABASE_USER");
        String senha = dotenv.get("DATABASE_PASSWORD");
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