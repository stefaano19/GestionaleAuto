package com.example.gestionaleauto.repositories;

import com.example.gestionaleauto.entities.OrdineVendita;
import com.example.gestionaleauto.entities.PraticheVendita;
import com.example.gestionaleauto.util.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PraticheVenditaRepository extends JpaRepository<PraticheVendita, Integer> {
    PraticheVendita findAllByOrdineVendita(OrdineVendita ordineVendita);
    List<PraticheVendita> findAllByPagamento(Pagamento pagamento);
}
