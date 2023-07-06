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

/**
 *
 */
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
    public OrdineAcquisto creaOrdineAuto(OrdineAcquisto ordineAcquisto){
        for( Auto a: ordineAcquisto.getAuto()){
        }
        System.out.println(ordineAcquisto);
        OrdineAcquisto o=new OrdineAcquisto();
        o.setDataOrdine(ordineAcquisto.getDataOrdine());
        o.setConforme(ordineAcquisto.isConforme());
        Optional<CasaProduttrice> casaProduttrice=casaProduttriceRepository.findById(ordineAcquisto.getCp_C());
        o.setCasaProduttrice(casaProduttrice.get());
        System.out.println(ordineAcquisto);
        ordineAcquistoRepository.save(o);
        for(Auto a: ordineAcquisto.getAuto()){
            Optional<Auto> acquistate= autoRepository.findById(a.getId());
            Auto acquistata= acquistate.get();
            acquistata.setQuantita(acquistata.getQuantita()+1);
            autoRepository.save(acquistata);
        }
        List<Auto> acquistate = new ArrayList<>();
        for(Auto a: ordineAcquisto.getAuto()){
            Optional<Auto> acquistata= autoRepository.findById(a.getId());
            acquistate.add(acquistata.get());
            o.setImporto(o.getImporto()+acquistata.get().getPrezzo());
            System.out.println("siu");
        }
        o.setAuto(acquistate);
        System.out.println("Ciao"+o);
        ordineAcquistoRepository.save(o);
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
            auto.setQuantita(auto.getQuantita()-1);
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
    public OrdineAcquisto creaOrdineProdotto(OrdineAcquisto ordineAcquisto){
        for( Prodotto p: ordineAcquisto.getProdotto()){
        }
        System.out.println(ordineAcquisto);
        OrdineAcquisto o=new OrdineAcquisto();
        o.setDataOrdine(ordineAcquisto.getDataOrdine());
        o.setConforme(ordineAcquisto.isConforme());
        Optional<Fornitore> fornitore=fornitoreRepository.findById(ordineAcquisto.getCp_F());
        o.setFornitore(fornitore.get());
        System.out.println(ordineAcquisto);
        ordineAcquistoRepository.save(o);
        for(Prodotto p: ordineAcquisto.getProdotto()){
            Optional<Prodotto> acquistate= prodottoRepository.findById(p.getId());
            Prodotto acquistata= acquistate.get();
            acquistata.setDisponibilita(acquistata.getDisponibilita()+1);
            prodottoRepository.save(acquistata);
        }
        List<Prodotto> acquistate = new ArrayList<>();
        for(Prodotto p: ordineAcquisto.getProdotto()){
            Optional<Prodotto> acquistata= prodottoRepository.findById(p.getId());
            acquistate.add(acquistata.get());
            o.setImporto(o.getImporto()+acquistata.get().getPrezzo());
            System.out.println("siu");
        }
        o.setProdotto(acquistate);
        System.out.println("Ciao"+o);
        ordineAcquistoRepository.save(o);
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
                a1.setQuantita(a1.getQuantita() - 1);
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

    public List<OrdineAcquisto> ordiniAcquistoProdotti() {
        return ordineAcquistoRepository.findDistinctByCasaProduttriceIsNull();
    }

    public List<OrdineAcquisto> ordiniAcquistoAuto() {
        return ordineAcquistoRepository.findDistinctByFornitoreIsNull();
    }
}
