package com.web.av1unidade.DAO;

import com.web.av1unidade.Models.Lojista;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LojistaDAO {
    private Conexao con;
    private String PRO = "SELECT id FROM Lojista WHERE email=? AND senha=?";

    public LojistaDAO() {
        con = new Conexao("jdbc:postgresql://localhost:5432/banco_pw", "postgres", "postgres");
    }

    public boolean procurar(Lojista l) {
        boolean achou = false;
        try{
            con.conectar();
            PreparedStatement ps = con.getConexao().prepareStatement(PRO);
            ps.setString(1, l.getEmail());
            ps.setString(2, l.getSenha());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                achou = true;
            }
            con.desconectar();
        }catch(Exception e){
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return achou;
    }
}