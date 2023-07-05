package com.example.gestionaleauto.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 *
 */
@Entity
@Data
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private double importo;

    private Date dataEmissione;

    private Date dataScadenza;

    private Date dataRicezione;

    @OneToOne
    @JoinColumn(name="ORDINEVENDITA_ID")
    private OrdineVendita ordineVendita;

    @OneToOne
    @JoinColumn(name="ORDINEACQUISTO_ID")
    private OrdineAcquisto ordineAcquisto;
}
