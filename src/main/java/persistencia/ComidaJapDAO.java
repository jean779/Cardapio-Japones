package persistencia;

import com.example.curso02.Conexao;
import models.Comidajap;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ComidaJapDAO {
    private Conexao conn;
    private String create = "CREATE TABLE public.comidajap" +
            "(" +
            "    ncomida character varying(12) COLLATE pg_catalog.\"default\"," +
            "    descricao character varying(80) COLLATE pg_catalog.\"default\"," +
            "    id SERIAL," +
            "    preco real," +
            "    tipo character varying COLLATE pg_catalog.\"default\"," +
            "    quantidade integer," +
            "    CONSTRAINT comidajap_pkey PRIMARY KEY (id)" +
            ")";
    private String insert = "INSERT INTO public.comidajap(\n" +
            "\tncomida, descricao, preco, tipo, quantidade)\n" +
            "\tVALUES (?, ?, ?, ?, ?);";

    private String select = "SELECT * FROM comidajap";
    private String selectbyid = "SELECT * FROM comidajap where id = ?";

    public ComidaJapDAO(){
        conn = new Conexao(System.getenv("host"),System.getenv("port"),System.getenv("database"),
                System.getenv("user"),System.getenv("pass"));
    }
    public void createTableComidaJaponesa(){
        try{
            conn.Conectar();
            Statement instrucao = conn.getConn().createStatement();
            instrucao.execute(create);
            conn.Desconectar();
        }catch(SQLException e){
            System.out.println("erro na criação da tabela:"+ e.getMessage());
        }
    }

    public boolean inserirListaComidasInicial(ArrayList<Comidajap> listaComidas){

        for (Comidajap comida : listaComidas){
            boolean inseriu = insertTabelaComidaJaponesa(comida);
            if(inseriu == false) {
                System.out.println("Falha ao tentar inserir comida: " + comida.getNome());
                return false;
            }
        }

        return true;
    }

    public boolean insertTabelaComidaJaponesa(Comidajap c){
        try{
            conn.Conectar();
            PreparedStatement instrucao = conn.getConn().prepareStatement(insert);
            instrucao.setString(1, c.getNome());
            instrucao.setString(2, c.getDescricao());
            instrucao.setDouble(3, c.getPreco());
            instrucao.setString(4, c.getTipo());
            instrucao.setInt(5, c.getQuantidade());
            instrucao.execute();
            conn.Desconectar();
            return true;
        }catch(SQLException e){
            System.out.println("erro ao inserir a comida:"+ e.getMessage());
            return false;
        }
    }
    public ArrayList<Comidajap> select(){
        ArrayList<Comidajap> lista = new ArrayList<>();
        try{
            conn.Conectar();
            Statement instrucao = conn.getConn().createStatement();
            ResultSet rs =  instrucao.executeQuery(select);
            while (rs.next()){
                lista.add(new Comidajap(rs.getInt("id"),rs.getString("ncomida"),rs.getString("tipo"),
                        rs.getFloat("preco"),rs.getString("descricao"),rs.getInt("quantidade")));
            }
            conn.Desconectar();
        }catch(SQLException e){
            System.out.println("erro ao listar as comidas:"+e.getMessage());
        }
        return lista;
    } //SELECT MAX(id) FROM public.comidajap

    public Comidajap findById(int id){
        Comidajap comida = null;
        try{
            conn.Conectar();
            PreparedStatement instrucao = conn.getConn().prepareStatement(selectbyid);
            instrucao.setInt(1, id);
            ResultSet rs =  instrucao.executeQuery();
            rs.next();
            comida = new Comidajap(rs.getInt("id"),rs.getString("ncomida"),rs.getString("tipo"),
                    rs.getFloat("preco"),rs.getString("descricao"),rs.getInt("quantidade"));
            conn.Desconectar();
        }catch(SQLException e){
            System.out.println("erro ao adicionar o carrinho:"+e.getMessage());
        }
        return comida;
    }

}



