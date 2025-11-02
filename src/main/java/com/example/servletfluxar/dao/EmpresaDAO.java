package com.example.servletfluxar.dao;

import com.example.servletfluxar.conexao.Conexao;
import com.example.servletfluxar.dao.interfaces.LoginDAO;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.model.Empresa;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO implements DAO<Empresa>, LoginDAO<Empresa> {
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public List<Empresa> listar(int pagina, int limite) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Empresa> empresas = new ArrayList<>();
        Empresa empresa;

//        Conectando ao banco de dados e enviando comando sql para pegar a tabela da empresa.
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista das empresas.
            while (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());

                empresas.add(empresa);
            }

//            Retornando a lista de empresas cadastradas.
            return empresas;

        } catch (Exception e) {
            e.printStackTrace();
            return empresas;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Empresa> listarPorNome(int pagina, int limite, String nome) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Empresa> empresas = new ArrayList<>();
        Empresa empresa;

//        Conectando ao banco de dados e enviando comando sql para pegar a tabela da empresa.
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE nome  ILIKE ? ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setString(1, "%"+nome+"%");
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista das empresas.
            while (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());

                empresas.add(empresa);
            }

//            Retornando a lista de empresas cadastradas.
            return empresas;

        } catch (Exception e) {
            e.printStackTrace();
            return empresas;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Empresa> listarPorEmail(int pagina, int limite, String email) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Empresa> empresas = new ArrayList<>();
        Empresa empresa;

//        Conectando ao banco de dados e enviando comando sql para pegar a tabela da empresa.
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE email ILIKE ? ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setString(1, "%"+email+"%");
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista das empresas.
            while (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());

                empresas.add(empresa);
            }

//            Retornando a lista de empresas cadastradas.
            return empresas;

        } catch (Exception e) {
            e.printStackTrace();
            return empresas;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Empresa> listarPorCNPJ(int pagina, int limite, String cnpj) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Empresa> empresas = new ArrayList<>();
        Empresa empresa;

//        Conectando ao banco de dados e enviando comando sql para pegar a tabela da empresa.
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE cnpj ILIKE ? ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setString(1, "%"+cnpj+"%");
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista de empresas.
            while (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());

                empresas.add(empresa);
            }

//            Retornando a lista de empresas cadastradas.
            return empresas;

        } catch (Exception e) {
            e.printStackTrace();
            return empresas;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Empresa> listarPorMinDataCadastro(int pagina, int limite, LocalDate data) {
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Empresa> empresas = new ArrayList<>();
        Empresa empresa;

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(
                    "SELECT * FROM empresa WHERE dt_cadastro >= ? ORDER BY dt_cadastro ASC LIMIT ? OFFSET ?"
            );
            pstmt.setDate(1, java.sql.Date.valueOf(data));
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());
                empresas.add(empresa);
            }

            return empresas;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return empresas;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Empresa> listarPorMaxDataCadastro(int pagina, int limite, LocalDate data) {
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Empresa> empresas = new ArrayList<>();
        Empresa empresa;

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(
                    "SELECT * FROM empresa WHERE dt_cadastro <= ? ORDER BY dt_cadastro DESC LIMIT ? OFFSET ?"
            );
            pstmt.setDate(1, java.sql.Date.valueOf(data));
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());
                empresas.add(empresa);
            }

            return empresas;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return empresas;
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
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM empresa");

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

    public int contarPorNome(String nome){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM empresa WHERE nome ILIKE ?");
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
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM empresa WHERE email ILIKE ?");
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

    public int contarPorCNPJ(String cnpj){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM empresa WHERE cnpj ILIKE ?");
            pstmt.setString(1, "%"+cnpj+"%");
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

    public int contarPorMinDataCadastro(LocalDate data){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM empresa WHERE dt_cadastro >= ?");
            pstmt.setDate(1, java.sql.Date.valueOf(data));
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

    public int contarPorMaxDataCadastro(LocalDate data){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM empresa WHERE dt_cadastro <= ?");
            pstmt.setDate(1, java.sql.Date.valueOf(data));
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
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM empresa e " +
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
    public Empresa buscarPorId(int id) {
        Connection conn = null;
        Empresa empresa;

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
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());
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
        Connection conn = null;
        Empresa empresa;

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE cnpj ILIKE ?");
            pstmt.setString(1, cnpj);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());
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
        Connection conn = null;
        Empresa empresa;

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE nome ILIKE ?");
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());

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
        Connection conn = null;
        Empresa empresa;

        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE email ILIKE ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());

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
        Connection conn = null;
        Empresa empresa;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM empresa WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());

//              Verifica se a senha bate com a no banco de dados
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
        Connection conn = null;

        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO empresa (cnpj, nome,email, senha) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, empresa.getCnpj());
            pstmt.setString(2, empresa.getNome());
            pstmt.setString(3, empresa.getEmail());
            pstmt.setString(4, BCrypt.hashpw(empresa.getSenha(), BCrypt.gensalt()));

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
    public boolean alterar(Empresa empresa) {
        Connection conn = null;
        
        try {
            conn = Conexao.conectar();

            // Preparação do comando SQL para atualizar o nome da empresa:
            pstmt = conn.prepareStatement("UPDATE empresa SET nome = ?, email = ? WHERE id = ?");
            pstmt.setString(1, empresa.getNome());
            pstmt.setString(2, empresa.getEmail());
            pstmt.setInt(3,empresa.getId());

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
        Connection conn = null;
        
        try {
            conn = Conexao.conectar();
//          Preparação do comando SQL para atualizar a senha do admin da empresa:
            pstmt = conn.prepareStatement("UPDATE empresa SET senha = ? WHERE email = ?");
            pstmt.setString(1, BCrypt.hashpw(novaSenha, BCrypt.gensalt()));
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