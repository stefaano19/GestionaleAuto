package com.example.gestionaleauto.Controllers;

import com.example.gestionaleauto.Entities.Auto;
import com.example.gestionaleauto.Services.GestioneAuto;
import com.example.gestionaleauto.Util.ResponseMessage;
import com.example.gestionaleauto.Util.TipologiaAuto;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auto")
public class AutoController{
    @Autowired
    private GestioneAuto gestioneAuto;
    @GetMapping
    public ResponseEntity mostraAuto(){
        return new ResponseEntity(gestioneAuto.mostraAuto(), HttpStatus.OK);
    }
    @GetMapping("/mostraAutoPerTipologia")
    public ResponseEntity mostraAutoPerTipologia(@RequestBody @Valid TipologiaAuto tipologiaAuto){
        return new ResponseEntity(gestioneAuto.mostraAutoPerTipologia(tipologiaAuto), HttpStatus.OK);
    }
    @GetMapping("/mostraAutoPerPrezzo")
    public ResponseEntity mostraAutoPerPrezzo(){
        return new ResponseEntity(gestioneAuto.mostraAutoPerPrezzo(), HttpStatus.OK);
    }
    @GetMapping("/mostraAutoPerDisponibilità")
    public ResponseEntity mostraAutoPerDisponibilità(){
        return new ResponseEntity(gestioneAuto.mostraAutoPerDisponibilità(), HttpStatus.OK);
    }

    @GetMapping("/mostraAutoDaAcquistare")
    public ResponseEntity mostraAutoDaAcquistare(){
        return new ResponseEntity(gestioneAuto.mostraAutoDaAcquistare(), HttpStatus.OK);
    }
    @GetMapping("/mostraAutoDaAcquistarePerModello")
    public ResponseEntity mostraAutoDaAcquistarePerModello(){
        return new ResponseEntity(gestioneAuto.mostraAutoDaAcquistarePerModello(), HttpStatus.OK);
    }

    @PostMapping("/creaAuto")
    public ResponseEntity creaAuto(@RequestBody @Valid Auto auto){
        try{
            gestioneAuto.creaAuto(auto);
        }catch(Exception e){
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
