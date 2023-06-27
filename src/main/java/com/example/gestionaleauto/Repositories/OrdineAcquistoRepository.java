package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.OrdineAcquisto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdineAcquistoRepository extends JpaRepository<OrdineAcquisto, Integer> {
    List<OrdineAcquisto> findAllByConformeIsFalse();
}
