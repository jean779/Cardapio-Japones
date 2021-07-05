package controllers;

import models.Comidajap;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import persistencia.ComidaJapDAO;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;


@Controller
public class HomeController {

    @RequestMapping("/home")
    public String Homeapp() {
        return "index";
    }

    @RequestMapping("/itensAdmin")
    public String itensAdmin() {
        return "admin/listarItensAdmin";
    }

    @RequestMapping("/adicionarcarrinho")
    public String adicionarCarrinho(@RequestParam("id") int id, final HttpServletRequest request) {
        System.out.println("ta chegando");
        ComidaJapDAO cdao = new ComidaJapDAO();
        Comidajap comida = cdao.findById(id);
        System.out.println(comida.getNome());
        if (request.getSession().getAttribute("carrinho") == null) {
            ArrayList carrinho = new ArrayList();
            carrinho.add(comida);
            request.getSession().setAttribute("carrinho", carrinho);
        } else {
            ArrayList carrinho = (ArrayList) request.getSession().getAttribute("carrinho");
            carrinho.add(comida);
            request.getSession().setAttribute("carrinho", carrinho);
        }
        return "redirect:/comidajap/listarItens";
    }

    @RequestMapping("/finalizarCompra")
    public String finalizarCompra(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/home";
    }


    @RequestMapping("/comidajap/listarItens")
    public ModelAndView listarItens() {
        ComidaJapDAO comidaDao = new ComidaJapDAO();


        ArrayList<Comidajap> listaComidas = comidaDao.select();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("comidas", listaComidas);
        modelAndView.setViewName("comidajap/listarItens");

        return modelAndView;
    }

    @RequestMapping("/comidajap/vercarrinho")
    public ModelAndView listarItensdoCarrinho(HttpServletRequest request) {

        ModelAndView carrinhoVazio = new ModelAndView();
        if (request.getSession().getAttribute("carrinho") == null) {
            carrinhoVazio.setViewName("/comidajap/carrinhoVazio");
            return carrinhoVazio;
        }
        ArrayList verCarrinho = (ArrayList) request.getSession().getAttribute("carrinho");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("carrinho", verCarrinho);
        modelAndView.setViewName("comidajap/vercarrinho");

        return modelAndView;
    }

    @RequestMapping("/config")
    public ModelAndView criarComida() {
        ComidaJapDAO comidaDao = new ComidaJapDAO();

        comidaDao.createTableComidaJaponesa();

        Comidajap sushi = new Comidajap(1, "Sushi", "Entrada", 3.50, "Bolinho de arroz enrolado com uma alga.", 1);
        Comidajap sashimi = new Comidajap(2, "Sashimi", "Entrada", 15.99, " Peixes e frutos do mar fatiados em pequenos pedaços.", 1);
        Comidajap tempura = new Comidajap(3, "Tempurá", "Entrada", 19.25, "Pedaços fritos de mariscos envoltos num polme fino.", 1);
        Comidajap guioza = new Comidajap(4, "Guioza", "Entrada", 11.00, "Recheio de carne moída e/ou de legumes dentro de uma massa frita.", 1);
        Comidajap temaki = new Comidajap(5, "Temaki", "Eentrada", 14.11, "Formato de cone, com o nori na parte externa e recheio ao seu gosto.", 1);

        ArrayList<Comidajap> listaComidas = new ArrayList<>();
        listaComidas.add(sushi);
        listaComidas.add(sashimi);
        listaComidas.add(tempura);
        listaComidas.add(guioza);
        listaComidas.add(temaki);

        boolean retorno = comidaDao.inserirListaComidasInicial(listaComidas);

        ModelAndView modelAndView = new ModelAndView();


        if (retorno == true) {
            modelAndView.setViewName("comidajap/tabelaInserida");
            modelAndView.addObject("comidas", listaComidas);
        } else {
            modelAndView.setViewName("comidajap/erroInserir");
        }

        return modelAndView;
    }


}
