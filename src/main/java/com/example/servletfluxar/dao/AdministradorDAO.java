package com.example.servletfluxar.dao;

import com.example.servletfluxar.conexao.Conexao;
import com.example.servletfluxar.dao.interfaces.LoginDAO;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.model.Administrador;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO implements DAO<Administrador>, LoginDAO<Administrador> {
//    Declaração de atributos:
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public List<Administrador> listar(int pagina, int limite) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        Administrador administrador;
        List<Administrador> administradores = new ArrayList<>();

//        Conectando ao banco de dados e enviando comando sql para selecionar a tabela administrador ordernada pelo limite e por onde vai começar a buscar.
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM administrador ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos administradores.
            while (rs.next()) {
                administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setSobrenome(rs.getString("sobrenome"));
                administrador.setEmail(rs.getString("email"));

//                Adicionando o administrador a lista de administradores:
                administradores.add(administrador);
            }

//            Retornando o map de administrador.
            return administradores;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return administradores;
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
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM administrador");

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
    public Administrador buscarPorId(int id){
//        Declaração de variáveis:
        Connection conn = null;
        Administrador administrador;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM administrador WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados.
            if(rs.next()){
                administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setSobrenome(rs.getString("sobrenome"));
                administrador.setEmail(rs.getString("email"));

                return administrador;
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
    public Administrador buscarPorNome(String nome){
//        Declaração de variáveis:
        Connection conn = null;
        Administrador administrador;

        try{
            conn = Conexao.conectar();

//          Busca o administrador pelo nome, concatenando as tabelas nome e sobrenome para achar quando o usuario digitar o nome completo.
            pstmt = conn.prepareStatement("SELECT id, nome+\' \'+sobrenome \"nome_completo\", email, senha FROM administrador WHERE nome_completo LIKE ?");
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();

            if(rs.next()){
                administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setSobrenome(rs.getString("sobrenome"));
                administrador.setEmail(rs.getString("email"));

                return administrador;
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
    public Administrador buscarPorEmail(String email){
//        Declaração de variáveis:
        Connection conn = null;
        Administrador administrador;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM administrador WHERE email LIKE ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

//          Se achar o administrador, retorna os dados dele.
            if(rs.next()){
                administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setSobrenome(rs.getString("sobrenome"));
                administrador.setEmail(rs.getString("email"));

                return administrador;
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
    public Administrador autenticar(String email, String senha){
//        Declaração de variáveis:
        Connection conn = null;
        Administrador administrador = null;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM administrador WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if(rs.next()){
                administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setSobrenome(rs.getString("sobrenome"));
                administrador.setEmail(rs.getString("email"));

//              Verificando se a senha do usuario concede com a do banco de dados.
                if (BCrypt.checkpw(senha, rs.getString("senha"))){
                    return administrador;
                }
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
    public boolean inserir (Administrador administrador) {
        Connection conn = null;
//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO administrador (nome, sobrenome, email, senha) VALUES (?, ?, ?, ?)");
            pstmt.setString(1,administrador.getNome());
            pstmt.setString(2,administrador.getSobrenome());
            pstmt.setString(3,administrador.getEmail());
            pstmt.setString(4, BCrypt.hashpw(administrador.getSenha(), BCrypt.gensalt()));

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
    public boolean alterar(Administrador administrador) {
        Connection conn = null;
        try {
            conn = Conexao.conectar();

//          Preparação do comando SQL para atualizar os dados do adminstrador da empresa.
            pstmt = conn.prepareStatement("UPDATE administrador SET nome = ?, sobrenome = ?, email = ? WHERE id = ?");
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getSobrenome());
            pstmt.setString(3, administrador.getEmail());
            pstmt.setInt(4, administrador.getId());

            return pstmt.executeUpdate()>0;

        } catch (SQLException sqle) {
            System.out.println("Erro");
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean alterarSenha(String email, String novaSenha) {
        Connection conn = null;
        
        try {
            conn = Conexao.conectar();

//          Preparação do comando SQL para atualizar a senha do adminstrador da empresa.
            pstmt = conn.prepareStatement("UPDATE administrador SET senha = ? WHERE email = ?");
            pstmt.setString(1,novaSenha);
            pstmt.setString(2,email);

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
        Connection conn = null;
        
        try{
            conn = Conexao.conectar();

//          Deleta um adminstrador.
            pstmt = conn.prepareStatement("DELETE FROM administrador WHERE id = ?");
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
