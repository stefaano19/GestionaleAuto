package com.example.gestionaleauto.Repositories;

import com.example.gestionaleauto.Entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Integer> {
    Fattura findFatturaById(int id);
    List<Fattura> findAllByDataEmissioneIsAfterOrderByDataEmissioneAsc(Date date);
    List<Fattura> findAllByImportoIsLessThanOrderByImportoAsc(double importo);
    List<Fattura> findAllByDataScadenzaIsLessThanEqual(Date date);

    List<Fattura> findAllByDataScadenzaIsAfterOrderByDataEmissioneAsc(Date data);
}
