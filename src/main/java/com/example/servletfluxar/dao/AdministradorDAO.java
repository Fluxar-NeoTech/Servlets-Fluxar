package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {
    public static List<Administrador> listar() {
//        Declarando variáveis:
        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        List<Administrador> administradores = new ArrayList<>();

//        Conectando ao banco de dados e enviando comando sql:
        try {
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM administrador ORDER BY id");

//            Criando objetos e adicionando a lista dos administradores:
            while (rs.next()) {
                administradores.add(new Administrador(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"), rs.getString("email"), rs.getString("senha")));
            }

//            Returnando a lista de administrador
            return administradores;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return administradores;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static Administrador buscarPorId(int id){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM administrador WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                return new Administrador(rs.getInt("id"),rs.getString("nome"),rs.getString("sobrenome"),rs.getString("email"),rs.getString("senha"));
            }
            return null;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static Administrador buscarPorEmail(String email){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM administrador WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                return new Administrador(rs.getInt("id"),rs.getString("nome"),rs.getString("sobrenome"),rs.getString("email"),rs.getString("senha"));
            }
            return null;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static Administrador buscarPorNomeSobrenome(String nome, String sobrenome){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM administrador WHERE nome = ? AND sobrenome = ?");
            pstmt.setString(1, nome);
            pstmt.setString(2,sobrenome);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                return new Administrador(rs.getInt("id"),rs.getString("nome"),rs.getString("sobrenome"),rs.getString("email"),rs.getString("senha"));
            }
            return null;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean cadastrar(Administrador administrador){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO administrador (nome, sobrenome, email, senha) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1,administrador.getNome());
            pstmt.setString(2,administrador.getSobrenome());
            pstmt.setString(3,administrador.getEmail());
            pstmt.setString(4,administrador.getSenha());

            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean alterarSenha(String email, String novaSenha) {
        // Declaração de variáveis
        Connection conn = null;
        PreparedStatement pstm;

        try {
            // Obtenção da conexão com o banco de dados
            conn = Conexao.conectar();

            // Preparação do comando SQL para atualizar a senha do admin da empresa
            pstm = conn.prepareStatement("UPDATE administrador SET senha = ? WHERE email = ?");
            pstm.setString(1,novaSenha);
            pstm.setString(2,email);

            // Execução da atualização
            return pstm.executeUpdate()>0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public boolean removerPorId(int id){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE * FROM administrador WHERE id = ?");
            pstmt.setInt(1, id);
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
