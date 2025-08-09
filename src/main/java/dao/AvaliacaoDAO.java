package dao;

import model.Avaliacao;
import model.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {
    public static List<Avaliacao> listar() {
//        Declaração de variáveis:
        String sql = "SELECT * FROM avaliacao";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        Avaliacao avaliacao;
        List<Avaliacao> avaliacaos = new ArrayList<Avaliacao>();

//        Conectando ao banco de dados:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()){
                avaliacao = new Avaliacao(rs.getInt("id"), rs.getString("comentarios"),rs.getInt("nota"), rs.getString("email"), rs.getDate("data"));
                avaliacaos.add(avaliacao);
            }

        } catch (SQLException sqle) {
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }

        return avaliacaos;
    }
}
