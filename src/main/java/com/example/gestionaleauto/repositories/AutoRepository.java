package com.example.gestionaleauto.repositories;

import com.example.gestionaleauto.entities.Auto;
import com.example.gestionaleauto.util.TipologiaAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {
    @Override
    List<Auto> findAll();


    List<Auto> findAllByTipologiaAutoIsOrderByTipologiaAutoAscModelloAsc(TipologiaAuto tipologiaAuto);
    List<Auto> findAllByModelloContainingOrderByModelloAsc(String modello);

    List<Auto> findAllByPrezzoIsLessThanOrderByPrezzoAscModelloAsc(double prezzo);

    List<Auto> findAllByQuantitaIsLessThanOrderByQuantitaAsc(int quantità);

    Auto findByModelloContaining(String modello);

    List<Auto> findAllByCasaProduttrice_RagioneSociale(String ragioneSociale);

    List<Auto> findAllByCasaProduttrice_Id(int id);
    List<Auto> findAllByQuantitaIsLessThanOrderByTipologiaAutoAsc(int quantità);
    List<Auto> findAllByQuantitaIsLessThanOrderByModelloAsc(int quantità);
}
