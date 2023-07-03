package com.example.gestionaleauto.services;

import com.example.gestionaleauto.entities.Utente;
import com.example.gestionaleauto.repositories.UtenteRepository;
import com.example.gestionaleauto.util.ResponseMessage;
import com.example.gestionaleauto.util.exception.ClienteEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class GestioneUtente {
    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional(readOnly = false)
    public Utente aggiungiUtente(Utente utente) throws ClienteEsistenteException {
        if(utente.getId()!=-1 && utenteRepository.existsByCf(utente.getCf())){
            throw new ClienteEsistenteException();
        }
        utenteRepository.save(utente);
        return utente;
    }
    @Transactional(readOnly = true)
    public Boolean login(Utente utente){
        return utenteRepository.existsByEmailAndPassword(utente.getEmail(), utente.getPassword());
    }
    @Transactional(readOnly = false)
    public ResponseEntity<String> registrazione(Map<String, String> requestMap){
        try{
            if(loginValidato((requestMap))) {
                Utente utente= utenteRepository.findByEmail(requestMap.get("email"));
                if(Objects.isNull(utente)){
                    utenteRepository.save(getUserFromMap(requestMap));
                }
                if(utenteRepository.existsByCf(requestMap.get("cf"))){
                    return new ResponseEntity<>("ERRORE CF ESISTENTE", HttpStatus.BAD_REQUEST);
                }
            }else{
                return ResponseMessage.getResponseEntity("Invalid String", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.getResponseEntity("Invalid String", HttpStatus.BAD_REQUEST);
    }
    private boolean loginValidato(Map<String, String> requestMap){
       if(requestMap.containsKey("nome") && requestMap.containsKey("email")
                && requestMap.containsKey("password")  && requestMap.containsKey("cf")){
           return true;
       }
       return false;
    }

    private Utente getUserFromMap(Map<String, String> requestMap){
       Utente utente=new Utente();
       utente.setNome(requestMap.get("nome"));
       utente.setPassword(requestMap.get("password"));
       utente.setCf(requestMap.get("cf"));
       utente.setEmail(requestMap.get("email"));
       utente.setStatus("false");
       utente.setPermessi("utente");
       return utente;
    }
}
