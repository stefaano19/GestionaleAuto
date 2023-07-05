package com.example.gestionaleauto.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

/**
 *
 */
@Entity
@Data
public class OrdineAcquisto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(nullable = false)
    private boolean conforme;

    @Basic
    @Column(name = "data_ordine", nullable = false)
    private Date dataOrdine;

    @Basic
    @Column(nullable = false)
    private double importo;

    @ManyToOne
    @JoinColumn(name="FORNITORE_ID")
    private Fornitore fornitore;
    @ManyToOne
    @JoinColumn(name="CASAPRODUTTRICE_ID")
    private CasaProduttrice casaProduttrice;
    @ManyToOne
    @JoinColumn(name="DIPENDENTE_ID")
    private Dipendente dipendente;
    @ManyToMany(mappedBy ="ordiniAcquisto")
    public Collection<Auto> auto;
    @ManyToMany(mappedBy ="ordiniAcquisto")
    public Collection<Prodotto> prodotto;
}
