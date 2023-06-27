package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.CasaProduttrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasaProduttriceRepository extends JpaRepository<CasaProduttrice,Integer> {
    List<CasaProduttrice> findAll();

    List<CasaProduttrice> findAllByRagioneSocialeContainingOrderByRagioneSocialeAsc(String casaProduttrice);

    List<CasaProduttrice> findAllBySedeContainingOrderBySedeAscRagioneSocialeAsc(String sede);

    List<CasaProduttrice> findAllByPartitaIvaContainingOrderByPartitaIvaAsc(String partitaIva);

    CasaProduttrice findByRagioneSocialeContainingIgnoreCase(String ragioneSociale);
    CasaProduttrice findByPartitaIvaContainingIgnoreCase(String partitaIva);
}
