package com.example.gestionaleauto.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class Appuntamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private Date data;

    @Basic
    private boolean testDrive;

    @ManyToMany(mappedBy ="appuntamenti")
    public Collection<Auto> auto;
}
