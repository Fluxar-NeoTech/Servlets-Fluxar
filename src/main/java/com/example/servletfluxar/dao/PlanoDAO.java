package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.model.Plano;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {
//    Criando método para listar os planos:
    public static List<Plano> listar(){
//        Declarando variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List<Plano> planos = new ArrayList<>();

//        Conectando ao banco de dados e enviando sql:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM plano ORDER BY id");
            rs = pstmt.executeQuery();

//            Adicionando registros do banco de dados a lista de planos:
            while (rs.next()){
                planos.add(new Plano(rs.getInt("id"), rs.getString("nome"), rs.getInt("tempo"), rs.getDouble("preco")));
            }

//            Retornando a lista de planos:
            return planos;

        }catch (Exception e){
            return planos;
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

    public static boolean alterar(Plano plano){
//      Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            // Obtenção da conexão com o banco de dados:
            conn = Conexao.conectar();

            // Preparando comando SQL para atualizar a senha do admin da empresa:
            pstmt = conn.prepareStatement("UPDATE plano SET nome = ?, preco = ?, tempo = ? WHERE id = ?");
            pstmt.setString(1,plano.getNome());
            pstmt.setDouble(2,plano.getPreco());
            pstmt.setInt(3, plano.getTempo());
            pstmt.setInt(4, plano.getId());

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
            pstmt = conn.prepareStatement("DELETE FROM plano WHERE id = ?");
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