package dao;

import model.Insumo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsumosDAO {
    public static List<Insumo> listarTodos() {
        List<Insumo> insumos = new ArrayList<>();
        String sql = "SELECT * FROM insumos";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                insumos.add(construirInsumo(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insumos;
    }

    public static Insumo construirInsumo(ResultSet rs) throws SQLException {
        Insumo insumo = new Insumo();
        insumo.setCodigo(rs.getInt("codigo"));
        insumo.setNome(rs.getString("nome"));
        insumo.setQtdOcupada(rs.getInt("qtd_ocupada")); // ou getInt, se for inteiro
        insumo.setQtdMax(rs.getInt("qtd_max"));         // idem
        insumo.setUnidadeDeMedida(rs.getString("unidade_de_medida"));
        return insumo;
    }

}
