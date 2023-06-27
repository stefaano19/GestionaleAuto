package com.example.gestionaleauto.services;

import com.example.gestionaleauto.entities.*;
import com.example.gestionaleauto.repositories.*;
import com.example.gestionaleauto.util.Pagamento;
import com.example.gestionaleauto.util.Stato;
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
    public OrdineVendita ordineDaPreventivo(Preventivo preventivo) throws IllegalAccessException{
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
            Collection<OrdineVendita> ordiniVendita=auto.getOrdiniVendita();
            ordiniVendita.add(ordineVendita);
            auto.setOrdiniVendita(ordiniVendita);
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

    @Transactional(readOnly = true)
    public List<OrdineAcquisto> ordiniNonConformi(){
        return ordineAcquistoRepository.findAllByConformeIsFalse();
    }

    @Transactional(readOnly = false)
    public OrdineAcquisto aggiornaOrdine(OrdineAcquisto ordineAcquisto, boolean conforme){
        ordineAcquisto.setConforme(conforme);
        ordineAcquistoRepository.save(ordineAcquisto);
        entityManager.refresh(ordineAcquisto);
        return ordineAcquisto;
    }

    @Transactional(readOnly = true)
    public List<PraticheVendita> mostraPraticheVendita(){
        return praticheVenditaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PraticheVendita> mostraPraticheVenditaPerPagamentoRate(Pagamento pagamento){
        return praticheVenditaRepository.findAllByPagamento(Pagamento.PAGAMENTO_A_RATE);
    }

    @Transactional(readOnly = true)
    public List<PraticheVendita> mostraPraticheVenditaPerPagamentoContanti(Pagamento pagamento){
        return praticheVenditaRepository.findAllByPagamento(Pagamento.CONTANTI);
    }
    @Transactional(readOnly = true)
    public List<PraticheVendita> mostraPraticheVenditaPerPagamentoAssegno(Pagamento pagamento){
        return praticheVenditaRepository.findAllByPagamento(Pagamento.ASSEGNO);
    }
    @Transactional(readOnly = true)
    public List<PraticheVendita> mostraPraticheVenditaPerPagamentoBonifico(Pagamento pagamento){
        return praticheVenditaRepository.findAllByPagamento(Pagamento.BONIFICO);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public OrdineVendita ordineAuto(Cliente cliente, List<Auto> auto) throws IllegalAccessException {
        OrdineVendita ordineVendita = new OrdineVendita();
        ordineVendita.setDataOrdine(new Date(System.currentTimeMillis()));
        double importo = 0;
        for (Auto a : auto) {
            importo += a.getPrezzo();
        }
        ordineVendita.setImporto(importo);
        ordineVendita.setConsegnaAdomicilio(true);
        ordineVendita.setCliente(cliente);
        for (Auto a : auto) {
            if (autoRepository.existsById(a.getId())) {
                Auto a1 = autoRepository.findById(a.getId()).get();
                a1.setQuantità(a1.getQuantità() - 1);
                Collection<OrdineVendita> ordiniVendita=a1.getOrdiniVendita();
                ordiniVendita.add(ordineVendita);
                a1.setOrdiniVendita(ordiniVendita);
                autoRepository.save(a1);
                entityManager.refresh(a);
                List<Auto> list = new ArrayList<>();
                list.add(a);
                ordineVendita.setAuto(list);
                ordineVenditaRepository.save(ordineVendita);
                entityManager.refresh(ordineVendita);
            }
        }
        return ordineVendita;
    }

    public List<OrdineVendita> ordiniVendita() {
        return ordineVenditaRepository.findAll();
    }

    public List<OrdineAcquisto> ordiniAcquisto() {
        return ordineAcquistoRepository.findAll();
    }
}
