package com.example.servletfluxar.dao;

import com.example.servletfluxar.conexao.Conexao;
import com.example.servletfluxar.dao.interfaces.DependeEmpresa;
import com.example.servletfluxar.dao.interfaces.LoginDAO;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.model.Administrador;
import com.example.servletfluxar.model.Funcionario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements DAO<Funcionario>, LoginDAO<Funcionario>, DependeEmpresa<Funcionario> {
//    Declaração de atributos:
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public List<Funcionario> listar(int pagina, int limite) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Funcionario> funcionarios = new ArrayList<>();

//        Conectando ao banco de dados e enviando sql para ver os dados da tabela funcionario.
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM funcionario ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos funcionários:
            while (rs.next()) {
                funcionarios.add(new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"), rs.getString("email"), rs.getString("senha"), rs.getString("cargo"), rs.getInt("id_setor")));
            }

//        Retornando os funcionários cadastrados:
            return funcionarios;

        } catch (Exception e) {
            return funcionarios;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Funcionario> listarPorNomeCompleto(int pagina, int limite, String nome) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Funcionario> funcionarios = new ArrayList<>();

//        Conectando ao banco de dados e enviando sql para ver os dados da tabela funcionario.
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM funcionario WHERE CONCAT(nome, ' ', sobrenome) LIKE ? ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setString(1, "%"+nome+"%");
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos funcionários:
            while (rs.next()) {
                funcionarios.add(new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"), rs.getString("email"), rs.getString("senha"), rs.getString("cargo"), rs.getInt("id_setor")));
            }

//        Retornando os funcionários cadastrados:
            return funcionarios;

        } catch (Exception e) {
            return funcionarios;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Funcionario> listarPorEmail(int pagina, int limite, String email, int idEmpresa) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Funcionario> funcionarios = new ArrayList<>();

//        Conectando ao banco de dados e enviando sql para ver os dados da tabela funcionario.
        try {
            conn = Conexao.conectar();
            if (idEmpresa==0) {
                pstmt = conn.prepareStatement("SELECT * FROM funcionario WHERE email LIKE ? ORDER BY id LIMIT ? OFFSET ?");
            } else {
                pstmt = conn.prepareStatement("SELECT * FROM funcionario WHERE email LIKE ? ORDER BY id LIMIT ? OFFSET ?");
            }
            pstmt.setString(1, "%"+email+"%");
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos funcionários:
            while (rs.next()) {
                funcionarios.add(new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"), rs.getString("email"), rs.getString("senha"), rs.getString("cargo"), rs.getInt("id_setor")));
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
    public List<Funcionario> listarPorIdEmpresa(int pagina, int limite, int idEmpresa) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT f.* FROM funcionario f JOIN setor s " +
                    "ON f.id_setor = s.id JOIN unidade u ON s.id_unidade = u.id JOIN empresa e " +
                    "ON u.id_empresa = e.id WHERE e.id = ? ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, idEmpresa);
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                funcionarios.add(new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("sobrenome"), rs.getString("email"), rs.getString("senha"), rs.getString("cargo"), rs.getInt("id_setor")));
            }

            return funcionarios;

        } catch (Exception e) {
            e.printStackTrace();
            return funcionarios;
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

    public int contarPorNomeCompleto(String nome){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM funcionario WHERE CONCAT(nome, ' ', sobrenome) LIKE ?");
            pstmt.setString(1, "%"+nome+"%");
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

    public int contarPorEmail(String email){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM funcionario WHERE email LIKE ?");
            pstmt.setString(1, "%"+email+"%");
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
    public int contarPorIdEmpresa(int idEmpresa){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(f.*) \"contador\" FROM funcionario f JOIN setor s " +
                    "ON f.id_setor = s.id JOIN unidade u ON s.id_unidade = u.id JOIN empresa e " +
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
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM funcionario f JOIN setor s ON f.id_setor = s.id " +
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

    @Override
    public Funcionario buscarPorId(int id){
//        Declaração de variáveis:
        Connection conn = null;
        Funcionario funcionario;

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
        Connection conn = null;
        Funcionario funcionario;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM funcionario WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

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
        Connection conn = null;
        Funcionario funcionario;

//      concatenando as colunas nome e sobrenome do banco de dados, para achar o nome completo quando o usuario digitar.    
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
        Connection conn = null;
        Funcionario funcionario;

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
        Connection conn = null;

//        Conectando ao banco de dados e inserindo informação de um novo funcionario
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO funcionario (nome, sobrenome, email, cargo, id_setor,senha) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getSobrenome());
            pstmt.setString(3, funcionario.getEmail());
            pstmt.setString(4, funcionario.getCargo());
            pstmt.setInt(5, funcionario.getIdSetor());
            pstmt.setString(6, BCrypt.hashpw(funcionario.getSenha(), BCrypt.gensalt()));

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
    public  boolean alterarSenha(String email, String novaSenha) {
        Connection conn = null;
        try {
            conn = Conexao.conectar();

            // Preparação do comando SQL para atualizar a senha do admin da empresa
            pstmt = conn.prepareStatement("UPDATE funcionario SET senha = ? WHERE email LIKE ?");
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
    public  boolean alterar(Funcionario funcionario) {
        Connection conn = null;
        try {
            conn = Conexao.conectar();

//          Preparação do comando SQL para atualizar informações sobre o funcionario.
            pstmt = conn.prepareStatement("UPDATE funcionario SET nome = ?, sobrenome = ?, cargo = ?, email = ?, id_setor = ? WHERE id = ?");
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getSobrenome());
            pstmt.setString(3, funcionario.getCargo());
            pstmt.setString(4, funcionario.getEmail());
            pstmt.setInt(5, funcionario.getIdSetor());
            pstmt.setInt(6, funcionario.getId());

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
        PreparedStatement pstmt;

        try{
            conn = Conexao.conectar();

            //Excluindo um funcionario com base em seu id no banco.
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
