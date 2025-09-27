package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.model.Plano;
import com.example.servletfluxar.model.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {
    public static List<Setor> listar() {
//        Declarando variáveis:
        String sql = "SELECT * FROM setor";
        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        List<Setor> setores= new ArrayList<>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

//            Criando objetos e adicionando a lista dos setores:
            while (rs.next()) {
                setores.add(new Setor(rs.getInt("id"),rs.getString("nome"), rs.getInt("id_unidade")));
            }

//            Retornando a lista de setores cadastrados:
            return setores;

        } catch (Exception e) {
            return setores;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static Plano buscarPeloId(int id){
//        Declarando variáveis:
        String sql = "SELECT * FROM plano WHERE id = ?";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        Plano plano;
        List<Plano> planos = new ArrayList<>();

//        Conectando ao banco de dados e enviando sql:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();

//            Verificando se há um plano com esse id:
            if(rs.next()){
//                Retornando plano encontrado:
                return new Plano(rs.getInt("id"), rs.getString("nome"), rs.getInt("tempo"), rs.getDouble("preco"));
            }

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
        return null;
    }

    public static boolean cadastrar(Plano plano){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

//        Conectando ao banco de dados e dando o insert:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO plano (nome, preco, tempo) VALUES (?, ?, ?)");
            pstmt.setString(1, plano.getNome());
            pstmt.setDouble(2, plano.getPreco());
            pstmt.setInt(3, plano.getTempo());

            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean alterarNome(Setor setor){
//      Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            // Obtenção da conexão com o banco de dados:
            conn = Conexao.conectar();

            // Preparando comando SQL para atualizar a senha do admin da empresa:
            pstmt = conn.prepareStatement("UPDATE setor SET nome = ? WHERE id = ?");
            pstmt.setString(1,setor.getNome());
            pstmt.setInt(2, setor.getId());

            // Execução da atualização
            return pstmt.executeUpdate()>0;

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
            pstmt = conn.prepareStatement("DELETE * FROM setor WHERE id = ?");
            pstmt.setInt(1, id);
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }
}