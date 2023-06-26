package com.example.gestionaleauto.Services;

import com.example.gestionaleauto.Entities.Fattura;
import com.example.gestionaleauto.Entities.OrdineVendita;
import com.example.gestionaleauto.Repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class GestioneFattura {
    @Autowired
    private FatturaRepository fatturaRepository;

    @Transactional(readOnly = false)
    public Fattura creaFattura(Fattura fattura, OrdineVendita ordineVendita){
        if(fattura.getId()!=-1 && fatturaRepository.existsById(fattura.getId())){
            Fattura trovata = fatturaRepository.findFatturaById(fattura.getId());
            trovata.setOrdineVendita(ordineVendita);
            fatturaRepository.save(fattura);
            return fattura;
        }else{
            fatturaRepository.save(fattura);
        }
        return fattura;
    }
}
