package com.example.gestionaleauto.Services;

import com.example.gestionaleauto.Entities.Prodotto;
import com.example.gestionaleauto.Repositories.ProdottoRepository;
import com.example.gestionaleauto.Util.Exception.ProdottoEsistenteException;
import com.example.gestionaleauto.Util.Exception.ProdottoNonEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Service
public class GestioneProdotto {

    @Autowired
    private ProdottoRepository prodottoRepository;

    private EntityManager entityManager;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Prodotto creaProdotto(Prodotto prodotto) throws ProdottoEsistenteException {
        if(prodotto.getId()!=-1 && prodottoRepository.existsById(prodotto.getId())) {
            throw new ProdottoEsistenteException();
        }
        return prodottoRepository.save(prodotto);
    }

    @Transactional(readOnly = true)
    public List<Prodotto> mostraProdotti() {
        return prodottoRepository.findAllByNomeOrderByNomeAscNomeAsc();
    }

    @Transactional(readOnly = true)
    public List<Prodotto> mostraProdottiPerTipologia(){
        return prodottoRepository.findAllByTipologiaOrderByTipologiaAscNomeAsc();
    }

    @Transactional(readOnly = true)
    public List<Prodotto> mostraProdottiPerPrezzo(){
        return prodottoRepository.findAllByPrezzoOrderByPrezzoAscNomeAsc();
    }
    @Transactional(readOnly = true)
    public List<Prodotto> mostraProdottiPerDisponibilità(){
        return prodottoRepository.findAllByDisponibilitàOrderByDisponibilitàAscNomeAsc();
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

}
