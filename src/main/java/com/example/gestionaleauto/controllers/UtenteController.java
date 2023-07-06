package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.Utente;
import com.example.gestionaleauto.services.GestioneUtente;
import com.example.gestionaleauto.util.QueryToXML;
import com.example.gestionaleauto.util.ResponseMessage;
import com.example.gestionaleauto.util.exception.AutoEsistenteException;
import com.example.gestionaleauto.util.exception.ClienteEsistenteException;
import com.example.gestionaleauto.util.exception.UtenteEsistenteException;
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

/**
 *
 */
@Controller
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    GestioneUtente gestioneUtente;
    @PostMapping ("/registrazione")
    public ResponseEntity<String> registrazione(@RequestBody(required = false) Map<String,String> requestMap) {
        String messaggio = new String();
        try {
            messaggio  = gestioneUtente.registrazione(requestMap);
        } catch (UtenteEsistenteException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("messag:"+messaggio, HttpStatus.BAD_REQUEST);
        }
        return ResponseMessage.getResponseEntity(messaggio, HttpStatus.OK);
    }
    @PostMapping ("/login")
    public ResponseEntity<Boolean> login(@RequestBody @Valid Utente utente){
        return new ResponseEntity<Boolean>(gestioneUtente.login(utente), HttpStatus.OK);
    }
}
