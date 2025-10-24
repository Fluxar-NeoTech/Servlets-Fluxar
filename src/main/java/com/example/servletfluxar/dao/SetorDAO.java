package com.example.servletfluxar.dao;

import com.example.servletfluxar.Conexao;
import com.example.servletfluxar.dao.interfaces.GenericoDAO;
import com.example.servletfluxar.model.Setor;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SetorDAO implements GenericoDAO<Setor> {
//    Declaração de atributos:
    private Connection conn = null;
    private PreparedStatement pstmt;
    private ResultSet rs;
    @Override
    public Map<Integer, Setor> listar(int pagina, int limite) {
//        Declarando variáveis:
        int offset = (pagina - 1) * limite;
        Map<Integer, Setor> setores= new HashMap<>();
        Setor setor;

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM setor ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos setores:
            while (rs.next()) {
                setor = new Setor();
                setor.setId(rs.getInt("id"));
                setor.setNome(rs.getString("nome"));
                setor.setDescricao(rs.getString("descricao"));
                setor.setIdUnidade(rs.getInt("id_unidade"));

                setores.put(rs.getInt("id"), setor);
            }

//            Retornando a lista de setores cadastrados:
            return setores;

        } catch (Exception e) {
            return setores;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public Setor buscarPorId(int id){
//        Conectando ao banco de dados e enviando sql:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM setor WHERE id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();

//            Verificando se há um plano com esse id:
            if(rs.next()){
//                Retornando plano encontrado:
                return new Setor(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("id_unidade"));
            }

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public boolean inserir(Setor setor){
//        Conectando ao banco de dados e dando o insert:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO setor (nome, descricao, id_unidade) VALUES (?, ?, ?)");
            pstmt.setString(1, setor.getNome());
            pstmt.setString(2, setor.getDescricao());
            pstmt.setInt(3, setor.getIdUnidade());

            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public boolean alterar(Setor setor){
        try {
            // Obtenção da conexão com o banco de dados:
            conn = Conexao.conectar();

            // Preparando comando SQL para atualizar a senha do admin da empresa:
            pstmt = conn.prepareStatement("UPDATE setor SET nome = ?, descricao = ?, id_unidade = ? WHERE id = ?");
            pstmt.setString(1,setor.getNome());
            pstmt.setString(2, setor.getDescricao());
            pstmt.setInt(3, setor.getIdUnidade());
            pstmt.setInt(4, setor.getId());

            // Execução da atualização
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
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("DELETE FROM setor WHERE id = ?");
            pstmt.setInt(1, id);

            return pstmt.executeUpdate()>0;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally {
            Conexao.desconectar(conn);
        }
    }
}