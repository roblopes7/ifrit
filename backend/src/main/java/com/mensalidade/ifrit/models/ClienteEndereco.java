package com.mensalidade.ifrit.models;

import jakarta.persistence.*;

@Entity(name = "cliente_endereco")
public class ClienteEndereco {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    private Endereco endereco;

    public ClienteEndereco(String id, Endereco endereco) {
        this.id = id;
        this.endereco = endereco;
    }

    public ClienteEndereco() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
