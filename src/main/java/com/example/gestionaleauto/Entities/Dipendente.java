package com.example.gestionaleauto.Entities;

import com.example.gestionaleauto.Util.Mansione;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private Mansione mansione;

    @OneToOne
    @JoinColumn(name = "UTENTE_ID")
    private Utente utente;

}
