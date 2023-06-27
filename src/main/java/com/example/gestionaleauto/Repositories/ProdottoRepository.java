package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.Prodotto;
import com.example.gestionaleauto.Util.TipologiaProdotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {
    List<Prodotto> findAll();
    Prodotto findByNomeContaining(String nome);
    List<Prodotto> findAllByFornitore_RagioneSociale(String ragioneSociale);
    List<Prodotto> findAllByNomeContainingOrderByNomeAscNomeAsc(String nome);
    List<Prodotto> findAllByTipologiaIsOrderByTipologiaAscNomeAsc(TipologiaProdotto tipologiaProdotto);
    List<Prodotto> findAllByPrezzoIsLessThanOrderByPrezzoAscNomeAsc(double prezzo);
    List<Prodotto> findAllByDisponibilitàIsLessThanOrderByDisponibilitàAscNomeAsc(int disponibilità);
    List<Prodotto> findProdottoByFornitore_Id(int id);

    List<Prodotto> findAllByTipologiaIs(TipologiaProdotto tipologiaProdotto);
}
