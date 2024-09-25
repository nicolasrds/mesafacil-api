package com.mesafacil.dominio.reserva.restaurante.enumeration;

public enum DisponibilidadeMesa {
    DISPONIVEL("Disponível"),
    INDISPONIVEL("Indisponível");

    private final String descricao;

    DisponibilidadeMesa(String status) {
        this.descricao = status;
    }

    public String getDescricao() {
        return descricao;
    }
}
