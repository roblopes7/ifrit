package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

@Entity(name = "empresa_contato")
public class EmpresaContato {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    private Contato contato;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
}
