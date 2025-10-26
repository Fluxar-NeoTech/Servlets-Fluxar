package com.example.servletfluxar.conexao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() {
        Dotenv dotenv = null;
//      Tenta carregar o .env localmente
        try {
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            dotenv = null;
        }

//      Busca variáveis — primeiro do sistema para caso a aplicação esteja em produção,
//      depois do .env (local) caso esteja em desenvolvimento

//        Url do banco de dados:
        String url = System.getenv("DATABASE_URL");
        if (url == null && dotenv != null) url = dotenv.get("DATABASE_URL");

//        Usuário de acesso ao banco de dados:
        String usuario = System.getenv("DATABASE_USER");
        if (usuario == null && dotenv != null) usuario = dotenv.get("DATABASE_USER");

//        Senha do banco de dados:
        String senha = System.getenv("DATABASE_PASSWORD");
        if (senha == null && dotenv != null) senha = dotenv.get("DATABASE_PASSWORD");

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, usuario, senha);
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void desconectar(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao fechar conexão");
        }
    }
}