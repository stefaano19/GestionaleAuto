package com.example.gestionaleauto.Entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Bolla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date dataEmissione;


    private Date dataRicezione;

    private Double importo;

    @OneToOne
    @JoinColumn(name="ORDINEVENDITA_ID")
    private OrdineVendita ordineVendita;

    @OneToOne
    @JoinColumn(name="ORDINEACQUISTO_ID")
    private OrdineAcquisto ordineAcquisto;
}
