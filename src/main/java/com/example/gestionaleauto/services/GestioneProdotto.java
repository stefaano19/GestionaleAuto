package com.example.gestionaleauto.services;

import com.example.gestionaleauto.entities.Auto;
import com.example.gestionaleauto.entities.CasaProduttrice;
import com.example.gestionaleauto.entities.Fornitore;
import com.example.gestionaleauto.entities.Prodotto;
import com.example.gestionaleauto.repositories.FornitoreRepository;
import com.example.gestionaleauto.repositories.ProdottoRepository;
import com.example.gestionaleauto.util.exception.AutoEsistenteException;
import com.example.gestionaleauto.util.exception.ProdottoEsistenteException;
import com.example.gestionaleauto.util.exception.ProdottoNonEsistenteException;
import com.example.gestionaleauto.util.TipologiaProdotto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class GestioneProdotto {

    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private FornitoreRepository fornitoreRepository;

    private EntityManager entityManager;

    final double PREZZO_MAX=1000000000;
    final String SPAZIO="";
    final int DISPONIBILITA_MAX=200;
    final int DISPONIBILITA_MIN=1;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Prodotto creaProdotto(Prodotto prodotto) throws AutoEsistenteException {
        Fornitore f=null;
        Optional<Fornitore> fornitore=fornitoreRepository.findById(prodotto.getCP_F());
        if(fornitore.isPresent()){
            f=fornitore.get();
            System.out.println(f);
        }
        prodotto.setFornitore(f);
        System.out.println(""+prodotto.toString());
        return prodottoRepository.save(prodotto);
    }

    @Transactional(readOnly = true)
    public List<Prodotto> mostraProdotti() {
        return prodottoRepository.findAllByNomeContainingOrderByNomeAscNomeAsc(SPAZIO);
    }

    @Transactional(readOnly = true)
    public List<Prodotto> mostraProdottiPerTipologia(TipologiaProdotto tipologiaProdotto){
        return prodottoRepository.findAllByTipologiaIsOrderByTipologiaAscNomeAsc(tipologiaProdotto);
    }

    @Transactional(readOnly = true)
    public List<Prodotto> mostraProdottiPerPrezzo(){
        return prodottoRepository.findAllByPrezzoIsLessThanOrderByPrezzoAscNomeAsc(PREZZO_MAX);
    }
    @Transactional(readOnly = true)
    public List<Prodotto> mostraProdottiPerDisponibilita(){
        return prodottoRepository.findAllByDisponibilitaIsLessThan(DISPONIBILITA_MIN);
    }



    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Prodotto aggiornaProdotto(String nome, double prezzo) throws ProdottoNonEsistenteException {
        Prodotto aggiornato = prodottoRepository.findByNomeContaining(nome);
        if( aggiornato == null){
            throw new ProdottoNonEsistenteException();
        }
        aggiornato.setPrezzo(prezzo);
        prodottoRepository.save(aggiornato);
        entityManager.refresh(aggiornato);
        return aggiornato;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaProdotto(String nome){
        Prodotto cancellato=prodottoRepository.findByNomeContaining(nome);
        if(cancellato != null){
            prodottoRepository.delete(cancellato);
        }
        return true;
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaProdotto(int id){
        Optional<Prodotto> cancellato=prodottoRepository.findById(id);
        if(cancellato.isPresent()){
            prodottoRepository.delete(cancellato.get());
        }
        return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaProdotto(Prodotto prodotto){
        return cancellaProdotto(prodotto.getId());
    }

    @Transactional(readOnly = true)
    public List<Prodotto> mostraProdottiManutenzione(){
        return prodottoRepository.findAllByTipologiaIs(TipologiaProdotto.MANUTENZIONE);
    }
    @Transactional(readOnly = false)
    public List<Prodotto> mostraProdottiDaOrdinare() {
        return prodottoRepository.findAllByDisponibilitaIsLessThanOrderByDisponibilitaAscNomeAsc(DISPONIBILITA_MIN);
    }

}
