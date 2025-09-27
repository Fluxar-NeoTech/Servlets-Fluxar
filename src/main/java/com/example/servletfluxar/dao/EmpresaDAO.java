package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.model.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Lista a fazer para bloquear SQLInjection;
// Não permitir ;
//
public class EmpresaDAO {
    public static List<Empresa> listarEmpresas() {
//        Declarando variáveis:
        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        List<Empresa> empresas = new ArrayList<>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM empresa ORDER BY id");

//            Criando objetos e adicionando a lista das empresas:
            while (rs.next()) {
                empresas.add(new Empresa(rs.getInt("id"),rs.getString("nome"),rs.getString("cnpj"), rs.getString("email"), rs.getString("senha"), rs.getString("telefone")));
            }

//            Retornando a lista de empresas cadastradas:
            return empresas;

        } catch (Exception e) {
            e.printStackTrace();
            return empresas;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static Empresa buscarPorId(int id) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if (rs.next()) {
                return new Empresa(rs.getInt("id"),rs.getString("nome"),rs.getString("cnpj"), rs.getString("email"), rs.getString("senha"), rs.getString("telefone"));
            }

            return null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static Empresa buscarPorCNPJ(String cnpj) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE cnpj = ?");
            pstmt.setString(1, cnpj);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if (rs.next()) {
                return new Empresa(rs.getInt("id"),rs.getString("nome"),rs.getString("cnpj"), rs.getString("email"), rs.getString("senha"), rs.getString("telefone"));
            }
            return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static Empresa buscarPorNome(String nome) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE nome = ?");
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if (rs.next()) {
                return new Empresa(rs.getInt("id"),rs.getString("nome"),rs.getString("cnpj"), rs.getString("email"), rs.getString("senha"), rs.getString("telefone"));
            }

            return null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static Empresa buscarPorEmail(String email) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if (rs.next()) {
                return new Empresa(rs.getInt("id"),rs.getString("nome"),rs.getString("cnpj"), rs.getString("email"), rs.getString("senha"), rs.getString("telefone"));
            }

            return null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean cadastrar(Empresa empresa){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO empresa (cnpj, nome,email, senha, telefone) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1,empresa.getCnpj());
            pstmt.setString(2,empresa.getNome());
            pstmt.setString(3,empresa.getEmail());
            pstmt.setString(4,empresa.getSenha());
            pstmt.setString(5,empresa.getTelefone());

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
        int linhas;

        try {
            // Obtenção da conexão com o banco de dados
            conn = Conexao.conectar();

            // Preparação do comando SQL para atualizar a senha do admin da empresa
            pstm = conn.prepareStatement("UPDATE empresa SET senha = ? WHERE email = ?");
            pstm.setString(1,novaSenha);
            pstm.setString(2,email);

            // Execução da atualização
            linhas = pstm.executeUpdate();

            return linhas>0;

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
            pstmt = conn.prepareStatement("DELETE * FROM empresa WHERE id = ?");
            pstmt.setInt(1, id);
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean removerPorNome(String nome){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE * FROM empresa WHERE nome = ?");
            pstmt.setString(1, nome);
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean removerPorCNPJ(String cnpj){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE * FROM empresa WHERE cnpj= ?");
            pstmt.setString(1, cnpj);
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
