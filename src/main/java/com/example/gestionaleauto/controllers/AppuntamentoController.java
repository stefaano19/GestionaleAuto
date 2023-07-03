package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.Appuntamento;
import com.example.gestionaleauto.entities.Auto;
import com.example.gestionaleauto.services.GestioneAppuntamento;
import com.example.gestionaleauto.util.ResponseMessage;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/appuntamenti")
public class AppuntamentoController {
    @Autowired
    private GestioneAppuntamento gestioneAppuntamento;

    @GetMapping("/autoDisponibili")
    public ResponseEntity autoDisponibiliData(@RequestBody @Valid Date data){
        return new ResponseEntity<>(gestioneAppuntamento.autoDisponibiliData(data), HttpStatus.OK);
    }

    @PostMapping("/creaAppuntamento")
    public ResponseEntity creaAppuntamento(@RequestBody @Valid Appuntamento appuntamento){
        try{
            gestioneAppuntamento.creaAppuntamento(appuntamento);
            System.out.println("ok");
            return new ResponseEntity<>(new ResponseMessage("AGGIUNTO"), HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity(new ResponseMessage("APPUNTAMENTO ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/creaAppuntamentoConValori")
    public ResponseEntity creaAppuntamento(@RequestBody @Valid Date data, @RequestBody List<Auto> auto, @RequestBody boolean testDrive){
        try{
            gestioneAppuntamento.creaAppuntamento(data, auto, testDrive);
        }catch(Exception e){
            return new ResponseEntity(new ResponseMessage("APPUNTAMENTO ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("AGGIUNTO"), HttpStatus.OK);
    }

    @GetMapping
    public List<Appuntamento> appuntamenti(){
        System.out.println(gestioneAppuntamento.mostraAppuntamenti().toArray());
        return gestioneAppuntamento.mostraAppuntamenti();
    }

    @PostMapping("/rimuoviAppuntamento")
    public ResponseEntity rimuoviAppuntamento(@RequestBody @Valid Appuntamento appuntamento){
            gestioneAppuntamento.rimuoviAppuntamento(appuntamento);
            return new ResponseEntity(new ResponseMessage("Appuntamento Rimosso"), HttpStatus.BAD_REQUEST);
    }


}
