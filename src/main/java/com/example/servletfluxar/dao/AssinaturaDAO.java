package com.example.servletfluxar.dao;

import com.example.servletfluxar.connection.Conexao;
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
            rs = stmt.executeQuery("SELECT * FROM assinatura ORDER BY id");

//            Pegando as assinaturas do banco e adicionando a lista de assinaturas:
            while (rs.next()){
                assinaturas.add(new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio").toLocalDate(), rs.getDate("dt_fim").toLocalDate(), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento")));
            }

//            Retornando a lista de assinaturas:
            return assinaturas;

        }catch (Exception e){
            return assinaturas;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public static Assinatura buscarPorIdEmpresa(int idEmpresa){
//        Declaração de variáveis:
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

//        Conectando ao banco de dados e enviando o select no SQL:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM assinatura WHERE id_empresa = ?");
            pstmt.setInt(1, idEmpresa);
            rs = pstmt.executeQuery();

            if(rs.next()){
                return new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio").toLocalDate(), rs.getDate("dt_fim").toLocalDate(), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento"));
            }
            return null;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
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
                return new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio").toLocalDate(), rs.getDate("dt_fim").toLocalDate(), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento"));
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

            // Preparando comando SQL para cadastrar uma assinatura:
            pstmt = conn.prepareStatement("INSERT INTO assinatura (id_plano, id_empresa, status, dt_inicio, dt_fim, forma_pagamento) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1,assinatura.getIdPlano());
            pstmt.setInt(2,assinatura.getIdEmpresa());
            pstmt.setString(3, Character.toString(assinatura.getStatus()));
            pstmt.setDate(4, Date.valueOf(assinatura.getDtInicio()));
            pstmt.setDate(5, Date.valueOf(assinatura.getDtFim()));
            pstmt.setString(6, assinatura.getFormaPagamento());

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
