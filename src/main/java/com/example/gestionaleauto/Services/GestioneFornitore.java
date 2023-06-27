package com.example.gestionaleauto.Services;

import com.example.gestionaleauto.Entities.Auto;
import com.example.gestionaleauto.Entities.Fornitore;
import com.example.gestionaleauto.Entities.Prodotto;
import com.example.gestionaleauto.Repositories.FornitoreRepository;
import com.example.gestionaleauto.Repositories.ProdottoRepository;
import com.example.gestionaleauto.Util.Exception.FornitoreEsistenteException;
import com.example.gestionaleauto.Util.Exception.FornitoreNonEsistenteException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GestioneFornitore {
    @Autowired
    private FornitoreRepository fornitoreRepository;
    private EntityManager entityManager;
    @Autowired
    private ProdottoRepository prodottoRepository;

    final String SPAZIO="";

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Fornitore creaFornitore(Fornitore fornitore) throws FornitoreEsistenteException {
        if(fornitore.getId()!=-1 && fornitoreRepository.existsById(fornitore.getId())) {
            throw new FornitoreEsistenteException();
        }
        fornitore.setRagioneSociale(fornitore.getRagioneSociale()+SPAZIO);
        return fornitoreRepository.save(fornitore);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Fornitore creaFornitore(String ... v) {
        String partitaIva=v[0];
        String ragioneSociale=v[1];
        String sede=v[2];
        Fornitore fornitore= new Fornitore();
        fornitore.setPartitaIva(partitaIva);
        fornitore.setRagioneSociale(ragioneSociale);
        if(sede != ""){
            fornitore.setSede(sede);
        }
        return fornitoreRepository.save(fornitore);
    }

    @Transactional(readOnly = true)
    public List<Fornitore> mostraFornitori() {
        return fornitoreRepository.findAllByRagioneSocialeContainingOrderByRagioneSocialeAsc(SPAZIO);
    }

    @Transactional(readOnly = true)
    public List<Fornitore> mostraFornitoriPerSede(String sede){
        return fornitoreRepository.findAllBySedeContainingOrderBySedeAscRagioneSocialeAsc(sede);
    }

    @Transactional(readOnly = true)
    public List<Fornitore> mostraFornitoriPerPartitaIva(String partitaIva){
        return fornitoreRepository.findAllByPartitaIvaContainingOrderByPartitaIvaAsc(partitaIva);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Fornitore aggiornaFornitore(String ragioneSociale, String partitaIva) throws FornitoreNonEsistenteException {
        Fornitore aggiornato = fornitoreRepository.findByRagioneSocialeContainingIgnoreCase(ragioneSociale);
        if( aggiornato == null){
            throw new FornitoreNonEsistenteException();
        }
        aggiornato.setPartitaIva(partitaIva);
        fornitoreRepository.save(aggiornato);
        entityManager.refresh(aggiornato);
        return aggiornato;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaFornitore(String ragioneSociale){
        Fornitore cancellato=fornitoreRepository.findByRagioneSocialeContainingIgnoreCase(ragioneSociale);
        if(cancellato != null){
            fornitoreRepository.delete(cancellato);
        }
        return true;
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaFornitore(int id){
        Optional<Fornitore> cancellato=fornitoreRepository.findById(id);
        if(cancellato.isPresent()){
            fornitoreRepository.delete(cancellato.get());
        }
        return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaFornitore(Fornitore fornitore){
        return cancellaFornitore(fornitore.getId());
    }



    public List<Prodotto> prodottiFornitore(Fornitore fornitore){
        if(fornitoreRepository.existsById(fornitore.getId())){
            return prodottoRepository.findProdottoByFornitore_Id(fornitore.getId());
        }
        return new ArrayList();
    }

    public List<Prodotto> prodottoFornitore(String ragioneSociale){
        if(fornitoreRepository.findByRagioneSocialeContainingIgnoreCase(ragioneSociale)!=null){
            return prodottoRepository.findAllByFornitore_RagioneSociale(ragioneSociale);
        }
        return new ArrayList<>();
    }




}
