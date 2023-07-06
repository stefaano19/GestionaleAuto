package com.example.gestionaleauto.services;

import com.example.gestionaleauto.entities.Utente;
import com.example.gestionaleauto.repositories.UtenteRepository;
import com.example.gestionaleauto.util.QueryToXML;
import com.example.gestionaleauto.util.ResponseMessage;
import com.example.gestionaleauto.util.exception.ClienteEsistenteException;
import com.example.gestionaleauto.util.exception.UtenteEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

/**
 *
 */
@Service
public class GestioneUtente {
    @Autowired
    private UtenteRepository utenteRepository;

    String query="SELECT * FROM utente WHERE permessi='utente'";
    QueryToXML queryToXML=new QueryToXML();
    String path="C:\\Users\\stefa\\Downloads\\SpringProjectPSW-master\\GestionaleAuto\\src\\XML\\query.xml";

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
    public String registrazione(Map<String, String> requestMap) throws UtenteEsistenteException{
        try{
            if(loginValidato((requestMap))) {
                String CF= requestMap.get("cf").toLowerCase();
                if(utenteRepository.existsByCf(CF)){
                    return "ERRORE";
                }
                Utente utente= utenteRepository.findByCf(CF);
                if(Objects.isNull(utente)){
                    requestMap.replace("cf", CF);
                    utenteRepository.save(getUserFromMap(requestMap));
                    queryToXML.connection(query, path);
                    return "AGGIUNTO";
                }
            }else{
                return "ERRORE";
            }
        }catch(Exception e){}
        return "ERRORE";
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
