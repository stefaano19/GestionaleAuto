package com.example.gestionaleauto.Services;

import com.example.gestionaleauto.Entities.Auto;
import com.example.gestionaleauto.Repositories.AutoRepository;
import com.example.gestionaleauto.Util.Exception.AutoEsistenteException;
import com.example.gestionaleauto.Util.Exception.AutoNonEsistenteException;
import com.example.gestionaleauto.Util.TipologiaAuto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class GestioneAuto {

    @Autowired
    private AutoRepository autoRepository;
    private EntityManager entityManager;
    final double PREZZO_MAX=1000000000;
    final String SPAZIO="";
    final int QUANTITA_MAX=200;
    final int QUANTITA_MIN=1;

    @Transactional(readOnly = true)
    public List<Auto> mostraAutoDaAcquistare(){
        return autoRepository.findAllByQuantitàIsLessThanOrderByTipologiaAutoAsc(1);
    }

    @Transactional(readOnly = true)
    public List<Auto> mostraAuto() {
        return autoRepository.findAllByModelloContainingOrderByModelloAsc(SPAZIO);
    }

    @Transactional(readOnly = true)
    public List<Auto> mostraAutoPerTipologia(TipologiaAuto tipologiaAuto){
        return autoRepository.findAllByTipologiaAutoIsOrderByTipologiaAutoAscModelloAsc(tipologiaAuto);
    }

    @Transactional(readOnly = true)
    public List<Auto> mostraAutoPerPrezzo(){
        return autoRepository.findAllByPrezzoIsLessThanOrderByPrezzoAscModelloAsc(PREZZO_MAX);
    }
    @Transactional(readOnly = true)
    public List<Auto> mostraAutoPerDisponibilità(){

        return autoRepository.findAllByQuantitàIsLessThanOrderByQuantitàAsc(QUANTITA_MAX);
    }
    @Transactional(readOnly = false)
    public List<Auto> mostraAutoDaAcquistarePerModello(){
        return autoRepository.findAllByQuantitàIsLessThanOrderByModelloAsc(QUANTITA_MIN);
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Auto creaAuto(Auto auto) throws AutoEsistenteException {
        if(auto.getId()!=-1 && autoRepository.existsById(auto.getId())) {
            throw new AutoEsistenteException();
        }
        auto.setModello(auto.getModello()+SPAZIO);
        return autoRepository.save(auto);
    }



    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Auto aggiornaAuto(String modello, double prezzo) throws AutoNonEsistenteException {
        Auto aggiornato = autoRepository.findByModelloContaining(modello);
        if( aggiornato == null){
            throw new AutoNonEsistenteException();
        }
        aggiornato.setPrezzo(prezzo);
        autoRepository.save(aggiornato);
        entityManager.refresh(aggiornato);
        return aggiornato;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Auto aggiornaShowroom(String modello, boolean showroom) throws AutoNonEsistenteException {
        Auto aggiornato = autoRepository.findByModelloContaining(modello);
        if( aggiornato == null){
            throw new AutoNonEsistenteException();
        }
        aggiornato.setShowroom(showroom);
        autoRepository.save(aggiornato);
        entityManager.refresh(aggiornato);
        return aggiornato;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaAuto(String modello){
        Auto cancellato =autoRepository.findByModelloContaining(modello);
        if(cancellato != null){
            autoRepository.delete(cancellato);
        }
        return true;
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaAuto(int id){
        Optional<Auto> cancellato=autoRepository.findById(id);
        if(cancellato.isPresent()){
            autoRepository.delete(cancellato.get());
        }
        return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaAuto(Auto auto){
        return cancellaAuto(auto.getId());
    }
}
