package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.AddettoTrasporto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddettoTrasportoRepository extends JpaRepository<AddettoTrasporto, Integer> {
    AddettoTrasporto findByNome(String nome);
    List<AddettoTrasporto> findALL();
}
