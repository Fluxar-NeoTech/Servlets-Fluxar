package com.example.servletfluxar.dao;

import com.example.servletfluxar.connection.Conexao;
import com.example.servletfluxar.model.Unidade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnidadeDAO {
    public static List<Unidade> listarUnidades(){
//        Declarando variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List<Unidade> unidades = new ArrayList<>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM unidade ORDER BY id");
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista das unidades:
            while (rs.next()) {
                unidades.add(new Unidade(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), rs.getInt("id_endereco"), rs.getInt("id_empresa")));
            }

//        Retornando as unidades cadastradas:
            return unidades;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados");
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static List<Unidade> listarPorEmpresa(int idEmpresa){
//        Declarando variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List<Unidade> unidades = new ArrayList<>();

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM unidade WHERE id_empresa = ?");
            pstmt.setInt(1,idEmpresa);
            rs = pstmt.executeQuery();

//            Coletando dados:
            while (rs.next()) {
                unidades.add(new Unidade(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), rs.getInt("id_endereco"), rs.getInt("id_empresa")));
            }

//        Retornando as unidades cadastradas por essa empresa:
            return unidades;

        }catch (SQLException sqle){
            return unidades;
        }finally {
            Conexao.desconectar(conn);
        }
    }
}
