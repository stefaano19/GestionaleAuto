package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.util.QueryToXML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/home")
public class DashboardController {

    public String home(){
        return " Gestione Concessionario";
    }
}
