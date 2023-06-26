package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.OrdineVendita;
import com.example.gestionaleauto.Entities.PraticheVendita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PraticheVenditaRepository extends JpaRepository<PraticheVendita, Integer> {
    PraticheVendita findAllByOrdineVendita(OrdineVendita ordineVendita);
}
