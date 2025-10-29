package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.dao.interfaces.GenericoDAO;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnderecoDAO implements GenericoDAO<Endereco> {
//    Declarando atributos:
    private Connection conn = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public Map<Integer, Endereco> listar(int pagina, int limite) {
//        Declarando variáveis:
        int offset = (pagina - 1) * limite;
        Map<Integer, Endereco> enderecos = new HashMap<>();

//        Conectando ao banco de dados e enviando comando sql para ver os dados da tabela endereço.
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
    public Endereco buscarPorId(int id){
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
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO endereco (cep, numero, complemento) VALUES (?, ?, ?)");
            pstmt.setString(1,endereco.getCep());
            pstmt.setInt(2,endereco.getNumero());
            pstmt.setString(3,endereco.getComplemento());

//          retorna um boolean caso o número de linhas afetadas seja maior que 0, se for, a ação foi feita.
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
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("UPDATE empresa SET cep = ?, numero = ?, complemento = ? WHERE id = ?");
            pstmt.setString(1, endereco.getCep());
            pstmt.setInt(2, endereco.getNumero());
            pstmt.setString(3, endereco.getComplemento());
            pstmt.setInt(4, endereco.getId());

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
        Connection conn = null;
        PreparedStatement pstmt;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE * FROM endereco WHERE id = ?");
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
