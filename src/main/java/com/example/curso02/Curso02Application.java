package com.example.curso02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages=("controllers"))
public class Curso02Application {

    public static void main(String[] args) {
        SpringApplication.run(Curso02Application.class, args);
    }

}
