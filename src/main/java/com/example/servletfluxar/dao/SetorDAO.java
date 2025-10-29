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

//        Conectando ao banco de dados e enviando sql para ver os dados na tabela setor. 
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT * FROM setor ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, limite);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

//            Criando objetos
            while (rs.next()) {
                setor = new Setor();
                setor.setId(rs.getInt("id"));
                setor.setNome(rs.getString("nome"));
                setor.setDescricao(rs.getString("descricao"));
                setor.setIdUnidade(rs.getInt("id_unidade"));

//             Adicionando o objeto no hashmap de setores, a chave é id e o valor um setor.
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
//        Conectando ao banco de dados e inserindo um novo setor.
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("INSERT INTO setor (nome, descricao, id_unidade) VALUES (?, ?, ?)");
            pstmt.setString(1, setor.getNome());
            pstmt.setString(2, setor.getDescricao());
            pstmt.setInt(3, setor.getIdUnidade());

//          retorna um boolean caso o número de linhas afetadas seja maior que 0, se for, a ação foi feita.
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
            conn = Conexao.conectar();

//          Preparando comando SQL para atualizar informações do administrador da empresa.
            pstmt = conn.prepareStatement("UPDATE setor SET nome = ?, descricao = ?, id_unidade = ? WHERE id = ?");
            pstmt.setString(1,setor.getNome());
            pstmt.setString(2, setor.getDescricao());
            pstmt.setInt(3, setor.getIdUnidade());
            pstmt.setInt(4, setor.getId());

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