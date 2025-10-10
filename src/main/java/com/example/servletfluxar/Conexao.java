package com.example.servletfluxar;

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
            return conn;
        }catch (Exception e){
            return conn;
        }
    }

    public static void desconectar(Connection conn){
        try{
            if(conn!=null&&!conn.isClosed()){
                conn.close();
            }
        }catch (SQLException sqle){
            throw new RuntimeException("Erro ao fechar conexão");
        }
    }
}