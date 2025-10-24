package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.dao.interfaces.ComLoginDAO;
import com.example.servletfluxar.dao.interfaces.GenericoDAO;
import com.example.servletfluxar.model.Empresa;
import com.example.servletfluxar.model.Funcionario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpresaDAO implements GenericoDAO<Empresa>, ComLoginDAO<Empresa> {
    private Connection conn = null;
    private PreparedStatement pstmt;
    private ResultSet rs;
    @Override
    public Map<Integer,Empresa> listar(int pagina, int limite) {
//        Declarando variáveis:
        int offset = (pagina - 1) * limite;
        Map<Integer, Empresa> empresas = new HashMap<>();
        Empresa empresa;

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista das empresas:
            while (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("data_cadastro"));

                empresas.put(rs.getInt("id"),empresa);
            }

//            Retornando a lista de empresas cadastradas:
            return empresas;

        } catch (Exception e) {
            e.printStackTrace();
            return empresas;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Empresa buscarPorId(int id) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Empresa empresa;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("data_cadastro"));
                return empresa;
            }
            return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public Empresa buscarPorCNPJ(String cnpj) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Empresa empresa;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE cnpj = ?");
            pstmt.setString(1, cnpj);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("data_cadastro"));
                return empresa;
            }
            return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Empresa buscarPorNome(String nome) {
//        Declaração de variáveis:
        Empresa empresa;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE nome = ?");
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("data_cadastro"));

                return empresa;
            }
            return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Empresa buscarPorEmail(String email) {
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Empresa empresa;

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE email LIKE ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("data_cadastro"));

                return empresa;
            }

            return null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Empresa autenticar(String email, String senha){
//        Declaração de variáveis:
        Empresa empresa;

//        Tentando conectar ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE email = ?");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro"));

                if(BCrypt.checkpw(senha, rs.getString("senha"))){
                    return empresa;
                }
            }
            return null;

        } catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean inserir(Empresa empresa){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO empresa (cnpj, nome,email, senha) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, empresa.getCnpj());
            pstmt.setString(2, empresa.getNome());
            pstmt.setString(3, empresa.getEmail());
            pstmt.setString(4, empresa.getSenha());

            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean alterar(Empresa empresa) {
//        Tentando conectar ao banco de dados:
        try {
            // Obtenção da conexão com o banco de dados:
            conn = Conexao.conectar();

            // Preparação do comando SQL para atualizar o nome da empresa:
            pstmt = conn.prepareStatement("UPDATE empresa SET nome =? WHERE id = ?");
            pstmt.setString(1, empresa.getNome());
            pstmt.setInt(2,empresa.getId());

            // Execução da atualização e retorno:
            return pstmt.executeUpdate()>0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean alterarSenha(String email, String novaSenha) {
//        Tentando conectar ao banco de dados:
        try {
            // Obtenção da conexão com o banco de dados:
            conn = Conexao.conectar();

            // Preparação do comando SQL para atualizar a senha do admin da empresa:
            pstmt = conn.prepareStatement("UPDATE empresa SET senha = ? WHERE email = ?");
            pstmt.setString(1,novaSenha);
            pstmt.setString(2,email);

            // Execução da atualização e retorno:
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
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE FROM empresa WHERE id = ?");
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