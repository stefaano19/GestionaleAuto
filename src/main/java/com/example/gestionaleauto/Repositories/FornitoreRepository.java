package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.Fornitore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FornitoreRepository extends JpaRepository<Fornitore, Integer> {
    List<Fornitore> findAllByRagioneSocialeOrderByRagioneSocialeAsc();
    List<Fornitore> findAllBySedeOrderBySedeAscRagioneSocialeAsc();
    List<Fornitore> findAllByPartitaIvaOrderByPartitaIvaRagioneSociale();
    Fornitore findByRagioneSocialeContainingIgnoreCase(String ragioneSociale);


    Fornitore findByPartitaIvaContainingIgnoreCase(String partitaIva);
}
