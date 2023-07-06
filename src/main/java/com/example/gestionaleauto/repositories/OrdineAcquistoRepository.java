package com.example.gestionaleauto.repositories;

import com.example.gestionaleauto.entities.OrdineAcquisto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface OrdineAcquistoRepository extends JpaRepository<OrdineAcquisto, Integer> {
    List<OrdineAcquisto> findAllByConformeIsFalse();
    List<OrdineAcquisto> findDistinctByCasaProduttriceIsNull();
    List<OrdineAcquisto> findDistinctByFornitoreIsNull();
}
