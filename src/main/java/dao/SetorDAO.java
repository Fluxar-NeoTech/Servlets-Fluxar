package dao;

import model.Setor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {
    public static List<Setor> listarTodos() {
        List<Setor> setores = new ArrayList<>();
        String sql = "SELECT * FROM setor";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                setores.add(construirSetor(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setores;
    }

    public static Setor construirSetor(ResultSet rs) throws SQLException {
        Setor setor = new Setor();
        setor.setCodigo(rs.getInt("codigo"));
        setor.setNome(rs.getString("nome"));
        setor.setCodUnidade(rs.getInt("cod_unidade"));
        setor.setCodUsuarioGestor(rs.getInt("cod_usuario_gestor"));
        setor.setCodUsuarioAnalista(rs.getInt("cod_usuario_analista"));
        return setor;
    }

}
