package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    public List<Cliente> findAll();
}
