package com.example.gestionaleauto.repositories;

import com.example.gestionaleauto.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Utente findByCf(String cf);
    boolean existsByEmail(String email);
    Utente findByEmail(@Param("email") String email);
    boolean existsByCf(String cf);
    boolean existsByEmailAndPassword(String email, String password);
}
