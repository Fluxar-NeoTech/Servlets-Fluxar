package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.model.Setor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {
    public static List<Setor> listarSetores() {
//        Declarando variáveis:
        String sql = "SELECT * FROM setor";
        Connection conn = null;
        Statement stmt;
        ResultSet rs;
        List<Setor> setores= new ArrayList<>();

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

//            Criando objetos e adicionando a lista dos setores:
            while (rs.next()) {
                setores.add(new Setor(rs.getInt("id"),rs.getString("nome"), rs.getInt("id_unidade")));
            }

//            Retornando a lista de setores cadastrados:
            return setores;

        } catch (Exception e) {
            return setores;
        }finally {
            Conexao.desconectar(conn);
        }
    }
}