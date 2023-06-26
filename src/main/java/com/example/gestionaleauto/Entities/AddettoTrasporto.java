package com.example.gestionaleauto.Entities;

import com.example.gestionaleauto.Util.TipologiaAuto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AddettoTrasporto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String ragioneSociale;

    @Basic
    private double costo;
    @Basic
    private TipologiaAuto tipologiaAuto;
    @Basic
    private String tempo;
}

