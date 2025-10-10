package com.example.servletfluxar.dao;

import com.example.servletfluxar.connection.Conexao;
import com.example.servletfluxar.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {
    public static List<Endereco> listar() {
//        Declarando variáveis:
        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        List<Endereco> enderecos = new ArrayList<>();

//        Conectando ao banco de dados e enviando comando sql:
        try {
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM endereco ORDER BY id");

//            Criando objetos e adicionando a lista dos endereços:
            while (rs.next()) {
                enderecos.add(new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getInt("numero"), rs.getString("complemento")));
            }

//            Returnando a lista de endereços

            return enderecos;

        } catch (Exception e) {
            return enderecos;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static Endereco buscarPorId(int id){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados e executando sql:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM endereco WHERE id = ?");
            rs = pstmt.executeQuery();

            if(rs.next()){
                return new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getInt("numero"), rs.getString("complemento"));
            }
            return null;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean cadastrar(Endereco endereco){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO endereco (cep, numero, complemento) VALUES (?, ?, ?)");
            pstmt.setString(1,endereco.getCep());
            pstmt.setInt(2,endereco.getNumero());
            pstmt.setString(3,endereco.getComplemento());

//            Retornando se houve alteração, ou seja, pelo menos uma linha do banco foi adicionada:
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean removerPorId(int id){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE * FROM endereco WHERE id = ?");
            pstmt.setInt(1, id);

//            Retornando se houve alteração, ou seja, pelo menos uma linha do banco foi removida:
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }
}
