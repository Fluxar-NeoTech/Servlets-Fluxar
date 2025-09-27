package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.model.Assinatura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssinaturaDAO {
    public static List<Assinatura> listarAssinaturas(){
//        Declarando variáveis:
        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        List<Assinatura> assinaturas = new ArrayList<>();

//        Tentando conectar ao banco de dados e enviar o select do SQL:
        try{
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM administrador");

//            Pegando as assinaturas do banco e adicionando a lista de assinaturas:
            while (rs.next()){
                assinaturas.add(new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio"), rs.getDate("dt_fim"), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento")));
            }

//            Retornando a lista de assinaturas:
            return assinaturas;

        }catch (Exception e){
            return assinaturas;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static Assinatura buscarPorId(int id){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados e enviando o select no SQL:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM assinatura WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                return new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio"), rs.getDate("dt_fim"), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento"));
            }

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
        return null;
    }
    public static Assinatura buscarPorIdEmpresa(int idEmpresa){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados e enviando select do SQL:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM assinatura WHERE id_empresa = ?");
            pstmt.setInt(1, idEmpresa);
            rs = pstmt.executeQuery();

            if (rs.next()){
                return new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio"), rs.getDate("dt_fim"), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento"));
            }
        }catch (Exception e){
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
        return null;
    }
}
