package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

@Entity(name = "cliente_contato")
public class ClienteContato extends Contato{

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
