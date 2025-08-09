package dao;

import model.Funcionario;
import model.Unidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnidadeDAO {
    public static List<Unidade> listar(){
//        Declarando variáveis:
        String sql = "SELECT * FROM unidade";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        Unidade unidade;
        List<Unidade> unidades = new ArrayList<Unidade>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista das unidades:
            while (rs.next()) {
                unidade = new Unidade(rs.getInt("id"), rs.getString("nome"),rs.getInt("numero"),rs.getString("cep"),rs.getString("referencia"),rs.getString("descricao"),rs.getInt("id_empresa"));
                unidades.add(unidade);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }

//        Retornando as unidades cadastradas:
        return unidades;
    }

    public static List<Unidade> listarPorEmpresa(int codigo){
//        Declarando variáveis:
        String sql = "SELECT * FROM unidade WHERE id_empresa = ?";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        Unidade unidade;
        List<Unidade> unidades = new ArrayList<Unidade>();

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,codigo);
            rs = pstmt.executeQuery();

//            Coletando dados:
            while (rs.next()) {
                unidade = new Unidade(rs.getInt("id"), rs.getString("nome"),rs.getInt("numero"),rs.getString("cep"),rs.getString("referencia"),rs.getString("descricao"),rs.getInt("id_empresa"));
                unidades.add(unidade);
            }

        }catch (SQLException sqle){
            throw new RuntimeException("Erro ao conectar no banco de dados");
        }

        return unidades;
    }
}
