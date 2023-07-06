package com.example.gestionaleauto.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
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
    @ManyToMany(mappedBy ="ordiniAcquisto", cascade = CascadeType.MERGE)
    public Collection<Auto> auto;
    @ManyToMany(mappedBy ="ordiniAcquisto")
    public Collection<Prodotto> prodotto;
    @Column(nullable = true)
    private int cp_C;
    @Column(nullable = true)
    private int cp_F;

}
