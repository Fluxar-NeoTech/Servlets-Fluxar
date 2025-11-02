package com.example.servletfluxar.dao;

import com.example.servletfluxar.conexao.Conexao;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.dao.interfaces.DependeEmpresa;
import com.example.servletfluxar.model.Telefone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefoneDAO implements DAO<Telefone>, DependeEmpresa<Telefone> {
//    Declaração de atributos:
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public List<Telefone> listar(int pagina, int limite) {
//        Declaração de variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Telefone> telefones = new ArrayList<>();

//        Conectando ao banco de dados e executando o comando sql para buscar os dados no banco.
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM telefone ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//          Instanciando e adicionando valores retornados no result set ao map telefones 
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

    @Override
    public List<Telefone> listarPorIdEmpresa(int pagina, int limite, int idEmpresa) {
//        Declaração de variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Telefone> telefones = new ArrayList<>();

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM telefone WHERE id_empresa = ? ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, idEmpresa);
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();

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

    @Override
    public int contar(){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM telefone");

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
    public int contarPorIdEmpresa(int idEmpresa){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM telefone WHERE id_empresa = ?");
            pstmt.setInt(1, idEmpresa);
            rs = pstmt.executeQuery();

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
    public Telefone buscarPorId(int id) {
        Connection conn = null;

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM telefone WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

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
    public Telefone buscarPorNumero(String numero) {
//        Declaração de variáveis:
        Connection conn = null;

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM telefone WHERE numero = ?");
            pstmt.setString(1, numero);
            rs = pstmt.executeQuery();

//            Verificando se há retorno:
            if (rs.next()) {
                return new Telefone(rs.getInt("id"), rs.getString("numero"), rs.getInt("id_empresa"));
            }

//          Retorna nulo se não encontrar nenhum telefone com esse número
            return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();

//          Retorna nulo caso ocorra uma excessão.
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean inserir(Telefone telefone){
        Connection conn = null;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO telefone (numero, id_empresa) VALUES (?, ?)");
            pstmt.setString(1, telefone.getNumero());
            pstmt.setInt(2, telefone.getIdEmpresa());

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
    public boolean alterar(Telefone telefone){
        Connection conn = null;

        try{
            conn = Conexao.conectar();

//          alterando os valores do telefone no banco.
            pstmt = conn.prepareStatement("UPDATE telefone SET numero = ?, id_empresa = ? WHERE id = ?");
            pstmt.setString(1, telefone.getNumero());
            pstmt.setInt(2, telefone.getIdEmpresa());
            pstmt.setInt(3, telefone.getId());

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