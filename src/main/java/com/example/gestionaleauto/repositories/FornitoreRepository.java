package com.example.gestionaleauto.repositories;

import com.example.gestionaleauto.entities.Fornitore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FornitoreRepository extends JpaRepository<Fornitore, Integer> {
    List<Fornitore> findAllByRagioneSocialeContainingOrderByRagioneSocialeAsc(String ragioneSociale);
    List<Fornitore> findAllBySedeContainingOrderBySedeAscRagioneSocialeAsc(String sede);
    List<Fornitore> findAllByPartitaIvaContainingOrderByPartitaIvaAsc(String partitaIva);
    Fornitore findByRagioneSocialeContainingIgnoreCase(String ragioneSociale);


    Fornitore findByPartitaIvaContainingIgnoreCase(String partitaIva);
}
