package com.example.gestionaleauto.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;

/**
 *
 */
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
    private String permessi;
    @Basic
    private String email;
    @Basic
    private String status;
    @Basic
    private String password;
}
