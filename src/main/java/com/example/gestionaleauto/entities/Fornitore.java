package com.example.gestionaleauto.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Fornitore {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Basic
    @Column(length = 11)
    private String partitaIva;

    @Basic
    @Column(name="ragioneSociale", nullable = false)
    private String ragioneSociale;

    @Basic
    @Column(name="sede")
    private String sede;


}
