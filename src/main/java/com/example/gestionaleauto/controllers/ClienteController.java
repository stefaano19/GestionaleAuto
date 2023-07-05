package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.Cliente;
import com.example.gestionaleauto.services.GestioneCliente;
import com.example.gestionaleauto.util.ResponseMessage;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@RequestMapping("/clienti")
public class ClienteController {
    @Autowired
    private GestioneCliente gestioneCliente;

    @PostMapping("/creaCliente")
    public ResponseEntity creaCliente(@RequestBody @Valid Cliente cliente) {
        try {
            gestioneCliente.aggiungiCliente(cliente);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("CLIENTE ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("CLIENTE CREATO"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity mostraClienti() {
        return new ResponseEntity<>(gestioneCliente.mostraClienti(), HttpStatus.OK);
    }
}
