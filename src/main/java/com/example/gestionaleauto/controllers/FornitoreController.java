package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.Fornitore;
import com.example.gestionaleauto.services.GestioneFornitore;
import com.example.gestionaleauto.util.ResponseMessage;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fornitori")
public class FornitoreController {
    @Autowired
    private GestioneFornitore gestioneFornitore;

    @GetMapping
    public ResponseEntity mostraFornitori(){
        return new ResponseEntity(gestioneFornitore.mostraFornitori(), HttpStatus.OK);
    }
    @GetMapping("/mostraFornitoriPerSede")
    public ResponseEntity mostraFornitoriPerSede(@RequestBody @Valid String sede){
        return new ResponseEntity(gestioneFornitore.mostraFornitoriPerSede(sede), HttpStatus.OK);
    }
    @GetMapping("/mostraFornitoriPerPartitaIva")
    public ResponseEntity mostraFornitoriPerPartitaIva(@RequestBody @Valid String partitaIva){
        return new ResponseEntity(gestioneFornitore.mostraFornitoriPerPartitaIva(partitaIva), HttpStatus.OK);
    }
    @PostMapping("/creaFornitore")
    public ResponseEntity creaFornitore(@RequestBody @Valid Fornitore fornitore){
        try{
            gestioneFornitore.creaFornitore(fornitore);
        }catch(Exception e){
            return new ResponseEntity(new ResponseMessage("FORNITORE ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("CREATO"), HttpStatus.OK);
    }

    @PostMapping("/aggiornaFornitore")
    public ResponseEntity aggiornaFornitore(@RequestBody @Valid String ragioneSociale, @RequestBody @Valid String partitaIva){
        try{
            gestioneFornitore.aggiornaFornitore(ragioneSociale,partitaIva);
        }catch(Exception e){
            return new ResponseEntity(new ResponseMessage("FORNITORE NON ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("AGGIORNATO"), HttpStatus.OK);
    }
    @PostMapping("/cancellaFornitore")
    public ResponseEntity cancellaFornitore(@RequestBody @Valid Fornitore fornitore){
        gestioneFornitore.cancellaFornitore(fornitore);
        return new ResponseEntity<>(new ResponseMessage("CANCELLATO"), HttpStatus.OK);
    }
}
