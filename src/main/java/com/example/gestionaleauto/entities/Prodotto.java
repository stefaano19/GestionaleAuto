package com.example.gestionaleauto.entities;

import com.example.gestionaleauto.util.TipologiaProdotto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

/**
 *
 */
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
    private String tipologia;

    @Basic
    @Column(name="disponibilità", nullable = false)
    private int disponibilita;

    @ManyToOne
    @JoinColumn(name="FORNITORE_ID")
    private Fornitore fornitore;

    private int CP_F;
    @ManyToMany
    @JoinTable(name="COINVOLGE", joinColumns = {@JoinColumn(name = "PRODOTTO_ID")}, inverseJoinColumns = {@JoinColumn(name = "ORDINEACQUISTO_ID")})
    private Collection<OrdineAcquisto> ordiniAcquisto;

    @Version
    private long version;

}
