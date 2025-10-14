package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.dao.interfaces.DependeEmpresa;
import com.example.servletfluxar.dao.interfaces.LoginDAO;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.model.Funcionario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FuncionarioDAO implements DAO<Funcionario>, LoginDAO<Funcionario>, DependeEmpresa<Funcionario> {
//    Declaração de atributos:
    private Connection conn = null;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    @Override
    public Map<Integer, Funcionario> listar(int pagina, int limite) {
//        Declarando variáveis:
        int offset = (pagina - 1) * limite;
        Map<Integer, Funcionario> funcionarios = new HashMap<>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM funcionario ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos funcionários:
            while (rs.next()) {
                funcionarios.put(rs.getInt("id"), new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"), rs.getString("senha"), rs.getString("email"), rs.getString("cargo"), rs.getInt("id_setor")));
            }

//        Retornando os funcionários cadastrados:
            return funcionarios;

        } catch (Exception e) {
            return funcionarios;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Map<Integer, Funcionario> listarPorIdEmpresa(int pagina, int limite, int idEmpresa) {
//        Declarando variáveis:
        int offset = (pagina - 1) * limite;
        Map<Integer, Funcionario> funcionarios = new HashMap<>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(f.*) \"contador\" FROM funcionario f JOIN setor s " +
                    "ON f.id_setor = s.id JOIN unidade u ON s.id_unidade = u.id JOIN empresa e " +
                    "ON u.id_empresa = e.id WHERE e.id = ? ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, idEmpresa);
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos funcionários:
            while (rs.next()) {
                funcionarios.put(rs.getInt("id"), new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"), rs.getString("senha"), rs.getString("email"), rs.getString("cargo"), rs.getInt("id_setor")));
            }

//        Retornando os funcionários cadastrados:
            return funcionarios;

        } catch (Exception e) {
            return funcionarios;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public int contar(){
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM funcionario");

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
        try{
            pstmt = conn.prepareStatement("SELECT COUNT(f.*) \"contador\" FROM funcionario f JOIN setor s " +
                    "ON f.id_setor = s.id JOIN unidade u ON s.id_unidade = u.id JOIN empresa e " +
                    "ON u.id_empresa = e.id WHERE e.id = ?");
            pstmt.setInt(1, idEmpresa);
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM funcionario");

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
        try{
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM funcionario f JOIN setor s ON f.id_setor = s.id" +
                    "JOIN unidade u ON s.id_unidade = u.id JOIN empresa e ON u.id_empresa = e.id " +
                    "JOIN assinatura a ON a.id_empresa = e.id WHERE a.status = ?");
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

    public Map<Integer, Funcionario> listarPorEmpresa(int codigoEmpresa) {
//        Declarando variáveis:
        Map<Integer, Funcionario> funcionarios = new HashMap<>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM funcionario f JOIN setor s ON s.id=f.id_setor JOIN unidade u ON u.id=s.id_unidade JOIN empresa e ON e.id=u.id_empresa WHERE e.id = ? ORDER BY id");
            pstmt.setInt(1,codigoEmpresa);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos funcionários:
            while (rs.next()) {
                funcionarios.put(rs.getInt("id"), new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"), rs.getString("senha"), rs.getString("email"), rs.getString("cargo"), rs.getInt("id_setor")));
            }

//        Retornando os funcionários cadastrados:
            return funcionarios;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return funcionarios;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Funcionario buscarPorId(int id){
//        Declaração de variáveis:
        Funcionario funcionario;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM funcionario WHERE id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobrenome(rs.getString("sobrenome"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setIdSetor(rs.getInt("id_setor"));

                return funcionario;
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
    public Funcionario buscarPorEmail(String email){
//        Declaração de variáveis:
        Funcionario funcionario;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM funcionario WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobrenome(rs.getString("sobrenome"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setIdSetor(rs.getInt("id_setor"));

                return funcionario;
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
    public Funcionario buscarPorNome(String nome){
//        Declaração de variáveis:
        Funcionario funcionario;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT id, nome+\' \'+sobrenome \"nome_completo\", cargo, email, id_setor FROM funcionario WHERE nome_completo LIKE ?");
            pstmt.setString(1,nome);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobrenome(rs.getString("sobrenome"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setIdSetor(rs.getInt("id_setor"));

                return funcionario;
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
    public Funcionario autenticar(String email, String senha){
//        Declaração de variáveis:
        Funcionario funcionario;

//        Tentando conectar ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM funcionario WHERE email = ?");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobrenome(rs.getString("sobrenome"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setIdSetor(rs.getInt("id_setor"));

                if(BCrypt.checkpw(senha, rs.getString("senha"))){
                    return funcionario;
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
    public boolean inserir(Funcionario funcionario){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO funcionario (nome, sobrenome, email, cargo, id_setor,senha) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getSobrenome());
            pstmt.setString(3, funcionario.getEmail());
            pstmt.setString(4, funcionario.getCargo());
            pstmt.setInt(5, funcionario.getIdSetor());
            pstmt.setString(6, funcionario.getSenha());

            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public  boolean alterarSenha(String email, String novaSenha) {
        try {
            // Obtenção da conexão com o banco de dados
            conn = Conexao.conectar();

            // Preparação do comando SQL para atualizar a senha do admin da empresa
            pstmt = conn.prepareStatement("UPDATE funcionario SET senha = ? WHERE email LIKE ?");
            pstmt.setString(1,novaSenha);
            pstmt.setString(2,email);

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
    public  boolean alterar(Funcionario funcionario) {
        try {
            // Obtenção da conexão com o banco de dados
            conn = Conexao.conectar();

            // Preparação do comando SQL para atualizar a senha do admin da empresa
            pstmt = conn.prepareStatement("UPDATE funcionario SET nome = ?, sobrenome = ?, cargo = ?, email = ?, id_setor = ? WHERE id = ?");
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getSobrenome());
            pstmt.setString(3, funcionario.getCargo());
            pstmt.setString(4, funcionario.getEmail());
            pstmt.setInt(5, funcionario.getIdSetor());

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
            pstmt = conn.prepareStatement("DELETE FROM funcionario WHERE id = ?");
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
