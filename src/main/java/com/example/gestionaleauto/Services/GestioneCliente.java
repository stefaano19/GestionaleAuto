package com.example.gestionaleauto.Services;

import com.example.gestionaleauto.Entities.Cliente;
import com.example.gestionaleauto.Repositories.ClienteRepository;
import com.example.gestionaleauto.Util.Exception.ClienteEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GestioneCliente {
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Cliente> mostraClienti(){
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Cliente aggiungiCliente(Cliente cliente) throws ClienteEsistenteException {
        if(cliente.getId()!=-1 && clienteRepository.existsById(cliente.getId())){
            throw new ClienteEsistenteException();
        }
        clienteRepository.save(cliente);
        return cliente;
    }

}
