package com.example.gestionaleauto.Services;

import com.example.gestionaleauto.Entities.Auto;
import com.example.gestionaleauto.Repositories.AutoRepository;
import com.example.gestionaleauto.Util.Exception.AutoNonEsistenteException;
import com.example.gestionaleauto.Util.Exception.ProdottoEsistenteException;
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

    @Transactional(readOnly = true)
    public List<Auto> mostraAutoDaAcquistare(){
        return autoRepository.findAllByQuantitàIsLessThanOrderByTipologiaAutoAsc(1);
    }

    @Transactional(readOnly = true)
    public List<Auto> mostraAuto() {
        return autoRepository.findAllByModelloOrderByModelloAsc();
    }

    @Transactional(readOnly = true)
    public List<Auto> mostraAutoPerTipologia(){
        return autoRepository.findAllByTipologiaOrderByTipologiaAscModelloAsc();
    }

    @Transactional(readOnly = true)
    public List<Auto> mostraAutoPerPrezzo(){
        return autoRepository.findAllByPrezzoOrderByPrezzoAscModelloAsc();
    }
    @Transactional(readOnly = true)
    public List<Auto> mostraAutoPerDisponibilità(){
        return autoRepository.findAllByDisponibilitàOrderByDisponibilitàAscModelloAsc();
    }
    @Transactional(readOnly = false)
    public List<Auto> mostraAutoDaAcquistarePerModello(){
        return autoRepository.findAllByQuantitàIsLessThanOrderByModelloAsc(1);
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Auto creaAuto(Auto auto) throws ProdottoEsistenteException {
        if(auto.getId()!=-1 && autoRepository.existsById(auto.getId())) {
            throw new ProdottoEsistenteException();
        }
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
