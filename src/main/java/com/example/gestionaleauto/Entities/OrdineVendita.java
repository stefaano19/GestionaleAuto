package com.example.gestionaleauto.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class OrdineVendita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "data_ordine", nullable = false)
    private Date dataOrdine;

    @Basic
    private boolean consegnato;

    @Basic
    private boolean consegnaAdomicilio;

    @Basic
    @Column(nullable = false)
    private double importo;

    @ManyToOne
    @JoinColumn(name="CLIENTE_ID")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name="ADDETTOTRASPORTO_ID")
    private AddettoTrasporto addettoTrasporto;

    @ManyToMany(mappedBy ="ordiniVendita")
    public Collection<Auto> auto;

}