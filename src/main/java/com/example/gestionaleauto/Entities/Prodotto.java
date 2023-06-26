package com.example.gestionaleauto.Entities;

import com.example.gestionaleauto.Util.TipologiaProdotto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name="prezzo", nullable = false)
    private double prezzo;

    @Basic
    @Column(name="marca", nullable = false)
    private String marca;

    @Basic
    @Column(name="nome", nullable = false)
    private String nome;

    @Basic
    private TipologiaProdotto tipologia;

    @Basic
    @Column(name="disponibilità", nullable = false)
    private int disponibilità;

    @ManyToOne
    @JoinColumn(name="FORNITORE_ID")
    private Fornitore fornitore;

    @ManyToMany
    @JoinTable(name="COINVOLGE", joinColumns = {@JoinColumn(name = "PRODOTTO_ID")}, inverseJoinColumns = {@JoinColumn(name = "ORDINEACQUISTO_ID")})
    private Collection<OrdineAcquisto> ordiniAcquisto;

    @Version
    private long version;

}
