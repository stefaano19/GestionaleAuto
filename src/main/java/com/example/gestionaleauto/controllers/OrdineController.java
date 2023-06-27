package com.example.gestionaleauto.controllers;

import com.example.gestionaleauto.entities.*;
import com.example.gestionaleauto.services.GestioneOrdine;
import com.example.gestionaleauto.util.Pagamento;
import com.example.gestionaleauto.util.ResponseMessage;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordini")
public class OrdineController {
    @Autowired
    private GestioneOrdine gestioneOrdine;

    @GetMapping("/nonConformi")
    public ResponseEntity mostraOrdiniNonConformi() {
        return new ResponseEntity(gestioneOrdine.ordiniNonConformi(), HttpStatus.OK);
    }
    @GetMapping("/vendita")
    public ResponseEntity mostraOrdiniVendita() {
        return new ResponseEntity(gestioneOrdine.ordiniVendita(), HttpStatus.OK);
    }
    @GetMapping("/acquisto")
    public ResponseEntity mostraOrdiniAcquisto() {
        return new ResponseEntity(gestioneOrdine.ordiniAcquisto(), HttpStatus.OK);
    }

    @GetMapping("/mostraPraticheVendita")
    public ResponseEntity mostraPraticheVendita() {
        return new ResponseEntity(gestioneOrdine.mostraPraticheVendita(), HttpStatus.OK);
    }

    @GetMapping("/mostraPraticheVenditaPagamentoRate")
    public ResponseEntity mostraPraticheVenditaPagementoRate(@RequestBody @Valid Pagamento pagamento) {
        return new ResponseEntity(gestioneOrdine.mostraPraticheVenditaPerPagamentoRate(pagamento), HttpStatus.OK);
    }

    @GetMapping("/mostraPraticheVenditaPagamentoContanti")
    public ResponseEntity mostraPraticheVenditaPagamentoContanti(@RequestBody @Valid Pagamento pagamento) {
        return new ResponseEntity(gestioneOrdine.mostraPraticheVenditaPerPagamentoRate(pagamento), HttpStatus.OK);
    }

    @GetMapping("/mostraPraticheVenditaPagamentoAssegno")
    public ResponseEntity mostraPraticheVenditaPagamentoAssegno(@RequestBody @Valid Pagamento pagamento) {
        return new ResponseEntity(gestioneOrdine.mostraPraticheVenditaPerPagamentoAssegno(pagamento), HttpStatus.OK);
    }
    @GetMapping("/mostraPraticheVenditaPagamentoBonifico")
    public ResponseEntity mostraPraticheVenditaPagamentoBonifico(@RequestBody @Valid Pagamento pagamento) {
        return new ResponseEntity(gestioneOrdine.mostraPraticheVenditaPerPagamentoBonifico(pagamento), HttpStatus.OK);
    }

    @PostMapping("/creaOrdineVendita")
    public ResponseEntity creaOrdineVendita(@RequestBody @Valid Cliente cliente, @RequestBody @Valid List<Auto> auto) {
        try {
            gestioneOrdine.ordineAuto(cliente, auto);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseMessage("ORDINE ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("CREATO"), HttpStatus.OK);
    }

    @GetMapping("/mostraStato")
    public ResponseEntity mostraStato(@RequestBody @Valid OrdineVendita ordineVendita) {
        return new ResponseEntity(gestioneOrdine.ordine(ordineVendita),HttpStatus.OK);
    }

    @PostMapping("/creaOrdineAcquisto/Auto")
    public ResponseEntity creaOrdineAcquistoAuto(@RequestBody @Valid String partitaIva, @RequestBody @Valid List<Auto> auto) {
        try {
            gestioneOrdine.creaOrdineAuto(partitaIva,auto);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseMessage("ORDINE ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("CREATO"), HttpStatus.OK);
    }

    @PostMapping("/creaOrdineAcquisto/Prodotto")
    public ResponseEntity creaOrdineAcquistoProdotto(@RequestBody @Valid String partitaIva, @RequestBody @Valid List<Prodotto> prodotto) {
        try {
            gestioneOrdine.creaOrdineProdotto(partitaIva, prodotto);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseMessage("ORDINE ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("CREATO"), HttpStatus.OK);
    }

    @PostMapping("/creaOrdineVendita/Preventivo")
    public ResponseEntity creaOrdineDaPreventivo(@RequestBody @Valid Preventivo preventivo) {
        try {
            gestioneOrdine.ordineDaPreventivo(preventivo);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseMessage("ORDINE ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("CREATO"), HttpStatus.OK);
    }


}
