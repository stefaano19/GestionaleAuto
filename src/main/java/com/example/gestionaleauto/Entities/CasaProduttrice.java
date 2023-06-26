package com.example.gestionaleauto.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CasaProduttrice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Basic
    @Column(name="Partita Iva", nullable = false, length = 11 )
    private String PartitaIva;

    @Basic
    @Column(name="ragioneSociale", nullable = false)
    private String ragioneSociale;

    @Basic
    @Column(name="sede")
    private String sede;

    @Basic
    private String marchio;


}
