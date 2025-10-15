package com.example.servletfluxar.dao;

import com.example.servletfluxar.conexao.Conexao;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.model.Endereco;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EnderecoDAO implements DAO<Endereco> {
//    Declarando atributos:
    private Connection conn = null;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    @Override
    public Map<Integer, Endereco> listar(int pagina, int limite) {
//        Declarando variáveis:
        int offset = (pagina - 1) * limite;
        Map<Integer, Endereco> enderecos = new HashMap<>();

//        Conectando ao banco de dados e enviando comando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM endereco ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos endereços:
            while (rs.next()) {
                enderecos.put(rs.getInt("id"), new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getInt("numero"), rs.getString("complemento")));
            }

//            Returnando o map com os endereços:
            return enderecos;

        } catch (Exception e) {
            return enderecos;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public int contar(){
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM endereco");

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
    public Endereco buscarPorId(int id){
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

    @Override
    public boolean inserir(Endereco endereco){
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

    @Override
    public boolean alterar(Endereco endereco){
//        Tentando conectar ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("UPDATE empresa SET cep = ?, numero = ?, complemento = ? WHERE id = ?");
            pstmt.setString(1, endereco.getCep());
            pstmt.setInt(2, endereco.getNumero());
            pstmt.setString(3, endereco.getComplemento());
            pstmt.setInt(4, endereco.getId());

//            Retornando se houve um retono na alteração:
            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle){
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
