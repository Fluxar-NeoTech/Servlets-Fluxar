package dao;

import model.Plano;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {
//    Criando método para listar os planos:
    public static List<Plano> listar(){
//        Declarando variáveis:
        String sql = "SELECT * FROM plano";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        Plano plano;
        List<Plano> planos = new ArrayList<Plano>();

//        Conectando ao banco de dados e enviando sql:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista planos:
            while (rs.next()){
                plano = new Plano(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"),rs.getInt("duracao"));
                planos.add(plano);
            }

        }catch (Exception e){
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }

//        Retornando planos cadastrados:
        return planos;
    }

    public static Plano buscarPlanoPeloId(int id){
//        Declarando variáveis:
        String sql = "SELECT * FROM plano WHERE id = ?";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;
        Plano plano;
        List<Plano> planos = new ArrayList<Plano>();

//        Conectando ao banco de dados e enviando sql:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();

//            Verificando se há um plano com esse id:
            if(rs.next()){

//                Retornando plano encontrado:
                return new Plano(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"),rs.getInt("duracao"));
            }

        }catch (Exception e){
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }
        return null;
    }
}
