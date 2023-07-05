package com.example.gestionaleauto.services;

import com.example.gestionaleauto.entities.Preventivo;
import com.example.gestionaleauto.repositories.PreventivoRepository;
import com.example.gestionaleauto.util.exception.PreventivoEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
public class GestionePreventivo {
    @Autowired
    private PreventivoRepository preventivoRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Preventivo creaPreventivo(Preventivo preventivo) throws PreventivoEsistenteException{
        if(preventivo.getId()!=-1 && preventivoRepository.existsById(preventivo.getId())){
            throw new PreventivoEsistenteException();
        }
        preventivoRepository.save(preventivo);
        return preventivo;
    }

    @Transactional(readOnly = true)
    public List<Preventivo> mostraPreventivi(){
        return preventivoRepository.findAll();
    }



}
