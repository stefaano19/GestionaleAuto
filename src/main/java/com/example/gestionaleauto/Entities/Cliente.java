package com.example.gestionaleauto.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name="data_di_nascita")
    private Date dataNascita;

    private String luogoNascita;

    private String telefono;

    @OneToOne
    @JoinColumn(name = "UTENTE_ID")
    private Utente utente;
}
