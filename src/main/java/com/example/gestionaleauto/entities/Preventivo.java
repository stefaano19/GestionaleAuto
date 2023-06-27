package com.example.gestionaleauto.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Preventivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(nullable = false)
    private Date dataScadenza;

    @Basic
    @Column(nullable = false)
    private Date dataCreazione;

    private double prezzo;

    @ManyToOne
    @JoinColumn(name = "AUTO_ID")
    private Auto auto;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

}
