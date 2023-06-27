package com.example.gestionaleauto.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "UTENTE_ID")
    private Utente utente;

    private String cv;
}
