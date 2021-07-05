package com.example.curso02;

import models.Comidajap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import persistencia.ComidaJapDAO;

import java.util.ArrayList;

@SpringBootTest
class Curso02ApplicationTests {

    @Test
    void contextLoads() {
        ComidaJapDAO cdao = new ComidaJapDAO();
        cdao.createTableComidaJaponesa();
        cdao.insertTabelaComidaJaponesa(new Comidajap(1,"sushi","entrada",(float) 9.50,"peixe com arroz",2));
        ArrayList<Comidajap> lista  = cdao.select();
        for(Comidajap c : lista){
            System.out.println(c.getNome());
        }
    }

}
