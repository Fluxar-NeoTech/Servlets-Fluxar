package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.dao.interfaces.GenericoDAO;
import com.example.servletfluxar.model.Telefone;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelefoneDAO implements GenericoDAO<Telefone> {
//    Declaração de atributos:
    private Connection conn = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public Map<Integer, Telefone> listar(int pagina, int limite) {
//        Declaração de variáveis:
        int offset = (pagina - 1) * limite;
        Map<Integer, Telefone> telefones = new HashMap<>();

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM telefone ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                telefones.put(rs.getInt("id"), new Telefone(rs.getInt("id"), rs.getString("numero"), rs.getInt("id_empresa")));
            }
            return telefones;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return telefones;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    public List<Telefone> listarPorIdEmpresa(int idEmpresa) {
//        Declaração de variáveis:
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

    @Override
    public Telefone buscarPorId(int id) {
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

    @Override
    public boolean inserir(Telefone telefone){
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

    @Override
    public boolean alterar(Telefone telefone){
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


    @Override
    public boolean deletarPorId(int id){
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
}