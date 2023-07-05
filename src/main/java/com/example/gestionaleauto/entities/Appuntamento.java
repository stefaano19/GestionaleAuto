package com.example.gestionaleauto.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

/**
 *
 */
@Entity
@Data
public class Appuntamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date data;

    @Basic
    private boolean testDrive;

    @ManyToMany(mappedBy ="appuntamenti")
    public Collection<Auto> auto;

}
