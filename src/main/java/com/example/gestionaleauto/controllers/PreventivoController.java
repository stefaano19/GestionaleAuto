package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.Preventivo;
import com.example.gestionaleauto.services.GestionePreventivo;
import com.example.gestionaleauto.util.ResponseMessage;
import com.example.gestionaleauto.util.exception.PreventivoEsistenteException;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preventivi")
public class PreventivoController {
    @Autowired
    private GestionePreventivo gestionePreventivo;

    @PostMapping("/creaPreventivo")
    public ResponseEntity creaPreventivo(@RequestBody @Valid Preventivo preventivo){
        try {
            gestionePreventivo.creaPreventivo(preventivo);
        }catch(PreventivoEsistenteException e){
            return new ResponseEntity<>(new ResponseMessage("PREVENTIVO ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("PREVENTIVO CREATO"), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity mostraPreventivi(){
        return new ResponseEntity<>(gestionePreventivo.mostraPreventivi(), HttpStatus.OK);
    }
}
