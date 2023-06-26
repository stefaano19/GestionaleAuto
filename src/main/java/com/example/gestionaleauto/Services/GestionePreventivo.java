package com.example.gestionaleauto.Services;

import com.example.gestionaleauto.Entities.Auto;
import com.example.gestionaleauto.Entities.OrdineVendita;
import com.example.gestionaleauto.Entities.Preventivo;
import com.example.gestionaleauto.Repositories.PreventivoRepository;
import com.example.gestionaleauto.Util.Exception.PreventivoEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GestionePreventivo {
    @Autowired
    private PreventivoRepository preventivoRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Preventivo creaPreventivo(Preventivo preventivo) throws  PreventivoEsistenteException{
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
