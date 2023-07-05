package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.CasaProduttrice;
import com.example.gestionaleauto.services.GestioneCasaProduttrice;
import com.example.gestionaleauto.util.ResponseMessage;
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
@RequestMapping("/caseProduttrici")
public class CasaProduttriceController {
    @Autowired
    private GestioneCasaProduttrice gestioneCasaProduttrice;

    @GetMapping

    public List<CasaProduttrice> mostraCaseProduttrici(){
        return gestioneCasaProduttrice.mostraCasaProduttrice();
    }
    @GetMapping("/mostraCaseProduttriciPerSede")

    public ResponseEntity mostraCaseProduttriciPerSede(@RequestBody @Valid String sede){
        return new ResponseEntity(gestioneCasaProduttrice.mostraCasaProduttricePerSede(sede), HttpStatus.OK);
    }
    @GetMapping("/mostraCaseProdutticiPerPartitaIva")

    public ResponseEntity mostraCaseProduttriciPerPartitaIva(@RequestBody @Valid String partitaIva){
        return new ResponseEntity(gestioneCasaProduttrice.mostraCasaProduttricePerPartitaIva(partitaIva), HttpStatus.OK);
    }
    @PostMapping("/creaCasaProduttrice")

    public ResponseEntity creaCasaProduttrice(@RequestBody @Valid CasaProduttrice casaProduttrice){
        try{
            gestioneCasaProduttrice.creaCasaProduttrice(casaProduttrice);
        }catch(Exception e){
            return new ResponseEntity(new ResponseMessage("CASAPRODUTTRICE ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("CREATA"), HttpStatus.OK);
    }

    @PostMapping("/aggiornaCasaProduttrice")
    public ResponseEntity aggiornaCasaProduttrice(@RequestBody @Valid String ragioneSociale, @RequestBody @Valid String partitaIva){
        try{
            gestioneCasaProduttrice.aggiornaCasaProduttrice(ragioneSociale, partitaIva);
        }catch(Exception e){
            return new ResponseEntity(new ResponseMessage("CASAPRODUTTRICE NON ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("AGGIORNATA"), HttpStatus.OK);
    }
    @PostMapping("/cancellaCasaProduttrice")

    public ResponseEntity cancellaCasaProduttrice(@RequestBody @Valid CasaProduttrice casaProduttrice){
        gestioneCasaProduttrice.cancellaCasaProduttrice(casaProduttrice);
        return new ResponseEntity<>(new ResponseMessage("CANCELLATA"), HttpStatus.OK);
    }

}
