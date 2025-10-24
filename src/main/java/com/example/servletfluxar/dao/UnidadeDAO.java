package com.example.servletfluxar.dao;

import com.example.servletfluxar.conexao.Conexao;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.dao.interfaces.DependeEmpresa;
import com.example.servletfluxar.model.Unidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnidadeDAO implements DAO<Unidade>, DependeEmpresa<Unidade> {
//    Declaração de atributos:
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public List<Unidade> listar(int pagina, int limite){
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Unidade> unidades = new ArrayList<>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM unidade ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista das unidades:
            while (rs.next()) {
                unidades.add(new Unidade(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("cnpj"), rs.getString("email"), rs.getInt("id_empresa"),
                        rs.getString("endereco_cep"), rs.getInt("endereco_numero"),
                        rs.getString("endereco_complemento")));
            }

//        Retornando as unidades cadastradas:
            return unidades;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;

        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public List<Unidade> listarPorIdEmpresa(int pagina, int limite, int idEmpresa){
//        Declaração de variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Unidade> unidades = new ArrayList<>();

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM unidade WHERE id_empresa = ? ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, idEmpresa);
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();

//            Coletando dados:
            while (rs.next()) {
                unidades.add(new Unidade(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("cnpj"), rs.getString("email"), rs.getInt("id_empresa"),
                        rs.getString("endereco_cep"), rs.getInt("endereco_numero"),
                        rs.getString("endereco_complemento")));
            }

//        Retornando as unidades cadastradas por essa empresa:
            return unidades;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return unidades;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public int contar(){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM unidade");

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
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM unidade u JOIN empresa e " +
                    "ON u.id_empresa = e.id WHERE e.id = ?");
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
    public int contarPorEmpresaStatus(char status){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM unidade u JOIN empresa e " +
                    "ON u.id_empresa = e.id JOIN assinatura a ON a.id_empresa = e.id WHERE a.status = ?");
            pstmt.setString(1, String.valueOf(status));
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
    public Unidade buscarPorId(int id){
        Connection conn = null;
//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM unidade WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                return new Unidade(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("cnpj"), rs.getString("email"), rs.getInt("id_empresa"),
                        rs.getString("endereco_cep"), rs.getInt("endereco_numero"),
                        rs.getString("endereco_complemento"));
            }
            return null;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean inserir (Unidade unidade){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO unidade (cnpj, nome, email, id_empresa, " +
                    "endereco_cep, endereco_numero, endereco_complemento)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, unidade.getCnpj());
            pstmt.setString(2, unidade.getNome());
            pstmt.setString(3, unidade.getEmail());
            pstmt.setInt(4, unidade.getIdEmpresa());
            pstmt.setString(5, unidade.getCep());
            pstmt.setInt(6, unidade.getNumero());
            pstmt.setString(7, unidade.getComplemento());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean alterar (Unidade unidade){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("UPDATE unidade SET nome = ?, email = ?, endereco_cep = ?, endereco_numero = ?, " +
                    "endereco_complemento = ? WHERE id = ?");
            pstmt.setString(1, unidade.getNome());
            pstmt.setString(2, unidade.getEmail());
            pstmt.setString(3, unidade.getCep());
            pstmt.setInt(4, unidade.getNumero());
            pstmt.setString(5, unidade.getComplemento());
            pstmt.setInt(6, unidade.getId());

            return pstmt.executeUpdate()>0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean deletarPorId (int id){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE FROM unidade WHERE id = ?");
            pstmt.setInt(1, id);

            return pstmt.executeUpdate()>0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}