package dao;

import model.Empresa;
import model.Funcionario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    public static List<Funcionario> listarFuncionarios() {
//        Declarando variáveis:
        String sql = "SELECT * FROM funcionario";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        Funcionario funcionario;
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos funcionários:
            while (rs.next()) {
                funcionario = new Funcionario(rs.getInt("id"),rs.getString("nome"),rs.getString("sobrenome"), rs.getString("data_nasc"), rs.getString("telefone"),rs.getString("senha"),rs.getString("email"),rs.getString("cargo"),rs.getInt("id_setor"));
                funcionarios.add(funcionario);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }

        Conexao.desconectar(conn);

//        Retornando os funcionários cadastrados:
        return funcionarios;
    }

    public static List<Funcionario> listarFuncionarioPorEmpresa(int codigo) {
//        Declarando variáveis:
        String sql = "SELECT * FROM funcionario f JOIN setor s ON s.id=f.id_setor JOIN unidade u ON u.id=s.id_unidade JOIN empresa e ON e.id=u.id_empresa WHERE e.id = ? ";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        Funcionario funcionario;
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,codigo);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos funcionários:
            while (rs.next()) {
                funcionario = new Funcionario(rs.getInt("id"),rs.getString("nome"),rs.getString("sobrenome"), rs.getString("data_nasc"), rs.getString("telefone"),rs.getString("senha"),rs.getString("email"),rs.getString("cargo"),rs.getInt("id_setor"));
                funcionarios.add(funcionario);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }

        Conexao.desconectar(conn);

//        Retornando os funcionários cadastrados:
        return funcionarios;
    }

    public static boolean verificarCampo(String campo, String valor){
//        Declaração de variáveis:
        String sql = "SELECT * FROM funcionario WHERE "+campo+" = ?";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,valor);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                Conexao.desconectar(conn);
                return true;
            }

        }catch (SQLException sqle){
            throw new RuntimeException("Ocorreu um erro ao acessar o banco de dados.");
        }

        return false;
    }

    public static Funcionario buscarFuncionario(String campo, String valor){
//        Declaração de variáveis:
        String sql = "SELECT * FROM funcionario WHERE "+campo+" = ?";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,valor);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                return new Funcionario(rs.getInt("id"),rs.getString("nome"),rs.getString("sobrenome"), rs.getString("data_nasc"), rs.getString("telefone"),rs.getString("senha"),rs.getString("email"),rs.getString("cargo"),rs.getInt("id_unidade"));
            }

        }catch (SQLException sqle){
            throw new RuntimeException("Ocorreu um erro ao acessar o banco de dados.");
        }

        return null;
    }

    public static boolean autenticar(String email, String senha){
//        Declaração de variáveis:
        String sql = "SELECT * FROM funcionario WHERE email = ?";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,email);
            rs = pstmt.executeQuery();

//            Verificando se há um retorno com um registro do banco de dados:
            if(rs.next()){
                if(BCrypt.checkpw(senha,rs.getString("senha"))){
                    return true;
                }
            }else{
                return false;
            }

        }catch (SQLException sqle){
            throw new RuntimeException("Ocorreu um erro ao acessar o banco de dados.");
        }

        return false;
    }
}
