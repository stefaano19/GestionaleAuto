package com.example.gestionaleauto.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
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
