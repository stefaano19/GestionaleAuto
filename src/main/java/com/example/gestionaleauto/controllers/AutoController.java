package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.Auto;
import com.example.gestionaleauto.services.GestioneAuto;
import com.example.gestionaleauto.util.ResponseMessage;
import com.example.gestionaleauto.util.TipologiaAuto;
import com.example.gestionaleauto.util.exception.AutoEsistenteException;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/auto")
public class AutoController{
    @Autowired
    private GestioneAuto gestioneAuto;
    @GetMapping
    public List<Auto> mostraAuto(){
        return gestioneAuto.mostraAuto();
    }
    @GetMapping("/mostraAutoPerTipologia")
    public ResponseEntity mostraAutoPerTipologia(@RequestBody @Valid TipologiaAuto tipologiaAuto){
        return new ResponseEntity(gestioneAuto.mostraAutoPerTipologia(tipologiaAuto), HttpStatus.OK);
    }
    @GetMapping("/mostraAutoPerPrezzo")
    public ResponseEntity mostraAutoPerPrezzo(){
        return new ResponseEntity(gestioneAuto.mostraAutoPerPrezzo(), HttpStatus.OK);
    }
    @GetMapping("/mostraAutoPerDisponibilita")
    public ResponseEntity mostraAutoPerDisponibilita(){
        return new ResponseEntity(gestioneAuto.mostraAutoPerDisponibilita(), HttpStatus.OK);
    }

    @GetMapping("/mostraAutoDaAcquistare")
    public ResponseEntity mostraAutoDaAcquistare(){
        return new ResponseEntity(gestioneAuto.mostraAutoDaAcquistare(), HttpStatus.OK);
    }
    @GetMapping("/mostraAutoDaAcquistarePerModello")
    public List<Auto> mostraAutoDaAcquistarePerModello(){
        return gestioneAuto.mostraAutoDaAcquistarePerModello();
    }

    @PostMapping("/creaAuto")
    public ResponseEntity creaAuto(@RequestBody @Valid Auto auto){
        try{
            gestioneAuto.creaAuto(auto);
        }catch(AutoEsistenteException e){
            return new ResponseEntity(new ResponseMessage("AUTO ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("CREATA"), HttpStatus.OK);
    }

    @PostMapping("/aggiornaAuto")
    public ResponseEntity aggiornaAuto(@RequestBody @Valid String modello, @RequestBody @Valid double prezzo){
        try{
            gestioneAuto.aggiornaAuto(modello, prezzo);
        }catch(Exception e){
            return new ResponseEntity(new ResponseMessage("AUTO NON ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("AGGIORNATA"), HttpStatus.OK);
    }

    @PostMapping("/aggiornaAutoShowroom")
    public ResponseEntity aggiornaAutoShowroom(@RequestBody @Valid String modello, @RequestBody @Valid boolean showroom){
        try{
            gestioneAuto.aggiornaShowroom(modello, showroom);
        }catch(Exception e){
            return new ResponseEntity(new ResponseMessage("AUTO NON ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("AGGIORNATA"), HttpStatus.OK);
    }
    @PostMapping("/cancellaAuto")
    public ResponseEntity cancellaAuto(@RequestBody @Valid Auto auto){
        gestioneAuto.cancellaAuto(auto.getId());
        return new ResponseEntity<>(new ResponseMessage("CANCELLATA"), HttpStatus.OK);
    }





}
