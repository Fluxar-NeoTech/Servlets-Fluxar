package dao;

import model.Unidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnidadeDAO {
    public static List<Unidade> listarTodas() {
        List<Unidade> unidades = new ArrayList<>();
        String sql = "SELECT * FROM unidade";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                unidades.add(construirUnidade(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unidades;
    }

    public static Unidade construirUnidade(ResultSet rs) throws SQLException {
        Unidade unidade = new Unidade();
        unidade.setCodigo(rs.getInt("codigo"));
        unidade.setPais(rs.getString("pais"));
        unidade.setEstado(rs.getString("estado"));
        unidade.setMunicipio(rs.getString("municipio"));
        unidade.setRua(rs.getString("rua"));
        unidade.setNumero(rs.getString("numero")); // ou rs.getInt("numero") se for numérico
        unidade.setComentarios(rs.getString("comentarios"));
        unidade.setCodEmpresa(rs.getInt("cod_empresa"));
        return unidade;
    }

}
