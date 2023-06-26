package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {
    List<Prodotto> findAll();
    Prodotto findByNomeContaining(String nome);
    List<Prodotto> findAllByFornitore_RagioneSociale(String ragioneSociale);
    List<Prodotto> findAllByNomeOrderByNomeAscNomeAsc();
    List<Prodotto> findAllByTipologiaOrderByTipologiaAscNomeAsc();
    List<Prodotto> findAllByPrezzoOrderByPrezzoAscNomeAsc();
    List<Prodotto> findAllByDisponibilitàOrderByDisponibilitàAscNomeAsc();
    List<Prodotto> findProdottoByFornitore_Id(int id);

}
