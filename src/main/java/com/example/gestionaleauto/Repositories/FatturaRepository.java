package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Integer> {

    Fattura findFatturaById(int id);
}
