package dao;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    public static boolean cadastrar(Administrador adm) {
        String sql = "INSERT INTO administradores (nome, sobrenome, email, senha, cod_empresa) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, adm.getNome());
            pstm.setString(2, adm.getSobrenome());
            pstm.setString(3, adm.getEmail());
            pstm.setString(4, adm.getSenha());
            pstm.setInt(5, adm.getCodEmpresa());
            pstm.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean autenticar(String email, String senha) {
        String sql = "SELECT * FROM administradores WHERE email = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                if(BCrypt.checkpw(senha,rs.getString("senha"))){
                    return true;
                }else {
                    return false;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar administrador: " + e.getMessage());
        }

        return false;
    }

    public static boolean existeEmail(String email) {
        String sql = "SELECT * FROM administradores WHERE email = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, email);
            return pstm.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar e-mail: " + e.getMessage());
        }
    }

    public static Administrador buscarPorEmail(String email) {
        String sql = "SELECT codigo, nome, sobrenome, email, senha, cod_empresa FROM administradores WHERE email = ?";
        ResultSet rs;

        try (Connection conn = Conexao.conectar();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, email);
            rs = pstm.executeQuery();

            if (rs.next()) {
                return new Administrador(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("cod_empresa")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar e-mail: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Administrador> listarPorEmpresa(int codEmpresa) {
        String sql = "SELECT * FROM administradores WHERE cod_empresa = ?";
        List<Administrador> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codEmpresa);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Administrador adm = new Administrador();
                adm.setCodigo(rs.getInt("codigo"));
                adm.setNome(rs.getString("nome"));
                adm.setSobrenome(rs.getString("sobrenome"));
                adm.setEmail(rs.getString("email"));
                adm.setSenha(rs.getString("senha"));
                adm.setCodEmpresa(rs.getInt("cod_empresa"));
                lista.add(adm);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar administradores: " + e.getMessage());
        }

        return lista;
    }
    public static List<Administrador> listarTodos() {
        String sql = "SELECT * FROM administradores";
        List<Administrador> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Administrador adm = new Administrador();
                adm.setCodigo(rs.getInt("codigo"));
                adm.setNome(rs.getString("nome"));
                adm.setSobrenome(rs.getString("sobrenome"));
                adm.setEmail(rs.getString("email"));
                adm.setSenha(rs.getString("senha"));
                adm.setCodEmpresa(rs.getInt("cod_empresa"));
                lista.add(adm);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar administradores: " + e.getMessage());
        }

        return lista;
    }

    public static boolean alterarSenha(String email, String novaSenha) {
        // Declaração de variáveis
        String sql;
        Connection conn;
        PreparedStatement pstm;

        // Comando SQL para atualizar a senha do admin
        sql = "UPDATE administradores SET senha = '"+novaSenha+"' WHERE email = '"+email+"'";

        try {
            // Obtenção da conexão com o banco de dados
            conn = Conexao.conectar();

            // Preparação do comando SQL
            pstm = conn.prepareStatement(sql);

            // Execução da atualização
            int linhas = pstm.executeUpdate();

            return linhas>0;

        } catch (SQLException e) {
            System.err.println("Erro ao alterar a senha do usuário: " + e.getMessage());
            return false;
        }
    }
}
