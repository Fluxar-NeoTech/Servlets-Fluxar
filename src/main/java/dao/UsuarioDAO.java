package dao;

import model.Empresa;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public static boolean autenticar(String email, String senha){
        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha");

                // Verifica a senha digitada com o hash do banco
                if (BCrypt.checkpw(senha, senhaHash)) {
                    return true;
                }else{
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Login inválido
    }

    public static List<Usuario> buscarUsuarioPeloEmail(String email) {
        // Declaração de variáveis
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE email = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            // Conectando ao banco
            conn = Conexao.conectar();

            // Preparando comando SQL
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, email);

            // Executando a consulta
            rs = pstm.executeQuery();

            // Iterando sobre os resultados
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("turno"),
                        rs.getString("senha"),
                        rs.getString("tipo")
                );
                listaUsuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários pelo email: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return listaUsuarios;
    }

    public static boolean alterarSenha(String email, String novaSenha) {
        // Declaração de variáveis
        String sql = "UPDATE usuario SET senha = ? WHERE email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Conexão com o banco de dados
            conn = Conexao.conectar();

            // Preparação do comando SQL
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, novaSenha); // Nova senha criptografada
            stmt.setString(2, email);     // Email do usuário alvo

            // Execução e verificação do resultado
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao alterar a senha do usuário: " + e.getMessage());
            return false;

        } finally {
            // Libera recursos
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }
    }
    public static List<Usuario> listarTodos(){
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(construirUsuario(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public static Usuario construirUsuario(ResultSet rs) throws SQLException {
        Usuario user = new Usuario();
        user.setCodigo(rs.getInt("codigo"));
        user.setNome(rs.getString("nome"));
        user.setEmail(rs.getString("email"));
        user.setCpf(rs.getString("cpf"));
        user.setTurno(rs.getString("turno"));
        user.setTipo(rs.getString("tipo"));
        return user;
    }
}