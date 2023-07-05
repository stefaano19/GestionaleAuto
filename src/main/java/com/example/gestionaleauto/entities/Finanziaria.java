package com.example.gestionaleauto.entities;
import jakarta.persistence.*;
import lombok.Data;

/**
 *
 */
@Entity
@Data
public class Finanziaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name="Partita Iva", nullable = false, length = 11 )
    private String PartitaIva;

    @Basic
    private String ragioneSociale;

    @Basic
    private String sede;


}
