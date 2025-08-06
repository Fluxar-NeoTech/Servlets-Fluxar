package dao;

import model.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO{
    public static boolean cadastrarEmpresa(Empresa emp) {
        String sql = "INSERT INTO empresa (nome, cnpj, telefone, site, email, status, id_plano, dt_inicio, duracao, forma_pag) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar()){
             PreparedStatement stmt = conn.prepareStatement(sql);

            // Campo 1 - nome (OBRIGATÓRIO)
            stmt.setString(1, emp.getNome());

            // Campo 2 - cnpj (OBRIGATÓRIO)
            stmt.setString(2, emp.getCnpj());

            // Campo 3 - telefone (OPCIONAL)
            if (emp.getTelefone() == null || emp.getTelefone().isEmpty()) {
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setString(3, emp.getTelefone());
            }

            // Campo 4 - site (OPCIONAL)
            if (emp.getSite() == null || emp.getSite().isEmpty()) {
                stmt.setNull(4, Types.VARCHAR);
            } else {
                stmt.setString(4, emp.getSite());
            }

            // Campo 5 - email (OPCIONAL)
            if (emp.getEmail() == null || emp.getEmail().isEmpty()) {
                stmt.setNull(5, Types.VARCHAR);
            } else {
                stmt.setString(5, emp.getEmail());
            }

            // Campo 6 - status (OBRIGATÓRIO)
            stmt.setString(6, emp.getStatus());

            // Campo 7 - id_plano (OPCIONAL)
            if (emp.getIdPlano() == 0) {
                stmt.setNull(7, Types.INTEGER);
            } else {
                stmt.setInt(7, emp.getIdPlano());
            }

            // Campo 8 - dt_inicio (OPCIONAL)
            if (emp.getDtInicio() == null) {
                stmt.setNull(8, Types.DATE);
            } else {
                stmt.setDate(8, emp.getDtInicio());
            }

            // Campo 9 - duracao (OPCIONAL)
            if (emp.getDuracao() == 0) {
                stmt.setNull(9, Types.INTEGER);
            } else {
                stmt.setInt(9, emp.getDuracao());
            }

            // Campo 10 - forma_pag (OPCIONAL)
            if (emp.getFormaPag() == null || emp.getFormaPag().isEmpty()) {
                stmt.setNull(10, Types.VARCHAR);
            } else {
                stmt.setString(10, emp.getFormaPag());
            }

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Empresa buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM empresa WHERE codigo = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return construirEmpresa(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Empresa buscarPorCNPJ(String cnpj) {
        String sql = "SELECT * FROM empresa WHERE cnpj = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return construirEmpresa(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Empresa buscarPorNome(String nome) {
        String sql = "SELECT * FROM empresa WHERE LOWER(nome) LIKE LOWER(?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return construirEmpresa(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Empresa> buscarTodas() {
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT * FROM empresa";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                empresas.add(construirEmpresa(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empresas;
    }

    private static Empresa construirEmpresa(ResultSet rs) throws SQLException {
        Empresa emp = new Empresa();

        emp.setCodigo(rs.getInt("codigo"));
        emp.setNome(rs.getString("nome"));
        emp.setCnpj(rs.getString("cnpj"));
        emp.setTelefone(rs.getString("telefone"));
        emp.setSite(rs.getString("site"));
        emp.setEmail(rs.getString("email"));
        emp.setStatus(rs.getString("status"));
        emp.setIdPlano(rs.getInt("id_plano"));
        emp.setDtInicio(rs.getDate("dt_inicio"));
        emp.setDuracao(rs.getInt("duracao"));
        emp.setFormaPag(rs.getString("forma_pag"));

        return emp;
    }
}