package com.example.gestionaleauto.Controllers;

import com.example.gestionaleauto.Entities.Appuntamento;
import com.example.gestionaleauto.Entities.Auto;
import com.example.gestionaleauto.Services.GestioneAppuntamento;
import com.example.gestionaleauto.Util.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/appuntamenti")
public class AppuntamentiController {
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
        }catch(Exception e){
            return new ResponseEntity(new ResponseMessage("APPUNTAMENTO ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("AGGIUNTO"), HttpStatus.OK);
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
    public ResponseEntity appuntamenti(){
        return new ResponseEntity<>(gestioneAppuntamento.mostraAppuntamenti(), HttpStatus.OK);
    }

    @PostMapping("/rimuoviAppuntamento")
    public ResponseEntity rimuoviAppuntamento(@RequestBody @Valid Appuntamento appuntamento){
            gestioneAppuntamento.rimuoviAppuntamento(appuntamento);
            return new ResponseEntity(new ResponseMessage("Appuntamento Rimosso"), HttpStatus.BAD_REQUEST);
    }


}
