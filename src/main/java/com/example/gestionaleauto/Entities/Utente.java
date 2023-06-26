package com.example.gestionaleauto.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(nullable = false, length = 16)
    private String cf;

    @Basic
    private String nome;
    @Basic
    private String cognome;
    @Basic
    private String permessi;
    @Basic
    private String email;
}
