package com.example.gestionaleauto.services;

import com.example.gestionaleauto.entities.Auto;
import com.example.gestionaleauto.entities.CasaProduttrice;
import com.example.gestionaleauto.repositories.AutoRepository;
import com.example.gestionaleauto.repositories.CasaProduttriceRepository;
import com.example.gestionaleauto.util.exception.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class GestioneCasaProduttrice {
    @Autowired
    private CasaProduttriceRepository casaProduttriceRepository;
    @Autowired
    private AutoRepository autoRepository;
    private EntityManager entityManager;
    final double PREZZO_MAX=1000000000;
    final String SPAZIO="";
    final int QUANTITA_MAX=200;
    final int QUANTITA_MIN=1;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public CasaProduttrice creaCasaProduttrice(CasaProduttrice casaProduttrice) throws CasaProduttriceEsistenteException {
        if(casaProduttrice.getId()!=-1 && casaProduttriceRepository.existsById(casaProduttrice.getId())) {
            throw new CasaProduttriceEsistenteException();
        }
        casaProduttrice.setRagioneSociale(casaProduttrice.getRagioneSociale()+SPAZIO);
        return casaProduttriceRepository.save(casaProduttrice);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public CasaProduttrice creaCasaProduttrice(String ... v){
        String partitaIva=v[0];
        String ragioneSociale=v[1];
        String sede=v[2];
        String marchio = v[3];
        CasaProduttrice casaProduttrice = new CasaProduttrice();
        casaProduttrice.setPartitaIva(partitaIva);
        casaProduttrice.setRagioneSociale(ragioneSociale);
        if(sede != ""){
            casaProduttrice.setSede(sede);
            casaProduttrice.setMarchio(marchio);
        }
        return casaProduttriceRepository.save(casaProduttrice);
    }

    @Transactional(readOnly = true)
    public List<CasaProduttrice> mostraCasaProduttrice() {
        return casaProduttriceRepository.findAllByRagioneSocialeContainingOrderByRagioneSocialeAsc(SPAZIO);
    }

    @Transactional(readOnly = true)
    public List<CasaProduttrice> mostraCasaProduttricePerSede(String sede){
        return casaProduttriceRepository.findAllBySedeContainingOrderBySedeAscRagioneSocialeAsc(sede);
    }

    @Transactional(readOnly = true)
    public List<CasaProduttrice> mostraCasaProduttricePerPartitaIva(String partitaIva){
        return casaProduttriceRepository.findAllByPartitaIvaContainingOrderByPartitaIvaAsc(partitaIva);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public CasaProduttrice aggiornaCasaProduttrice(String ragioneSociale, String partitaIva) throws CasaProduttriceNonEsistenteException {
        CasaProduttrice aggiornata = casaProduttriceRepository.findByRagioneSocialeContainingIgnoreCase(ragioneSociale);
        if( aggiornata == null){
            throw new CasaProduttriceNonEsistenteException();
        }
        aggiornata.setPartitaIva(partitaIva);
        casaProduttriceRepository.save(aggiornata);
        entityManager.refresh(aggiornata);
        return aggiornata;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaCasaProduttrice(String ragioneSociale){
        CasaProduttrice cancellata=casaProduttriceRepository.findByRagioneSocialeContainingIgnoreCase(ragioneSociale);
        if(cancellata != null){
            casaProduttriceRepository.delete(cancellata);
        }
        return true;
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaCasaProduttrice(int id){
        Optional<CasaProduttrice> cancellata=casaProduttriceRepository.findById(id);
        if(cancellata.isPresent()){
            casaProduttriceRepository.delete(cancellata.get());
        }
        return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean cancellaCasaProduttrice(CasaProduttrice casaProduttrice){
        return cancellaCasaProduttrice(casaProduttrice.getId());
    }


    public List<Auto> autoCasaProduttrice(CasaProduttrice casaProduttrice){
        if(casaProduttriceRepository.existsById(casaProduttrice.getId())){
            return autoRepository.findAllByCasaProduttrice_Id(casaProduttrice.getId());
        }
        return new ArrayList();
    }

    public List<Auto> autoCasaProduttrice(String ragioneSociale){
        if(casaProduttriceRepository.findByRagioneSocialeContainingIgnoreCase(ragioneSociale)!=null){
            return autoRepository.findAllByCasaProduttrice_RagioneSociale(ragioneSociale);
        }
        return new ArrayList<>();
    }

}
