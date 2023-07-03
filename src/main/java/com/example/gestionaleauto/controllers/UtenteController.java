package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.Utente;
import com.example.gestionaleauto.services.GestioneUtente;
import com.example.gestionaleauto.util.ResponseMessage;
import com.example.gestionaleauto.util.exception.AutoEsistenteException;
import com.example.gestionaleauto.util.exception.ClienteEsistenteException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    GestioneUtente gestioneUtente;
    @PostMapping ("/registrazione")
    public ResponseEntity<String> registrazione(@RequestBody(required = false) Map<String,String> requestMap) {
        try {
            gestioneUtente.registrazione(requestMap);
            return ResponseMessage.getResponseEntity("Aggiunto", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseMessage.getResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping ("/login")
    public ResponseEntity<Boolean> login(@RequestBody @Valid Utente utente){
        return new ResponseEntity<Boolean>(gestioneUtente.login(utente), HttpStatus.OK);
    }
}
