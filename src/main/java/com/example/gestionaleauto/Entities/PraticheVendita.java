package com.example.gestionaleauto.Entities;

import com.example.gestionaleauto.Util.Pagamento;
import com.example.gestionaleauto.Util.Stato;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PraticheVendita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private Pagamento pagamento;

    @Basic
    private Stato stato;

    @ManyToOne
    @JoinColumn(name="FINANZIARIA_ID")
    private Finanziaria finanziaria;

    @OneToOne
    @JoinColumn(name="ORDINEVENDITA_ID")
    private OrdineVendita ordineVendita;
}
