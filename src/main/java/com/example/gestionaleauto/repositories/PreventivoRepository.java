package com.example.gestionaleauto.repositories;

import com.example.gestionaleauto.entities.Preventivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreventivoRepository extends JpaRepository<Preventivo, Integer> {

}
