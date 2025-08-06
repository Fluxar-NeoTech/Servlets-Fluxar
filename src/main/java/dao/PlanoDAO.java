package dao;

import model.Plano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {
    private Connection conn = Conexao.conectar();
    private String sql;
    private PreparedStatement pstm;
    private ResultSet rs;

    public List<Plano> listar(){
        List<Plano> planos = new ArrayList<>();
        try{
//            Preparando e executando código sql:
            sql = "SELECT * FROM planos";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

//            Pegando dados do rs e adicionando a ArrayList:
            while (rs.next()){
                Plano plano = new Plano(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getBoolean("alertas"),
                        rs.getBoolean("saida_entradas"),
                        rs.getInt("qtd_redirecionar"),
                        rs.getBoolean("relatorio_pdf"),
                        rs.getBoolean("relatorio_excel"),
                        rs.getInt("tempo_suporte_h"),
                        rs.getBigDecimal("valor_mensal"),
                        rs.getBigDecimal("valor_anual"),
                        rs.getInt("qtd_usuarios")
                );
                planos.add(plano);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return planos;
    }

    public Plano verPlano(String nomePlano){
        Plano plano = null;
        try{
//            Preparando e executando código sql:
            sql = "SELECT * FROM planos WHERE nome='"+nomePlano+"'";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

//            Pegando dados do rs e adicionando a ArrayList:
            if (rs.next()){
                plano = new Plano(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getBoolean("alertas"),
                        rs.getBoolean("saida_entradas"),
                        rs.getInt("qtd_redirecionar"),
                        rs.getBoolean("relatorio_pdf"),
                        rs.getBoolean("relatorio_excel"),
                        rs.getInt("tempo_suporte_h"),
                        rs.getBigDecimal("valor_mensal"),
                        rs.getBigDecimal("valor_anual"),
                        rs.getInt("qtd_usuarios")
                );
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return plano;
    }
}
