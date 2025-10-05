package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.model.Telefone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefoneDAO {

    //    Método para listar todos os telefones cadastrados:
    public static List<Telefone> listar() {
//        Declaração de variáveis:
        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        List<Telefone> telefones = new ArrayList<>();

        try {
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM telefone ORDER BY id");

            while (rs.next()) {
                telefones.add(new Telefone(rs.getInt("id"), rs.getString("numero"), rs.getInt("id_empresa")));
            }
            return telefones;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return telefones;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //    Método para listar telefones por empresa:
    public static List<Telefone> listarPorIdEmpresa(int idEmpresa) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List<Telefone> telefones = new ArrayList<>();

//        Conectando ao banco:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM telefone WHERE id_empresa = ? ORDER BY id");
            pstmt.setInt(1, idEmpresa);
            rs = pstmt.executeQuery();

//            Adicionando os registros retornados no result set a lista de telefones:
            while (rs.next()) {
                telefones.add(new Telefone(rs.getInt("id"), rs.getString("numero"), rs.getInt("id_empresa")));
            }
//            Retornando a lista de telefones:
            return telefones;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return telefones;
        } finally {
            Conexao.desconectar(conn);
        }
    }


    //    Método para buscar um telefone pelo id:
    public static Telefone buscarPorId(int id) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM telefone WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

//            Verificando se há retorno:
            if (rs.next()) {
                return new Telefone(rs.getInt("id"), rs.getString("numero"), rs.getInt("id_empresa"));
            }
            return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }


//    Buscar pelo número de telefone em si:
    public static Telefone buscarPorNumero(String numero) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM telefone WHERE numero = ?");
            pstmt.setString(1, numero);
            rs = pstmt.executeQuery();

//            Verificando se há retorno:
            if (rs.next()) {
                return new Telefone(rs.getInt("id"), rs.getString("numero"), rs.getInt("id_empresa"));
            }
            return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }


//    Método para cadastrar um telefone:
    public static boolean cadastrar(Telefone telefone){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO telefone (numero, id_empresa) VALUES (?, ?)");
            pstmt.setString(1, telefone.getNumero());
            pstmt.setInt(2, telefone.getIdEmpresa());

//            Retornando um boolean que já verifica se houve inserção:
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }


//    Método para alterar um telefone:
    public static boolean alterar(Telefone telefone){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("UPDATE telefone SET numero = ?, id_empresa = ? WHERE id = ?");
            pstmt.setString(1, telefone.getNumero());
            pstmt.setInt(2, telefone.getIdEmpresa());
            pstmt.setInt(3, telefone.getId());

//            Retornando um boolean que já verifica se houve atualização:
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }


//    Método para remover por id:
    public static boolean removerPorId(int id){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE FROM telefone WHERE id = ?");
            pstmt.setInt(1, id);
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }


//    Método para remover por número:
    public static boolean removerPorNumero(String numero) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE FROM telefone WHERE numero = ?");
            pstmt.setString(1, numero);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }


//    Método para remover por idEmpresa:
    public static boolean removerPorIdEmpresa(int idEmpresa) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE FROM telefone WHERE id_empresa = ?");
            pstmt.setInt(1, idEmpresa);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}