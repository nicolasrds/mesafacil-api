package com.mesafacil.dominio.reserva.restaurante.enumeration;

public enum TipoDeCulinaria {
    ITALIANA("Italiana"),
    JAPONESA("Japonesa"),
    CHINESA("Chinesa"),
    BRASILEIRA("Brasileira"),
    MEXICANA("Mexicana"),
    FRANCESA("Francesa"),
    TAILANDESA("Tailandesa"),
    INDIANA("Indiana"),
    GREGA("Grega"),
    AMERICANA("Americana"),
    ESPANHOLA("Espanhola"),
    ARGENTINA("Argentina"),
    PERUANA("Peruana"),
    VEGETARIANA("Vegetariana"),
    VEGANA("Vegana"),
    MEDITERRANEA("Mediterrânea");

    private final String descricao;

    TipoDeCulinaria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
