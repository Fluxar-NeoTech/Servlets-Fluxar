package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.model.Plano;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PlanoDAO implements DAO<Plano> {
//    Declaração de atributos:
    private Connection conn = null;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    @Override
    public Map<Integer, Plano> listar(int pagina, int limite){
//        Declarando variáveis:
        int offset = (pagina - 1) * limite;
        Map<Integer, Plano> planos = new HashMap<>();

//        Conectando ao banco de dados e enviando sql:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM plano ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Adicionando registros do banco de dados a lista de planos:
            while (rs.next()){
                planos.put(rs.getInt("id"), new Plano(rs.getInt("id"), rs.getString("nome"), rs.getInt("tempo"), rs.getDouble("preco")));
            }

//            Retornando a lista de planos:
            return planos;

        }catch (Exception e){
            return planos;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public int contar(){
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM plano");

            if(rs.next()){
                return rs.getInt("contador");
            }
            return -1;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Plano buscarPorId(int id){
//        Conectando ao banco de dados e enviando sql:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM plano WHERE id = ?");
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

    @Override
    public boolean inserir(Plano plano){
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

    @Override
    public boolean alterar(Plano plano){
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

    @Override
    public boolean deletarPorId(int id){
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