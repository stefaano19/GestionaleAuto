package com.example.gestionaleauto.repositories;

import com.example.gestionaleauto.entities.AddettoTrasporto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddettoTrasportoRepository extends JpaRepository<AddettoTrasporto, Integer> {
    AddettoTrasporto findByRagioneSocialeContaining(String ragioneSociale);
    List<AddettoTrasporto> findAll();
}
