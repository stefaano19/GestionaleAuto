package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.Preventivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreventivoRepository extends JpaRepository<Preventivo, Integer> {

}
