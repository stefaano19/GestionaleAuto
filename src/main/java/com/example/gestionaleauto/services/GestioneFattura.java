package com.example.gestionaleauto.services;

import com.example.gestionaleauto.entities.Fattura;
import com.example.gestionaleauto.entities.OrdineVendita;
import com.example.gestionaleauto.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class GestioneFattura {
    @Autowired
    private FatturaRepository fatturaRepository;

    private final double IMPORTO_MAX=1000000000;
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
    @Transactional(readOnly = true)
    public List<Fattura> mostraFattureScadenza30(){
        Date date=new Date();
        LocalDateTime localDateTime=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date data=Date.from(localDateTime.plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        return fatturaRepository.findAllByDataScadenzaIsLessThanEqual(data);

    }
    @Transactional(readOnly = true)
    public List<Fattura> mostraFatturaPerDataEmissione(){
        Date date=new Date();
        LocalDateTime localDateTime=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date data=Date.from(localDateTime.minusYears(200).atZone(ZoneId.systemDefault()).toInstant());
        return fatturaRepository.findAllByDataEmissioneIsAfterOrderByDataEmissioneAsc(data);
    }

    @Transactional(readOnly = true)
    public List<Fattura> mostraFatturaPerDataScadenza(){
        Date date=new Date();
        LocalDateTime localDateTime=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date data=Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return fatturaRepository.findAllByDataScadenzaIsAfterOrderByDataEmissioneAsc(data);
    }

    @Transactional(readOnly = true)
    public List<Fattura> mostraFatturaPerImporto(){
        return fatturaRepository.findAllByImportoIsLessThanOrderByImportoAsc(IMPORTO_MAX);
    }
}
