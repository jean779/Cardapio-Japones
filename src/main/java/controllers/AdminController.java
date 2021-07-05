package controllers;

import models.Comidajap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import persistencia.ComidaJapDAO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller()
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView Homeapp(HttpServletResponse response){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        Date date = new Date();
        Cookie cookie = new Cookie("Ultimavisita", dateFormat.format(date));
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);

        ComidaJapDAO comidaDao = new ComidaJapDAO();

        ArrayList<Comidajap> listaComidas = comidaDao.select();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("comidas", listaComidas);
        modelAndView.setViewName("cadastro");

        return modelAndView;
    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public String cadastrarProduto(Comidajap comida){
        System.out.println(comida.getNome());
        System.out.println(comida.getDescricao());
        System.out.println(comida.getQuantidade());
        System.out.println(comida.getPreco());
        System.out.println(comida.getTipo());

        ComidaJapDAO dao = new ComidaJapDAO();
        boolean inseriu = dao.insertTabelaComidaJaponesa(comida);

        if(inseriu == true){
            return "index";
        }
        return "/cadastro";
    }

}
