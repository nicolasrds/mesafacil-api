package com.mesafacil.dominio.reserva.restaurante.enumeration;

public enum DiaDaSemana {
    DOMINGO("Domingo"),
    SEGUNDA_FEIRA("Segunda-feira"),
    TERCA_FEIRA("Terça-feira"),
    QUARTA_FEIRA("Quarta-feira"),
    QUINTA_FEIRA("Quinta-feira"),
    SEXTA_FEIRA("Sexta-feira"),
    SABADO("Sábado");

    private final String nome;

    DiaDaSemana(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
