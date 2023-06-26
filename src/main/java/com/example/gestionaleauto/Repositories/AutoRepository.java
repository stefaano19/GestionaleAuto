package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.Auto;
import com.example.gestionaleauto.Entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {
    @Override
    List<Auto> findAll();


    List<Auto> findAllByTipologiaOrderByTipologiaAscModelloAsc();
    List<Auto> findAllByModelloOrderByModelloAsc();

    List<Auto> findAllByPrezzoOrderByPrezzoAscModelloAsc();

    List<Auto> findAllByDisponibilitàOrderByDisponibilitàAscModelloAsc();

    Auto findByModelloContaining(String modello);

    List<Auto> findAllByCasaProduttrice_RagioneSociale(String ragioneSociale);

    List<Auto> findAllByCasaProduttice_Id(int id);
    List<Auto> findAllByQuantitàIsLessThanOrderByTipologiaAutoAsc(int quantità);
    List<Auto> findAllByQuantitàIsLessThanOrderByModelloAsc(int quantità);
}
