package com.example.servletfluxar.dao;

import com.example.servletfluxar.conexao.Conexao;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.model.Assinatura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssinaturaDAO implements DAO<Assinatura>{
//    Declaração de atributos:
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public List<Assinatura> listar(int pagina, int limite) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Assinatura> assinaturas = new ArrayList<>();

//        Tentando conectar ao banco de dados e enviar o select do SQL para selecionar a(s) assinatura(s).
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM assinatura ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Pegando as assinaturas do banco e adicionando a lista de assinaturas.
            while (rs.next()) {
                assinaturas.add(new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio").toLocalDate(), rs.getDate("dt_fim").toLocalDate(), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento")));
            }

//            Retornando a lista de assinaturas.
            return assinaturas;

        } catch (Exception e) {
            e.printStackTrace();
            return assinaturas;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public int contar(){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM assinatura");

            if(rs.next()){
                return rs.getInt("contador");
            }
            return -1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }


    }

    public int contarPorStatus(char status){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM assinatura WHERE status = ?");
            pstmt.setString(1, String.valueOf(status));
            rs = pstmt.executeQuery();

            if(rs.next()){
                return rs.getInt("contador");
            }
            return -1;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public Assinatura buscarPorIdEmpresa(int idEmpresa) {
        Connection conn = null;
        
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM assinatura WHERE id_empresa = ?");
            pstmt.setInt(1, idEmpresa);
            rs = pstmt.executeQuery();

//          Retornando a tabela assinatura.
            if (rs.next()) {
                return new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio").toLocalDate(), rs.getDate("dt_fim").toLocalDate(), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento"));
            }
            return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Assinatura buscarPorId(int id) {
        Connection conn = null;
        
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM assinatura WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Assinatura(rs.getInt("id"), rs.getDate("dt_inicio").toLocalDate(), rs.getDate("dt_fim").toLocalDate(), rs.getString("status").charAt(0), rs.getInt("id_empresa"), rs.getInt("id_plano"), rs.getString("forma_pagamento"));
            }
            return null;

        } catch (Exception e) {
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean inserir (Assinatura assinatura) {
        Connection conn = null;

        try {
            conn = Conexao.conectar();

            // Preparando comando SQL para cadastrar uma assinatura:
            pstmt = conn.prepareStatement("INSERT INTO assinatura (id_plano, id_empresa, status, dt_inicio, dt_fim, forma_pagamento) VALUES (?, ?, ?, to_date(?,\"yyyy-mm-dd\"), to_date(?,\"yyyy-mm-dd\"), ?)");
            pstmt.setInt(1, assinatura.getIdPlano());
            pstmt.setInt(2, assinatura.getIdEmpresa());
            pstmt.setString(3, Character.toString(assinatura.getStatus()));
            pstmt.setDate(4, Date.valueOf(assinatura.getDtInicio()));
            pstmt.setDate(5, Date.valueOf(assinatura.getDtFim()));
            pstmt.setString(6, assinatura.getFormaPagamento());

//          retorna um boolean caso o número de linhas afetadas seja maior que 0, se for, a ação foi feita.
            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean alterar(Assinatura assinatura) {
        Connection conn = null;
        try {
            conn = Conexao.conectar();

//          Preparação do comando SQL para alterar as informações do plano.
            pstmt = conn.prepareStatement("UPDATE assinatura SET id_plano = ?, dt_inicio = ?, dt_fim = ?, forma_pagamento = ?, " +
                    "status = ? WHERE id = ?");
            pstmt.setInt(1, assinatura.getIdPlano());
            pstmt.setDate(2, Date.valueOf(assinatura.getDtInicio()));
            pstmt.setDate(3, Date.valueOf(assinatura.getDtFim()));
            pstmt.setString(4, assinatura.getFormaPagamento());
            pstmt.setString(5, String.valueOf(assinatura.getStatus()));
            pstmt.setInt(5, assinatura.getId());


            return pstmt.executeUpdate()>0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean deletarPorId(int id){
        Connection conn = null;
        
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE FROM assinatura WHERE id = ?");
            pstmt.setInt(1, id);

//            Retornando se houve um registro que foi deletado:
            return pstmt.executeUpdate() > 0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }
}