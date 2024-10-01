package com.mesafacil.dominio.reserva.restaurante.enumeration;

public enum DisponibilidadeMesa {
    DISPONIVEL("Disponível"),
    INDISPONIVEL("Indisponível");

    private final String descricao;

    DisponibilidadeMesa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
