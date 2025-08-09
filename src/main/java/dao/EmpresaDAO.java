package dao;

import model.Empresa;
import model.Plano;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {
    public static List<Empresa> listar(){
        //        Declarando variáveis:
        String sql = "SELECT * FROM empresa";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        Empresa empresa;
        List<Empresa> empresas = new ArrayList<Empresa>();

//        Conectando ao banco de dados e enviando sql:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista das empresas:
            while (rs.next()){
                empresa = new Empresa(rs.getInt("id"),rs.getString("status"),rs.getDate("dt_inicio"),rs.getString("cnpj"),rs.getString("senha"),rs.getString("nome"),rs.getString("email"),rs.getInt("id_plano"));
                empresas.add(empresa);
            }

        }catch (Exception e){
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }

//        Retornando as empresas cadastradas:
        return empresas;
    }

    public static boolean verificarCampo(String campo, String valor){
//        Declaração de variáveis:
        String sql = "SELECT * FROM empresa WHERE "+campo+" = ?";
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
                return true;
            }

        }catch (SQLException sqle){
            throw new RuntimeException("Ocorreu um erro ao acessar o banco de dados.");
        }
        return false;
    }

    public static Empresa buscarEmpresa(String campo, String valor){
//        Declaração de variáveis:
        String sql = "SELECT * FROM empresa WHERE "+campo+" = ?";
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
                return new Empresa(rs.getInt("id"),rs.getString("status"),rs.getDate("dt_inicio"),rs.getString("cnpj"),rs.getString("senha"),rs.getString("nome"),rs.getString("email"),rs.getInt("id_plano"));
            }

        }catch (SQLException sqle){
            throw new RuntimeException("Ocorreu um erro ao acessar o banco de dados.");
        }
        return null;
    }

    public static boolean autenticar(String email, String senha){
//        Declaração de variáveis:
        String sql = "SELECT * FROM empresa WHERE email = ?";
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
                    Conexao.desconectar(conn);
                    return true;
                }
            }else{
                Conexao.desconectar(conn);
                return false;
            }

        }catch (SQLException sqle){
            throw new RuntimeException("Ocorreu um erro ao acessar o banco de dados.");
        }

        return false;
    }

    public static  boolean cadastrarEmpresa(Empresa empresa){
        //        Declaração de variáveis:
        String sql = "INSERT INTO empresa (dt_inicio, cnpj, senha, nome, email, id_plano) VALUES (?, ?, ?, ?,?, ?)";
        Connection conn;
        PreparedStatement pstmt;
        int linhasAfetadas;

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1,empresa.getDt_inicio());
            pstmt.setString(2,empresa.getCnpj());
            pstmt.setString(3,empresa.getSenha());
            pstmt.setString(4,empresa.getNome());
            pstmt.setString(5,empresa.getEmail());
            pstmt.setInt(6,empresa.getId_plano());

            linhasAfetadas = pstmt.executeUpdate();

            return linhasAfetadas>0;

        }catch (SQLException sqle){
            throw new RuntimeException("Ocorreu um erro ao acessar o banco de dados.");
        }
    }

    public static boolean alterarSenha(String email, String novaSenha) {
        // Declaração de variáveis
        String sql;
        Connection conn;
        PreparedStatement pstm;
        int linhas;

        // Comando SQL para atualizar a senha do admin da empresa
        sql = "UPDATE empresa SET senha = '"+novaSenha+"' WHERE email = '"+email+"'";

        try {
            // Obtenção da conexão com o banco de dados
            conn = Conexao.conectar();

            // Preparação do comando SQL
            pstm = conn.prepareStatement(sql);

            // Execução da atualização
            linhas = pstm.executeUpdate();

            return linhas>0;

        } catch (SQLException e) {
            System.err.println("Erro ao alterar a senha do usuário: " + e.getMessage());
            return false;
        }
    }
}