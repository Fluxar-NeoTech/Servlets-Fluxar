package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.dao.interfaces.GenericoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Unidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnidadeDAO implements GenericoDAO<Unidade> {
//    Declaração de atributos:
    private Connection conn = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public Map<Integer, Unidade> listar(int pagina, int limite){
//        Declarando variáveis:
        int offset = (pagina - 1) * limite;
        Map<Integer, Unidade> unidades = new HashMap<>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM unidade ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista das unidades:
            while (rs.next()) {
                unidades.put(rs.getInt("id"), new Unidade(rs.getInt("id"), rs.getString("nome"), rs.getString("cnpj"), rs.getString("email"), rs.getInt("id_endereco"), rs.getInt("id_empresa")));
            }

//        Retornando as unidades cadastradas:
            return unidades;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Unidade> listarPorEmpresa(int idEmpresa, int pagina, int limite){
//        Declaração de variáveis:
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
                unidades.add(new Unidade(rs.getInt("id"), rs.getString("nome"), rs.getString("cnpj"), rs.getString("email"), rs.getInt("id_endereco"), rs.getInt("id_empresa")));
            }

//        Retornando as unidades cadastradas por essa empresa:
            return unidades;

        }catch (SQLException sqle){
            return unidades;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Unidade buscarPorId(int id){
//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM unidade WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                return new Unidade(rs.getInt("id"), rs.getString("nome"), rs.getString("cnpj"), rs.getString("email"), rs.getInt("id_endereco"), rs.getInt("id_empresa"));
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
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO unidade (cnpj, nome, email, id_endereco, id_empresa)" +
                    "VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, unidade.getCnpj());
            pstmt.setString(2, unidade.getNome());
            pstmt.setString(3, unidade.getEmail());
            pstmt.setInt(4, unidade.getIdEndereco());
            pstmt.setInt(5, unidade.getIdEmpresa());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean alterar (Unidade unidade){
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("UPDATE unidade SET nome = ?, email = ?, id_endereco = ? WHERE id = ?");
            pstmt.setString(1, unidade.getNome());
            pstmt.setString(2, unidade.getEmail());
            pstmt.setInt(3, unidade.getIdEndereco());
            pstmt.setInt(4, unidade.getId());

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