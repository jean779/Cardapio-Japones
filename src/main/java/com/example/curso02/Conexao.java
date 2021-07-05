package com.example.curso02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private final String DB_URL;
    private final String USER ;
    private final String PASS ;
    private Connection conn;

    public Conexao(String host, String porta, String banco, String user, String pass){
       USER = user;
       PASS = pass;
       DB_URL = "jdbc:postgresql://"+host+":"+porta+"/"+banco;
    }

    public void Conectar(){
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }catch(SQLException e){
            System.out.println("erro na conexão:"+e.getMessage());
        }
    }

    public void Desconectar(){
        try{
            conn.close();
        }catch(SQLException e){
            System.out.println("erro na desconexão:"+e.getMessage());
        }
    }

    public Connection getConn() {
        return conn;
    }
}
