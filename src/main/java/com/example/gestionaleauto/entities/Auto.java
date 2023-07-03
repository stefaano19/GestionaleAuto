package com.example.gestionaleauto.entities;

import com.example.gestionaleauto.util.TipologiaAuto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name="prezzo", nullable = false)
    private double prezzo;

    @Basic
    @Column(name="modello", nullable = false)
    private String modello;

    @Basic
    private String colore;

    @Basic
    private Boolean showroom;

    @Basic
    private TipologiaAuto tipologiaAuto;

    @Basic
    @Column(name="quantita", nullable = false)
    private int quantita;

    @ManyToOne
    @JoinColumn(name="CASAPRODUTTRICE_ID")
    private CasaProduttrice casaProduttrice;

    @ManyToMany
    @JoinTable(name="VENDUTA", joinColumns = {@JoinColumn(name = "AUTO_ID")}, inverseJoinColumns = {@JoinColumn(name = "ORDINEVENDITA_ID")})
    private Collection<OrdineVendita> ordiniVendita;

    @ManyToMany
    @JoinTable(name="ORDINA", joinColumns = {@JoinColumn(name = "AUTO_ID")}, inverseJoinColumns = {@JoinColumn(name = "ORDINEACQUISTO_ID")})
    private Collection<OrdineAcquisto> ordiniAcquisto;

    @ManyToMany
    @JoinTable(name="UTILIZZA", joinColumns = {@JoinColumn(name = "AUTO_ID")}, inverseJoinColumns = {@JoinColumn(name = "APPUNTAMENTO_ID")})
    private Collection<Appuntamento> appuntamenti;

    @Version
    private long version;
}
