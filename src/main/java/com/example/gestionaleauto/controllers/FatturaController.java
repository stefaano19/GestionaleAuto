package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.Fattura;
import com.example.gestionaleauto.entities.OrdineVendita;
import com.example.gestionaleauto.services.GestioneFattura;
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
@RequestMapping("/fatture")
public class FatturaController {
    @Autowired
    private GestioneFattura gestioneFattura;

    @PostMapping("/creaFattura")
    @PreAuthorize("hasRole('dipendente')")
    public ResponseEntity creaFattura(@RequestBody @Valid Fattura fattura, @RequestBody @Valid OrdineVendita ordineVendita) {
        try {
            gestioneFattura.creaFattura(fattura, ordineVendita);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("FATTURA ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("FATTURA CREATA"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @PreAuthorize("hasRole('dipendente')")
    public ResponseEntity mostraFatture() {
        return new ResponseEntity<>(gestioneFattura.mostraFatturaPerDataEmissione(), HttpStatus.OK);
    }
    @GetMapping("/30Giorni")
    @PreAuthorize("hasRole('dipendente')")
    public ResponseEntity mostraFatture30() {
        return new ResponseEntity<>(gestioneFattura.mostraFattureScadenza30(), HttpStatus.OK);
    }
    @GetMapping("/PerDataScadenza")
    @PreAuthorize("hasRole('dipendente')")
    public ResponseEntity mostraFatturePerDataScadenza() {
        return new ResponseEntity<>(gestioneFattura.mostraFatturaPerDataScadenza(), HttpStatus.OK);
    }
    @GetMapping("/PerImporto")
    @PreAuthorize("hasRole('dipendente')")
    public ResponseEntity mostraFatturePerImporto() {
        return new ResponseEntity<>(gestioneFattura.mostraFatturaPerImporto(), HttpStatus.OK);
    }
}