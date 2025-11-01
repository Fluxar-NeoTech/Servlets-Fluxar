package com.example.servletfluxar.dao;

import com.example.servletfluxar.conexao.Conexao;
import com.example.servletfluxar.dao.interfaces.DAO;
import com.example.servletfluxar.dao.interfaces.DependeEmpresa;
import com.example.servletfluxar.model.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO implements DAO<Setor>, DependeEmpresa<Setor> {
//    Declaração de atributos:
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public List<Setor> listar(int pagina, int limite) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Setor> setores= new ArrayList<>();
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

//             Adicionando o objeto na lista de setores, a chave é id e o valor um setor.
                setores.add(setor);
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
    public List<Setor> listarPorIdEmpresa(int pagina, int limite, int idEmpresa) {
//        Declarando variáveis:
        Connection conn = null;
        int offset = (pagina - 1) * limite;
        List<Setor> setores= new ArrayList<>();
        Setor setor;

//        Conectando ao banco de dados e enviando sql:
        try {
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT s.* FROM setor s JOIN unidade u ON s.id_unidade = u.id JOIN empresa e" +
                    " ON u.id_empresa = e.id WHERE id_empresa = ? ORDER BY id LIMIT ? OFFSET ?");
            pstmt.setInt(1, idEmpresa);
            pstmt.setInt(2, limite);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();

//            Criando objetos e adicionando a lista dos setores:
            while (rs.next()) {
                setor = new Setor();
                setor.setId(rs.getInt("id"));
                setor.setNome(rs.getString("nome"));
                setor.setDescricao(rs.getString("descricao"));
                setor.setIdUnidade(rs.getInt("id_unidade"));

                setores.add(setor);
            }

//            Retornando a lista de setores cadastrados:
            return setores;

        } catch (Exception e) {
            return setores;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Setor> listarNomesPorIdUnidade(int idUnidade){
        //        Declaração de variáveis:
        Connection conn = null;
        Setor setor;
        List<Setor> setores = new ArrayList<>();

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT DISTINCT id, nome FROM setor WHERE id_unidade = ?");
            pstmt.setInt(1, idUnidade);
            rs = pstmt.executeQuery();

//            Coletando dados:
            while (rs.next()) {
                setor = new Setor();
                setor.setNome(rs.getString("nome"));
                setor.setId(rs.getInt("id"));
                setores.add(setor);
            }

//        Retornando as unidades cadastradas por essa empresa:
            return setores;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return setores;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    public List<Setor> listarNomesPorIdEmpresa(int idEmpresa){
        //        Declaração de variáveis:
        Connection conn = null;
        Setor setor;
        List<Setor> setores = new ArrayList<>();

//        Conectando ao banco de dados:
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT DISTINCT s.id, s.nome FROM setor s JOIN unidade u ON s.id_unidade = u.id WHERE u.id_empresa = ?");
            pstmt.setInt(1, idEmpresa);
            rs = pstmt.executeQuery();

//            Coletando dados:
            while (rs.next()) {
                setor = new Setor();
                setor.setNome(rs.getString("nome"));
                setor.setId(rs.getInt("id"));
                setores.add(setor);
            }

//        Retornando as unidades cadastradas por essa empresa:
            return setores;

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return setores;
        }finally {
            Conexao.desconectar(conn);
        }
    }

    @Override
    public int contar(){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*)\"contador\" FROM setor");

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

    @Override
    public int contarPorIdEmpresa(int idEmpresa){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(s.*)\"contador\" FROM setor s JOIN unidade u " +
                    "ON s.id_unidade = u.id JOIN empresa e ON u.id_empresa = e.id WHERE id_empresa = ?");
            pstmt.setInt(1, idEmpresa);
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

    public int contarPorEmpresaStatus(char status){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(*)\"contador\" FROM setor s JOIN unidade u ON s.id_unidade = u.id" +
                    " JOIN empresa e ON u.id_empresa = e.id JOIN assinatura a ON a.id_empresa = e.id WHERE a.status = ?");
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

    public int contarPorIdUnidade(int idUnidade){
        Connection conn = null;
        try{
            conn = Conexao.conectar();
            pstmt = conn.prepareStatement("SELECT COUNT(s.*)\"contador\" FROM setor s JOIN unidade u " +
                    "ON s.id_unidade = u.id WHERE s.id_unidade = ?");
            pstmt.setInt(1, idUnidade);
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

    @Override
    public Setor buscarPorId(int id){
        Connection conn = null;
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
       Connection conn = null;
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
        Connection conn = null;
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
        Connection conn = null;
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