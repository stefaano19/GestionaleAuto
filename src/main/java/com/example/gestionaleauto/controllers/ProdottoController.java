package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.Prodotto;
import com.example.gestionaleauto.services.GestioneProdotto;
import com.example.gestionaleauto.util.ResponseMessage;
import com.example.gestionaleauto.util.TipologiaProdotto;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
    @Autowired
    private GestioneProdotto gestioneProdotto;

    @GetMapping
    public ResponseEntity mostraProdotti() {
        return new ResponseEntity(gestioneProdotto.mostraProdotti(), HttpStatus.OK);
    }

    @GetMapping("/mostraProdottoPerTipologia")
    public ResponseEntity mostraProdottoPerTipologia(@RequestBody @Valid TipologiaProdotto tipologiaProdotto) {
        return new ResponseEntity(gestioneProdotto.mostraProdottiPerTipologia(tipologiaProdotto), HttpStatus.OK);
    }

    @GetMapping("/mostraProdottiPerPrezzo")
    public ResponseEntity mostraProdottiPerPrezzo() {
        return new ResponseEntity(gestioneProdotto.mostraProdottiPerPrezzo(), HttpStatus.OK);
    }

    @GetMapping("/mostraProdottiPerDisponibilità")
    public ResponseEntity mostraProdottiPerDisponibilità() {
        return new ResponseEntity(gestioneProdotto.mostraProdottiPerDisponibilità(), HttpStatus.OK);
    }

    @GetMapping("/mostraProdottiDaOrdinare")
    public ResponseEntity mostraProdottiDaAcquistare() {
        return new ResponseEntity(gestioneProdotto.mostraProdottiDaOrdinare(), HttpStatus.OK);
    }

    @PostMapping("/creaProdotto")
    public ResponseEntity creaProdotto(@RequestBody @Valid Prodotto prodotto) {
        try {
            gestioneProdotto.creaProdotto(prodotto);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseMessage("PRODOTTO ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("CREATO"), HttpStatus.OK);
    }

    @PostMapping("/aggiornaProdotto")
    public ResponseEntity aggiornaProdotto(@RequestBody @Valid String nome, @RequestBody @Valid double prezzo) {
        try {
            gestioneProdotto.aggiornaProdotto(nome, prezzo);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseMessage("PRODOTTO NON ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("AGGIORNATO"), HttpStatus.OK);
    }

    @PostMapping("/cancellaProdotto")
    public ResponseEntity cancellaProdotto(@RequestBody @Valid Prodotto prodotto) {
        gestioneProdotto.cancellaProdotto(prodotto);
        return new ResponseEntity<>(new ResponseMessage("CANCELLATO"), HttpStatus.OK);
    }

}
