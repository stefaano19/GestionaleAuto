package com.example.gestionaleauto.repositories;

import com.example.gestionaleauto.entities.Auto;
import com.example.gestionaleauto.entities.Prodotto;
import com.example.gestionaleauto.util.TipologiaProdotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {
    List<Prodotto> findAll();
    Prodotto findByNomeContaining(String nome);
    List<Prodotto> findAllByFornitore_RagioneSociale(String ragioneSociale);
    List<Prodotto> findAllByNomeContainingOrderByNomeAscNomeAsc(String nome);
    List<Prodotto> findAllByTipologiaIsOrderByTipologiaAscNomeAsc(TipologiaProdotto tipologiaProdotto);
    List<Prodotto> findAllByPrezzoIsLessThanOrderByPrezzoAscNomeAsc(double prezzo);
    List<Prodotto> findAllByDisponibilitaIsLessThanOrderByDisponibilitaAscNomeAsc(int disponibilita);
    List<Prodotto> findProdottoByFornitore_Id(int id);
    List<Prodotto> findAllByDisponibilitaIsLessThan(int disponibilita);

    List<Prodotto> findAllByTipologiaIs(TipologiaProdotto tipologiaProdotto);
}
