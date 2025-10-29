package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.dao.interfaces.ComLoginDAO;
import com.example.servletfluxar.dao.interfaces.GenericoDAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Empresa;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdministradorDAO implements GenericoDAO<Administrador>, ComLoginDAO<Administrador> {
//    Declaração de atributos.
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public Map<Integer, Administrador> listar(int pagina, int limite) {
//        Declarando variáveis
        int offset = (pagina - 1) * limite;
        Administrador administrador;
        Map<Integer, Administrador> administradores = new HashMap();

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

//                Adicionando o administrador ao map de administradores
                administradores.put(rs.getInt("id"), administrador);
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
    public Administrador buscarPorId(int id){
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
        try{
            conn = Conexao.conectar();

//          Inserindo no banco um novo adminstrador.
            pstmt = conn.prepareStatement("INSERT INTO administrador (nome, sobrenome, email, senha) VALUES (?, ?, ?, ?)");
            pstmt.setString(1,administrador.getNome());
            pstmt.setString(2,administrador.getSobrenome());
            pstmt.setString(3,administrador.getEmail());
            pstmt.setString(4,administrador.getSenha());

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
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean alterarSenha(String email, String novaSenha) {
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
        try{
            conn = Conexao.conectar();

//          Deleta um adminstrador.
            pstmt = conn.prepareStatement("DELETE FROM administrador WHERE id = ?");
            pstmt.setInt(1, id);
            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
