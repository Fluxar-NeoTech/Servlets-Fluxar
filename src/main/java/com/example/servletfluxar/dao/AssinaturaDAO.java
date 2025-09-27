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

    public static List<Assinatura> buscarPorIdEmpresa(int idEmpresa){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List<Assinatura> assinaturas = new ArrayList<Assinatura>();

//        Conectando ao banco de dados e enviando o select no SQL:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM assinatura WHERE id_empresa = ?");
            pstmt.setInt(1, idEmpresa);
            rs = pstmt.executeQuery();

            while(rs.next()){
                assinaturas.add( new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio"), rs.getDate("dt_fim"), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento")));
            }
            return assinaturas;

        }catch (SQLException sqle){
            sqle.printStackTrace();
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

//        Conectando ao banco de dados e enviando select do SQL:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM assinatura WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()){
                return new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio"), rs.getDate("dt_fim"), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento"));
            }
            return null;

        }catch (Exception e){
            return null;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean cadastrar(Assinatura assinatura){
        //      Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            // Obtenção da conexão com o banco de dados:
            conn = Conexao.conectar();

            // Preparando comando SQL para atualizar a senha do admin da empresa:
            pstmt = conn.prepareStatement("INSERT INTO assinatura VALUES (id_plano, id_empresa, status, dt_inicio, dt_fim) VALUES (?, ?, ?, ?, ?)");
            pstmt.setInt(1,assinatura.getIdPlano());
            pstmt.setInt(2,assinatura.getIdEmpresa());
            pstmt.setString(3, Character.toString(assinatura.getStatus()));
            pstmt.setDate(4, assinatura.getDtInicio());
            pstmt.setDate(5, assinatura.getDtFim());

            // Execução da atualização
            return pstmt.executeUpdate()>0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }

    }
}
