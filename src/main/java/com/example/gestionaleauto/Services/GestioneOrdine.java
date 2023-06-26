package com.example.gestionaleauto.Services;

import com.example.gestionaleauto.Entities.*;
import com.example.gestionaleauto.Repositories.*;
import com.example.gestionaleauto.Util.Stato;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GestioneOrdine {
    @Autowired
    private OrdineVenditaRepository ordineVenditaRepository;
    @Autowired
    private OrdineAcquistoRepository ordineAcquistoRepository;
    @Autowired
    private CasaProduttriceRepository casaProduttriceRepository;
    @Autowired
    private PraticheVenditaRepository praticheVenditaRepository;

    @Autowired
    private FornitoreRepository fornitoreRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private PreventivoRepository preventivoRepository;

    @Autowired
    private AutoRepository autoRepository;
    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public OrdineAcquisto creaOrdineAuto(String partitaIva, List<Auto> auto){
        CasaProduttrice casaProduttrice=casaProduttriceRepository.findByPartitaIvaContainingIgnoreCase(partitaIva);
        OrdineAcquisto ordineAcquisto=new OrdineAcquisto();
        ordineAcquisto.setDataOrdine(new Date(System.currentTimeMillis()));
        ordineAcquisto.setCasaProduttrice(casaProduttrice);
        List<Auto> acquistate = new ArrayList<>();
        for(Auto a: auto){
            Optional<Auto> acquistata = autoRepository.findById(a.getId());
            if(acquistata.isEmpty()){
                acquistata.get().setQuantità(acquistata.get().getQuantità()+ a.getQuantità());
                ordineAcquisto.setImporto(ordineAcquisto.getImporto()+acquistata.get().getPrezzo());
                acquistate.add(a);
            }else{
                autoRepository.save(a);
            }
        }
        ordineAcquisto.setAuto(acquistate);
        entityManager.refresh(ordineAcquisto);
        for(Auto a: auto){
            Collection<OrdineAcquisto> ordiniAcquisto= a.getOrdiniAcquisto();
            ordiniAcquisto.add(ordineAcquisto);
            a.setOrdiniAcquisto(ordiniAcquisto);
            autoRepository.save(a);
        }
        ordineAcquistoRepository.save(ordineAcquisto);
        return ordineAcquisto;

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public OrdineVendita ordinePreventivo(Preventivo preventivo) throws IllegalAccessException{
        if(preventivo.getId()!=-1 && preventivoRepository.existsById(preventivo.getId())){
            throw new IllegalAccessException();
        }
        OrdineVendita ordineVendita= new OrdineVendita();
        ordineVendita.setDataOrdine(new Date(System.currentTimeMillis()));
        ordineVendita.setImporto(preventivo.getPrezzo());
        ordineVendita.setConsegnaAdomicilio(true);
        ordineVendita.setCliente(preventivo.getCliente());
        Auto auto= preventivo.getAuto();
        if(autoRepository.existsById(auto.getId())) {
            auto=autoRepository.findById(preventivo.getAuto().getId()).get();
            auto.setQuantità(auto.getQuantità()-1);
            autoRepository.save(auto);
            entityManager.refresh(auto);
            List<Auto> a = new ArrayList<>();
            a.add(auto);
            ordineVendita.setAuto(a);
            ordineVenditaRepository.save(ordineVendita);
            entityManager.refresh(ordineVendita);
        }
        return ordineVendita;
    }

    @Transactional(readOnly = true)
    public Stato ordine(OrdineVendita ordineVendita){
        if(ordineVendita.getId()!=-1 && ordineVenditaRepository.existsById(ordineVendita.getId())){
            PraticheVendita pratiche = praticheVenditaRepository.findAllByOrdineVendita(ordineVendita);
            return pratiche.getStato();
        }
        return null;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public OrdineAcquisto creaOrdineProdotto(String partitaIva, List<Prodotto> prodotto){
        Fornitore fornitore=fornitoreRepository.findByPartitaIvaContainingIgnoreCase(partitaIva);
        OrdineAcquisto ordineAcquisto=new OrdineAcquisto();
        ordineAcquisto.setDataOrdine(new Date(System.currentTimeMillis()));
        ordineAcquisto.setFornitore(fornitore);
        List<Prodotto> acquistati = new ArrayList<>();
        for(Prodotto p: prodotto){
            Optional<Prodotto> acquistato = prodottoRepository.findById(p.getId());
            if(acquistato.isEmpty()){
                acquistato.get().setDisponibilità(acquistato.get().getDisponibilità()+ p.getDisponibilità());
                ordineAcquisto.setImporto(ordineAcquisto.getImporto()+acquistato.get().getPrezzo());
                acquistati.add(p);
            }else{
                prodottoRepository.save(p);
            }
        }
        ordineAcquisto.setProdotto(acquistati);
        entityManager.refresh(ordineAcquisto);
        for(Prodotto p: prodotto){
            Collection<OrdineAcquisto> ordiniAcquisto= p.getOrdiniAcquisto();
            ordiniAcquisto.add(ordineAcquisto);
            p.setOrdiniAcquisto(ordiniAcquisto);
            prodottoRepository.save(p);
        }
        ordineAcquistoRepository.save(ordineAcquisto);
        return ordineAcquisto;
    }







}
