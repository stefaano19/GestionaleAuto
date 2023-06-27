package com.example.gestionaleauto.repositories;

import com.example.gestionaleauto.entities.Appuntamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Integer> {
    List<Appuntamento> findAllByDataEqualsAndTestDriveIsTrue(Date date);
}
