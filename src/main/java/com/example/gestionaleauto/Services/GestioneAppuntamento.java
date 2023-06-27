package com.example.gestionaleauto.Services;

import com.example.gestionaleauto.Entities.Appuntamento;
import com.example.gestionaleauto.Entities.Auto;
import com.example.gestionaleauto.Repositories.AppuntamentoRepository;
import com.example.gestionaleauto.Repositories.AutoRepository;
import com.example.gestionaleauto.Util.Exception.AppuntamentoEsistenteException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class GestioneAppuntamento {
    @Autowired
    private AppuntamentoRepository appuntamentoRepository;
    @Autowired
    private AutoRepository autoRepository;
    private EntityManager entityManager;
    @Transactional(readOnly = true)
    public List<Auto> autoDisponibiliData(Date date){
        List<Appuntamento> appuntamenti= appuntamentoRepository.findAllByDataEqualsAndTestDriveIsTrue(date);
        List<Auto> autoNonPrenotate= autoRepository.findAll();
        for(Appuntamento a: appuntamenti){
            Collection<Auto> autoPrenotate = a.getAuto();
            for(Auto autoPrenotata: autoPrenotate){
                if(autoNonPrenotate.contains(autoPrenotata)){
                    autoNonPrenotate.remove(autoPrenotata);
                }
            }
        }
        return autoNonPrenotate;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Appuntamento creaAppuntamento(Appuntamento appuntamento) throws AppuntamentoEsistenteException {
        if(appuntamento.getId()!=-1 && appuntamentoRepository.existsById(appuntamento.getId())){
            throw new AppuntamentoEsistenteException();
        }
        for(Auto a: appuntamento.getAuto()){
            Collection<Appuntamento> autoAppuntamenti=autoRepository.findById(a.getId()).get().getAppuntamenti();
            autoAppuntamenti.add(appuntamento);
            a.setAppuntamenti(autoAppuntamenti);
            autoRepository.save(a);
            entityManager.refresh(a);
        }
        appuntamentoRepository.save(appuntamento);
        return appuntamento;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Appuntamento creaAppuntamento(Date date, List<Auto> auto, boolean testDrive) throws AppuntamentoEsistenteException {
        Appuntamento appuntamento=new Appuntamento();
        appuntamento.setAuto(auto);
        appuntamento.setTestDrive(testDrive);
        appuntamento.setData(date);
        for(Auto a: auto){
            Collection<Appuntamento> autoAppuntamenti=autoRepository.findById(a.getId()).get().getAppuntamenti();
            autoAppuntamenti.add(appuntamento);
            a.setAppuntamenti(autoAppuntamenti);
            autoRepository.save(a);
            entityManager.refresh(a);
        }
        appuntamentoRepository.save(appuntamento);
        return appuntamento;
    }

    @Transactional(readOnly = true)
    public List<Appuntamento> mostraAppuntamenti(){
        return appuntamentoRepository.findAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Appuntamento rimuoviAppuntamento(Appuntamento appuntamento){
        appuntamentoRepository.delete(appuntamento);
        for(Auto a: appuntamento.getAuto()){
            Collection<Appuntamento> autoAppuntamenti=autoRepository.findById(a.getId()).get().getAppuntamenti();
            autoAppuntamenti.remove(appuntamento);
            a.setAppuntamenti(autoAppuntamenti);
            autoRepository.save(a);
            entityManager.refresh(a);
        }
        return appuntamento;
    }



}
